package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import com.letitbee.diamondvaluationsystem.enums.RequestDetailStatus;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDetailDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondValuationNoteRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestDetailRepository;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValuationRequestDetailServiceImpl implements ValuationRequestDetailService {

    private ModelMapper mapper;
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    private DiamondValuationNoteRepository diamondValuationNoteRepository;

    public ValuationRequestDetailServiceImpl(ModelMapper mapper, ValuationRequestDetailRepository valuationRequestDetailRepository, DiamondValuationNoteRepository diamondValuationNoteRepository) {
        this.mapper = mapper;
        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
        this.diamondValuationNoteRepository = diamondValuationNoteRepository;
    }

    private ValuationRequestDetail mapToEntity(ValuationRequestDetailDTO valuationRequestDetailDTO) {
        return mapper.map(valuationRequestDetailDTO, ValuationRequestDetail.class);
    }

    private ValuationRequestDetailDTO mapToDTO(ValuationRequestDetail valuationRequestDetail) {
        return mapper.map(valuationRequestDetail, ValuationRequestDetailDTO.class);
    }

    @Override
    public Response<ValuationRequestDetailDTO> getAllValuationRequestDetail(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        //Set size page and pageNo
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ValuationRequestDetail> page = valuationRequestDetailRepository.findAll(pageable);
        List<ValuationRequestDetail> valuationRequestDetails = page.getContent();

        List<ValuationRequestDetailDTO> listDTO = valuationRequestDetails.
                stream().
                map((valuationRequestDetail) -> mapToDTO(valuationRequestDetail)).toList();

        Response<ValuationRequestDetailDTO> response = new Response<>();

        response.setContent(listDTO);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPage(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLast(page.isLast());

        return response;

    }

    @Override
    public ValuationRequestDetailDTO getValuationRequestDetailById(Long id) {
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valuation request detail", "id", id));
        return mapToDTO(valuationRequestDetail);
    }

    @Override
    public ValuationRequestDetailDTO updateValuationRequestDetail(long id, ValuationRequestDetailDTO valuationRequestDetailDTO) {
        //get valuation request detail
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valuation request detail", "id", id));
        //set data to valuation request detail
        valuationRequestDetail.setSize(valuationRequestDetailDTO.getSize());
        valuationRequestDetail.setDiamond(valuationRequestDetailDTO.isDiamond());
        valuationRequestDetail.setSealingRecordLink(valuationRequestDetailDTO.getSealingRecordLink());
        valuationRequestDetail.setMode(valuationRequestDetailDTO.isMode());
        valuationRequestDetail.setDiamondValuationAssign(
                mapper.map(valuationRequestDetailDTO.getDiamondValuationAssign(), DiamondValuationAssign.class)
        );
        valuationRequestDetail.setStatus(valuationRequestDetailDTO.getStatus());

        //save to database
        valuationRequestDetail = valuationRequestDetailRepository.save(valuationRequestDetail);
        if(valuationRequestDetail.isDiamond()) {
            DiamondValuationNote diamondValuationNote = new DiamondValuationNote();
            diamondValuationNote.setValuationRequestDetail(valuationRequestDetail);
            diamondValuationNoteRepository.save(diamondValuationNote);
        } else {
            valuationRequestDetail.setStatus(RequestDetailStatus.CANCEL);
            valuationRequestDetail = valuationRequestDetailRepository.save(valuationRequestDetail);
        }
        return mapToDTO(valuationRequestDetail);
    }

}
