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
import com.letitbee.diamondvaluationsystem.repository.*;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ValuationRequestServiceImpl implements ValuationRequestService {
    private ValuationRequestRepository valuationRequestRepository;
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    private StaffRepository staffRepository;
    private ServiceRepository serviceRepository;
    private ModelMapper mapper;

    public ValuationRequestServiceImpl(ValuationRequestRepository valuationRequestRepository,
                                       ValuationRequestDetailRepository valuationRequestDetailRepository,
                                       StaffRepository staffRepository,
                                       ServiceRepository serviceRepository,
                                       ModelMapper mapper) {
        this.valuationRequestRepository = valuationRequestRepository;
        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
        this.staffRepository = staffRepository;
        this.serviceRepository = serviceRepository;
        this.mapper = mapper;
    }

    @Override
    public Response<ValuationRequestDTO> getAllValuationRequests(int pageNo, int pageSize, String sortBy, String sortDir, Date startDate, Date endDate) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        //Set size page and pageNo
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ValuationRequest> page = valuationRequestRepository.findByCreationDateBetween(startDate, endDate, pageable);
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
                orElseThrow(() -> new ResourceNotFoundException("Valuation request", "id", id + ""));
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
                .orElseThrow(() -> new ResourceNotFoundException("Valuation Request", "id", id + ""));
        //get staff
        Staff staff = staffRepository.findById(valuationRequestDTO.getStaffID()).orElse(null);

        //update valuation request
        valuationRequest.setStaff(staff);
        valuationRequest.setFeedback(valuationRequestDTO.getFeedback());
        valuationRequest.setReturnDate(valuationRequestDTO.getReturnDate());
        valuationRequest.setReceiptDate(valuationRequestDTO.getReceiptDate());
        valuationRequest.setReturnLink(valuationRequestDTO.getReturnLink());
        valuationRequest.setResultLink(valuationRequestDTO.getResultLink());
        if(valuationRequest.getReceiptLink() == null && valuationRequestDTO.getReceiptLink() != null) {
            valuationRequest.setReceiptDate(new Date());
        }
        valuationRequest.setReceiptLink(valuationRequestDTO.getReceiptLink());

        valuationRequest.setStatus(valuationRequestDTO.getStatus());
        valuationRequest.setCancelReason(valuationRequestDTO.getCancelReason());

        if(valuationRequest.getReceiptLink() != null){
            valuationRequest.setReturnDate(getReturnDate(valuationRequest));
        }
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
                .orElseThrow(() -> new ResourceNotFoundException("Valuation Request", "id", id + ""));
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

    private Date getReturnDate(ValuationRequest valuationRequest) {
        com.letitbee.diamondvaluationsystem.entity.Service service = valuationRequest.getService();
        int period = service.getPeriod();
        int totalHourService = valuationRequest.getDiamondAmount() * period;
        Date receiptDate = dateRounding(valuationRequest.getReceiptDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(receiptDate);
        int remainHourInDay = 17 - calendar.get(Calendar.HOUR_OF_DAY);
        int remainHourService = totalHourService - remainHourInDay;
        if (remainHourService <= 0) {
            calendar.add(Calendar.HOUR_OF_DAY, totalHourService);
            return calendar.getTime();
        }
        int count = 0;
        int hourInLastDay = 0;
        while(remainHourService - 9 > 0) {
            count++;
            remainHourService -= 9;
            hourInLastDay = remainHourService;
        }
        calendar.add(Calendar.DAY_OF_MONTH, count);
        calendar.set(Calendar.HOUR_OF_DAY, 8 +  hourInLastDay);
        return calendar.getTime();
    }


    private Date dateRounding(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

}
