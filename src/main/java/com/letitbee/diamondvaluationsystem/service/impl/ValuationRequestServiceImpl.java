package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestRepository;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ValuationRequestServiceImpl implements ValuationRequestService {
    private ValuationRequestRepository valuationRequestRepository;
    private CustomerRepository customerRepository;
    private StaffRepository staffRepository;
    private ModelMapper mapper;

    public ValuationRequestServiceImpl(ValuationRequestRepository valuationRequestRepository, CustomerRepository customerRepository, StaffRepository staffRepository, ModelMapper mapper) {
        this.valuationRequestRepository = valuationRequestRepository;
        this.customerRepository = customerRepository;
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
    public ValuationRequestDTO createValuationRequest(long customerId, ValuationRequestDTO valuationRequestDto) {
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        ValuationRequest valuationRequest = mapToEntity(valuationRequestDto);
        valuationRequest.setCustomer(customer);
        valuationRequestRepository.save(valuationRequest);
        return mapToDTO(valuationRequest);
    }

    @Override
    public void deleteValuationRequestById(Long id) {

    }

    private ValuationRequestDTO mapToDTO(ValuationRequest valuationRequest) {
        ValuationRequestDTO valuationRequestDTO = mapper.map(valuationRequest, ValuationRequestDTO.class);
        //find customer
        Customer customer = customerRepository.findByValuationRequestSetContaining(valuationRequest)
                .orElseThrow(() -> new NoSuchElementException("Not found customer with this valuation request"));
        //get customerId
        long customerId = customer.getId();
        //set customer to DTO
        valuationRequestDTO.setCustomerID(customerId);
        //find valuation staff
        Staff staff = staffRepository.findStaffByValuationRequestSetContaining(valuationRequest).orElse(null);

        //map staff to DTO
        if (staff != null) {
            long staffId = staff.getId();
            //set staff to valuation request
            valuationRequestDTO.setId(staffId);
        }
        return valuationRequestDTO;
    }

    private ValuationRequest mapToEntity(ValuationRequestDTO valuationRequestDTO) {
        return mapper.map(valuationRequestDTO, ValuationRequest.class);
    }
}
