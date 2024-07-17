package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondMarket;
import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.DiamondMarketDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.repository.DiamondMarketRepository;
import com.letitbee.diamondvaluationsystem.repository.SupplierRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondMarketService;
import com.letitbee.diamondvaluationsystem.utils.Tools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.letitbee.diamondvaluationsystem.utils.Tools.extractNumber;

@Service
public class DiamondMarketServiceImpl implements DiamondMarketService {
    private DiamondMarketRepository diamondMarketRepository;
    private ModelMapper mapper;
    private SupplierRepository supplierRepository;

    public DiamondMarketServiceImpl(DiamondMarketRepository diamondMarketRepository, ModelMapper mapper, SupplierRepository supplierRepository) {
        this.diamondMarketRepository = diamondMarketRepository;
        this.mapper = mapper;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public DiamondMarketDTO createDiamondMarket(DiamondMarketDTO diamondMarketDTO) {
        DiamondMarket diamondMarket = mapToEntity(diamondMarketDTO);
        diamondMarket.setCreationDate(new Date());
        diamondMarket.setDiamondImage("/a");
        return mapToDTO(diamondMarketRepository.save(diamondMarket));
    }

    @Override
    public List<DiamondMarketDTO> searchDiamonds(DiamondOrigin diamondOrigin,
                                                 float caratWeight,
                                                 Color color,
                                                 Clarity clarity,
                                                 Cut cut,
                                                 Polish polish,
                                                 Symmetry symmetry,
                                                 Shape shape,
                                                 Fluorescence fluorescence) {
        List<DiamondMarket> diamondMarket = diamondMarketRepository.findSelectedFieldsByDiamondProperties(
                diamondOrigin,
                caratWeight,
                color,
                clarity,
                cut,
                polish,
                symmetry,
                shape,
                fluorescence);
        if (diamondMarket != null && !diamondMarket.isEmpty()) {
            return diamondMarket.stream().map(DiamondMarket -> mapToDTO(DiamondMarket)).toList();
        } else throw new APIException(HttpStatus.NOT_FOUND, "No diamond market data found");
    }

    @Override
    public void deleteDiamondMarket(long id) {
        DiamondMarket diamondMarket = diamondMarketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("DiamondMarket", "id", id + ""));
        diamondMarketRepository.delete(diamondMarket);
    }

    @Override
    public DiamondPriceListDTO getDiamondPriceList(
            DiamondOrigin diamondOrigin,
            float caratWeight,
            Color color,
            Clarity clarity,
            Cut cut,
            Polish polish,
            Symmetry symmetry,
            Shape shape,
            Fluorescence fluorescence) {
        List<DiamondMarket> diamondMarket = diamondMarketRepository.findSelectedFieldsByDiamondProperties(
                diamondOrigin,
                caratWeight,
                color,
                clarity,
                cut,
                polish,
                symmetry,
                shape,
                fluorescence);

        if (diamondMarket != null && !diamondMarket.isEmpty()) {
            DiamondPriceListDTO diamondPriceList = new DiamondPriceListDTO();
            diamondPriceList.setDiamondOrigin(diamondOrigin);
            diamondPriceList.setCaratWeight(caratWeight);
            diamondPriceList.setColor(color);
            diamondPriceList.setClarity(clarity);
            diamondPriceList.setCut(cut);
            diamondPriceList.setPolish(polish);
            diamondPriceList.setSymmetry(symmetry);
            diamondPriceList.setShape(shape);
            diamondPriceList.setFluorescence(fluorescence);
            diamondPriceList.setMinPrice(diamondMarket.stream().findFirst().get().getPrice());
            diamondPriceList.setMaxPrice(diamondMarket.get(diamondMarket.size() - 1).getPrice());
            double fairPrice = 0;
            double cutStScore = 0;
            for (DiamondMarket diamondMarkets : diamondMarket) {
                fairPrice += diamondMarkets.getPrice();
                cutStScore += diamondMarkets.getCutScore();
            }
            fairPrice = fairPrice / diamondMarket.size();
            diamondPriceList.setFairPrice(fairPrice);
            diamondPriceList.setPricePerCarat(fairPrice / caratWeight);
            diamondPriceList.setCutScore(cutStScore / diamondMarket.size());
            return diamondPriceList;
        } else throw new APIException(HttpStatus.NOT_FOUND, "No diamond price list data found");
    }

