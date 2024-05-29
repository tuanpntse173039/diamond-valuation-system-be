package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import com.letitbee.diamondvaluationsystem.enums.RequestDetailStatus;
import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDetailDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondValuationNoteRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestDetailRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestRepository;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ValuationRequestDetailServiceImpl implements ValuationRequestDetailService {

    private ModelMapper mapper;
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    private ValuationRequestRepository valuationRequestRepository;
    private DiamondValuationNoteRepository diamondValuationNoteRepository;

    public ValuationRequestDetailServiceImpl(ModelMapper mapper, ValuationRequestDetailRepository valuationRequestDetailRepository, ValuationRequestRepository valuationRequestRepository) {
        this.mapper = mapper;
        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
        this.valuationRequestRepository = valuationRequestRepository;
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

        //create diamond note when know diamond is real
        createDiamondValuationNote(valuationRequestDetailDTO, valuationRequestDetail);
        //save to database
        valuationRequestDetail = valuationRequestDetailRepository.save(valuationRequestDetail);

        ValuationRequest valuationRequest = valuationRequestDetail.getValuationRequest();
        if (valuationRequestDetail.getStatus().toString().equalsIgnoreCase(RequestDetailStatus.CANCEL.toString())
                || valuationRequestDetail.getStatus().toString().equalsIgnoreCase(RequestDetailStatus.ASSESSING.toString())) {
            valuationRequest = valuationRequestDetail.getValuationRequest();
            changeValuationRequestStatusToValuating(valuationRequest);
        } // update valuation request if valuation request detail status is cancel or assessing
        changeValuationRequestStatusToComplete(valuationRequest); //update valuation request status to complete
                                                                  //if all detail is approve or cancel
        return mapToDTO(valuationRequestDetail);
    }
    private void updateValuationRequestStatus(ValuationRequest valuationRequest, RequestStatus requestStatus) {
        valuationRequest.setStatus(requestStatus);
        valuationRequestRepository.save(valuationRequest);
    }

    private void changeValuationRequestStatusToValuating(ValuationRequest valuationRequest) {
        if(valuationRequest.getStatus().toString().equalsIgnoreCase(RequestStatus.RECEIVED.toString())) {
            RequestStatus requestStatus = RequestStatus.VALUATING;
            updateValuationRequestStatus(valuationRequest, requestStatus);
        } // update valuation request if its status is "received"
    }
    private void changeValuationRequestStatusToComplete(ValuationRequest valuationRequest) {
        Set<ValuationRequestDetail> valuationRequestDetailSet = valuationRequest.getValuationRequestDetails();

        boolean checkStatusDetail = true;
        //check status in all valuation request detail
        for(ValuationRequestDetail valuationRequestDetail : valuationRequestDetailSet) {
            if(valuationRequestDetail.getStatus().toString().equalsIgnoreCase(RequestDetailStatus.CANCEL.toString())
                    || valuationRequestDetail.getStatus().toString().equalsIgnoreCase(RequestDetailStatus.APPROVED.toString())) {
                checkStatusDetail = false;
            }
        }
        if(checkStatusDetail) {
            RequestStatus requestStatus = RequestStatus.COMPLETED;
            updateValuationRequestStatus(valuationRequest, requestStatus);
        } // update valuation request if its all detail status is cancel or approve
    }

    private void createDiamondValuationNote(ValuationRequestDetailDTO valuationRequestDetailDTO
            , ValuationRequestDetail valuationRequestDetail) {
        if(!valuationRequestDetail.isDiamond() && valuationRequestDetailDTO.isDiamond()) {
            DiamondValuationNote diamondValuationNote = new DiamondValuationNote();
            diamondValuationNote.setValuationRequestDetail(valuationRequestDetail);
            diamondValuationNoteRepository.save(diamondValuationNote);
        }
    }

}
