package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDetailDTO;
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
    public ValuationRequestDetailServiceImpl(ModelMapper mapper, ValuationRequestDetailRepository valuationRequestDetailRepository) {
        this.mapper = mapper;
        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
    }

    @Override
    public Response<ValuationRequestDetailDTO> getAllValuationNotes(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        //Set size page and pageNo
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ValuationRequestDetail> page = valuationRequestDetailRepository.findAll(pageable);
        List<ValuationRequestDetail> valuationRequestDetails = page.getContent();

        List<ValuationRequestDetailDTO> listDTO = valuationRequestDetails.
                stream().
                map((valuationNote) -> mapToDTO(valuationNote)).toList();

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
    public ValuationRequestDetailDTO getValuationNoteById(Long id) {
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valuation note", "id", id));
        return mapToDTO(valuationRequestDetail);
    }

    @Override
    public ValuationRequestDetailDTO createValuationNote(ValuationRequestDetailDTO valuationRequestDetailDto) {
        return null;
    }

    @Override
    public void deleteValuationNoteById(Long id) {

    }

    private ValuationRequestDetail mapToEntity(ValuationRequestDetailDTO valuationRequestDetailDTO) {
        return mapper.map(valuationRequestDetailDTO, ValuationRequestDetail.class);
    }

    private ValuationRequestDetailDTO mapToDTO(ValuationRequestDetail valuationRequestDetail) {
        return mapper.map(valuationRequestDetail, ValuationRequestDetailDTO.class);}
}