    @Override
    public Response<DiamondMarketDTO> getAllDiamondMarket(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<DiamondMarket> page = diamondMarketRepository.findAll(pageable);
        List<DiamondMarketDTO> content = page.getContent().stream().map(this::mapToDTO).toList();

        Response<DiamondMarketDTO> response = new Response<>();
        response.setContent(content);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPage(page.getTotalPages());
        response.setLast(page.isLast());

        return response;
    }

    private void crawlDiamondMarketBaseOnOrigin(String urlRaw, DiamondOrigin diamondOrigin) {
        int limit = 100;
        double startCarat = 0.1;
        double endCarat = 50;
        try {
            //Search diamond based on carat weight
            for (double carat = startCarat; carat <= endCarat; carat += 0.2) {
                double caratMin = carat;
                double caratMax;
                if (carat >= 2) {
                    caratMax = carat + 48;
                } else {
                    caratMax = carat + 0.2;
                }
                String urlC = String.format(urlRaw + "&carat_min=%.2f&carat_max=%.2f", caratMin, caratMax);
                Document documentCarat = Jsoup.connect(urlC).timeout(500000).get();
                Element caratDocElement = documentCarat.selectFirst("#search-results-count");
                String caratCount = caratDocElement.text();
                int total = Integer.parseInt(extractNumber(caratCount));

                //insert diamond based on offset
                for (int offset = 0; offset < total; offset += limit) {
                    String url = String.format(urlRaw + "&limit=%d&offset=%d&carat_min=%.2f&carat_max=%.2f&sort_by=-price", limit, offset, caratMin, caratMax);
                    Document document = Jsoup.connect(url).timeout(500000).get();
                    Elements links = document.select("li.t-search-result-block.t-search-block.diamond-item > a");
                    for (Element link : links) {
                        String linkHref = "https://dreamstone.com" + link.attr("href");
                        Document detailDocument = Jsoup.connect(linkHref).timeout(10000).get();

                        Element caratElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div:nth-child(3) > table:nth-child(2) > tbody > tr:nth-child(2) > td:nth-child(2)");
                        Element shapeElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div:nth-child(3) > table:nth-child(2) > tbody > tr:nth-child(1) > td:nth-child(2)");
                        Element cutElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div:nth-child(3) > table:nth-child(2) > tbody > tr:nth-child(3) > td:nth-child(2)");
                        Element clarityElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div:nth-child(3) > table:nth-child(2) > tbody > tr:nth-child(5) > td:nth-child(2)");
                        Element symmetryElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div.o-col-lg-5.o-col-md-6.o-col-sm-12.u-fr > table > tbody > tr:nth-child(3) > td:nth-child(2)");
                        Element fulorenceElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div.o-col-lg-5.o-col-md-6.o-col-sm-12.u-fr > table > tbody > tr:nth-child(5) > td:nth-child(2)");
                        Element polishElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div.o-col-lg-5.o-col-md-6.o-col-sm-12.u-fr > table > tbody > tr:nth-child(4) > td:nth-child(2)");
                        Element certificateElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div.o-col-lg-5.o-col-md-6.o-col-sm-12.u-fr > table > tbody > tr:nth-child(8) > td:nth-child(2)");
                        Element priceElement = detailDocument.selectFirst("body > div.main > div.content > div.o-col-lg-5.o-col-sm-12.u-color-dark-gray.detail-diamond-info > div:nth-child(8) > span");
                        Element colorElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div:nth-child(3) > table:nth-child(2) > tbody > tr:nth-child(4) > td:nth-child(2)");
                        Element imageElement = detailDocument.selectFirst(".mobile-diamond-slider-nav > .diamond-slide-nav > .item-d-slid > img");


                        if (certificateElement == null) {
                            continue;
                        }
                        if (certificateElement.text().trim().contains(".")) {
                            certificateElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div.o-col-lg-5.o-col-md-6.o-col-sm-12.u-fr > table > tbody > tr:nth-child(9) > td:nth-child(2)");
                        }
                        if (certificateElement.text().contains("IGI")) {
                            certificateElement = detailDocument.selectFirst("#discover_more_block > div.content-md > div.o-col-lg-5.o-col-md-6.o-col-sm-12.u-fr > table > tbody > tr:nth-child(7) > td:nth-child(2)");
                        }

                        // Check if the element was found and print the result
                        String caratValue = caratElement.text().trim();
                        String shapeValue = shapeElement.text().trim();
                        String cutValue = cutElement.text().trim();
                        String clarityValue = clarityElement.text().trim();
                        String symmetryValue = symmetryElement.text().trim();
                        String florescenceValue = fulorenceElement.text().trim();
                        String polishValue = polishElement.text().trim();
                        String certificateValue = certificateElement.text().trim();
                        String priceValue = priceElement.text().trim();
                        String colorValue = colorElement.text().trim();
                        String diamondImageLink = "https://dreamstone.com" + imageElement.attr("src");

                        DiamondMarket diamond = new DiamondMarket();

                        if (diamondMarketRepository.findDiamondMarketByCertificateId(certificateValue) != null) {
                            diamond = diamondMarketRepository.findDiamondMarketByCertificateId(certificateValue);
                        }
                        diamond.setCreationDate(new Date());
                        diamond.setLink(linkHref);
                        diamond.setDiamondOrigin(diamondOrigin);
                        diamond.setDiamondImage(diamondImageLink);
                        diamond.setCutScore(Cut.cutScore(cutValue.toUpperCase()));
                        diamond.setCaratWeight(Float.parseFloat(extractNumber(caratValue)));
                        diamond.setShape(Shape.valueOf(shapeValue.toUpperCase()));
                        diamond.setCut(Cut.fromString(cutValue.toUpperCase()));
                        diamond.setClarity(Clarity.valueOf(clarityValue.toUpperCase()));
                        diamond.setSymmetry(Symmetry.fromString(symmetryValue.toUpperCase()));
                        diamond.setFluorescence(Fluorescence.fromString(florescenceValue.toUpperCase()));
                        diamond.setPolish(Polish.fromString(polishValue.toUpperCase()));
                        diamond.setCertificateId(certificateValue);
                        diamond.setPrice(Double.parseDouble(extractNumber(priceValue)));
                        diamond.setColor(Color.valueOf(colorValue.toUpperCase()));
                        diamond.setSupplier(supplierRepository.findSupplierByName("Dream Stone"));
                        diamond.setLink(linkHref);
                        diamondMarketRepository.save(diamond);
                    }


                }
                if (carat >= 2) {
                    carat = carat + 48;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crawlDiamondMarket(long id) {
        if (id == 1) {
            crawlDiamondMarketBaseOnOrigin("https://dreamstone.com/loose-diamonds/search?v=g", DiamondOrigin.NATURAL);
            crawlDiamondMarketBaseOnOrigin("https://dreamstone.com/loose-diamonds/search?v=g&natural=lab", DiamondOrigin.LAB_GROWN);
        } else if (id == 2) {
            //update later

        }
    }


    private DiamondMarketDTO mapToDTO(DiamondMarket diamondMarket) {
        return mapper.map(diamondMarket, DiamondMarketDTO.class);
    }

    private DiamondMarket mapToEntity(DiamondMarketDTO diamondMarketDTO) {
        return mapper.map(diamondMarketDTO, DiamondMarket.class);
    }
}
