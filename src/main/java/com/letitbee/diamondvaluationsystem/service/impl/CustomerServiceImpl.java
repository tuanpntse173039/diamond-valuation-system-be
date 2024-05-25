package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.CustomerDTO;
import com.letitbee.diamondvaluationsystem.payload.CustomerNoRequestDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import com.letitbee.diamondvaluationsystem.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private ModelMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public Response getAllCustomer(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        //Set size page and pageNo
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Customer> page = customerRepository.findAll(pageable);

        List<Customer> customerList = page.getContent();

        List<CustomerNoRequestDTO> customerDTOList = customerList.stream().
                map(customer -> mapToNoRequestDTO(customer)).
                toList();

        Response customerResponse = new Response();

        customerResponse.setContent(customerDTOList);
        customerResponse.setPageNumber(page.getNumber());
        customerResponse.setPageSize(page.getSize());
        customerResponse.setTotalPage(page.getTotalPages());
        customerResponse.setTotalElement(page.getTotalElements());
        customerResponse.setLast(page.isLast());

        return customerResponse;
    }

    @Override
    public CustomerDTO getCustomerById(long id) {
        Customer customer = customerRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer","id", id));
        return mapToDTO(customer);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        return mapper.map(customer, CustomerDTO.class);
    }

    private Customer mapToEntity(CustomerDTO customerDTO) {
        return mapper.map(customerDTO, Customer.class);
    }

    private CustomerNoRequestDTO mapToNoRequestDTO(Customer customer) {return mapper.map(customer, CustomerNoRequestDTO.class);}

}
