package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondMarket;
import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.DiamondMarketDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.repository.DiamondMarketRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondMarketService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiamondMarketServiceImpl implements DiamondMarketService {
    private DiamondMarketRepository diamondMarketRepository;
    private ModelMapper mapper;

    public DiamondMarketServiceImpl(DiamondMarketRepository diamondMarketRepository, ModelMapper mapper) {
        this.diamondMarketRepository = diamondMarketRepository;
        this.mapper = mapper;
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
         }
        else throw new APIException(HttpStatus.NOT_FOUND,"No diamond market data found");
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

        List<DiamondMarket> diamondMarkets1 = diamondMarketRepository.findSelectedFieldByDiamondProperties(
                diamondOrigin,
                caratWeight,
                color,
                clarity,
                cut,
                polish,
                symmetry,
                shape,
                fluorescence);

        if (diamondMarket != null && !diamondMarket.isEmpty()
                && diamondMarkets1 != null && !diamondMarkets1.isEmpty()
        ) {
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
            diamondPriceList.setMaxPrice(diamondMarket.get(diamondMarket.size()-1).getPrice());
            double fairPrice = 0;
            for (DiamondMarket diamondMarkets : diamondMarket) {
                fairPrice += diamondMarkets.getPrice();
            }
            fairPrice = fairPrice / diamondMarket.size();
            diamondPriceList.setFairPrice(fairPrice);
            diamondPriceList.setPricePerCarat(fairPrice / caratWeight);
            diamondPriceList.setCutScore(diamondMarkets1.stream().findFirst().get().getCutScore());
            return diamondPriceList;
        }
        else throw new APIException(HttpStatus.NOT_FOUND,"No diamond price list data found");
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


    private DiamondMarketDTO mapToDTO(DiamondMarket diamondMarket) {
        return mapper.map(diamondMarket, DiamondMarketDTO.class);
    }

    private DiamondMarket mapToEntity(DiamondMarketDTO diamondMarketDTO) {
        return mapper.map(diamondMarketDTO, DiamondMarket.class);
    }
}
