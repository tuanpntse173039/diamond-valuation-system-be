package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import com.letitbee.diamondvaluationsystem.enums.RequestDetailStatus;
import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestDetailRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestRepository;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ValuationRequestServiceImpl implements ValuationRequestService {
    private ValuationRequestRepository valuationRequestRepository;
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    private StaffRepository staffRepository;
    private ModelMapper mapper;

    public ValuationRequestServiceImpl(ValuationRequestRepository valuationRequestRepository, ValuationRequestDetailRepository valuationRequestDetailRepository, StaffRepository staffRepository, ModelMapper mapper) {
        this.valuationRequestRepository = valuationRequestRepository;
        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
        this.staffRepository = staffRepository;
        this.mapper = mapper;
    }

    @Override
    public Response<ValuationRequestDTO> getAllValuationRequests(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        //Set size page and pageNo
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ValuationRequest> page = valuationRequestRepository.findAll(pageable);
        List<ValuationRequest> valuationRequests = page.getContent();

        List<ValuationRequestDTO> listDTO = valuationRequests.
                stream().
                map((valuationRequest) -> mapToDTO(valuationRequest)).toList();

        Response<ValuationRequestDTO> response = new Response<>();

        response.setContent(listDTO);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPage(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLast(page.isLast());

        return response;

    }

    @Override
    public ValuationRequestDTO getValuationRequestById(Long id) {
        ValuationRequest valuationRequest = valuationRequestRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Valuation request", "id", id));
        return mapToDTO(valuationRequest);
    }

    @Override
    public ValuationRequestDTO createValuationRequest(ValuationRequestDTO valuationRequestDto) {
        ValuationRequest valuationRequest = mapToEntity(valuationRequestDto);
        valuationRequest.setStatus(RequestStatus.PENDING);
        valuationRequest.setCreationDate(new Date());
        valuationRequestRepository.save(valuationRequest);
        for (int i = 0; i < valuationRequest.getDiamondAmount(); i++) {
            ValuationRequestDetail valuationRequestDetail = new ValuationRequestDetail();
            valuationRequestDetail.setValuationRequest(valuationRequest);
            valuationRequestDetail.setStatus(RequestDetailStatus.PENDING);
            valuationRequestDetailRepository.save(valuationRequestDetail);
        }
        return mapToDTO(valuationRequest);
    }

    @Override
    public ValuationRequestDTO updateValuationRequest(long id, ValuationRequestDTO valuationRequestDTO) {
        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valuation Request", "id", id));
        //get staff
        Staff staff = staffRepository.findById(valuationRequestDTO.getStaffID()).orElse(null);

        //update valuation request
        valuationRequest.setStaff(staff);
        valuationRequest.setFeedback(valuationRequestDTO.getFeedback());
        valuationRequest.setReturnDate(valuationRequestDTO.getReturnDate());
        valuationRequest.setReceiptDate(valuationRequestDTO.getReceiptDate());
        valuationRequest.setReturnLink(valuationRequestDTO.getReturnLink());
        valuationRequest.setReceiptLink(valuationRequestDTO.getReceiptLink());
        valuationRequest.setStatus(valuationRequestDTO.getStatus());
        valuationRequest.setCancelReason(valuationRequestDTO.getCancelReason());
        //save to database
        valuationRequest = valuationRequestRepository.save(valuationRequest);
        //map to dto
        valuationRequestDTO = mapToDTO(valuationRequest);
        return valuationRequestDTO;
    }

    @Override
    public ValuationRequestDTO deleteValuationRequestById(Long id) {
        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valuation Request", "id", id));
        if (!valuationRequest.getStatus().toString().equalsIgnoreCase(RequestStatus.PENDING.toString())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Can't cancel this valuation request!");
        }
        valuationRequest.setStatus(RequestStatus.CANCEL);
        valuationRequest = valuationRequestRepository.save(valuationRequest);
        return mapToDTO(valuationRequest);
    }

    private ValuationRequestDTO mapToDTO(ValuationRequest valuationRequest) {
        return mapper.map(valuationRequest, ValuationRequestDTO.class);
    }

    private ValuationRequest mapToEntity(ValuationRequestDTO valuationRequestDTO) {
        ValuationRequest valuationRequest = mapper.map(valuationRequestDTO, ValuationRequest.class);
        long staffId = valuationRequestDTO.getStaffID();
        if (staffId == 0) {
            valuationRequest.setStaff(null);
        }
        return valuationRequest;
    }
}
