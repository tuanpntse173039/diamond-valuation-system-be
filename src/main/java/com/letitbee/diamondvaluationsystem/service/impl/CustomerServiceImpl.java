package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.CustomerDTO;
import com.letitbee.diamondvaluationsystem.payload.CustomerUpdate;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestRepository;
import com.letitbee.diamondvaluationsystem.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private ModelMapper mapper;
    private AccountRepository accountRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository,AccountRepository accountRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public Response<CustomerDTO> getAllCustomer(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        //Set size page and pageNo
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Customer> page = customerRepository.findAll(pageable);

        List<Customer> customerList = page.getContent();

        List<CustomerDTO> customerDTOList = customerList.stream().
                map(customer -> mapToDTO(customer)).
                toList();

        Response<CustomerDTO> response = new Response<>();

        response.setContent(customerDTOList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPage(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLast(page.isLast());

        return response;
    }

    @Override
    public CustomerDTO getCustomerById(long id) {
        Customer customer = customerRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id
                        + ""));
        return mapToDTO(customer);
    }

    @Override
    public void deleteCustomerById(long id) {
        Customer customer = customerRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id
                        + ""));
        Account account = accountRepository.findById(customer.getAccount().getId()).orElse(null);
        account.setIs_active(!account.getIs_active());
        accountRepository.save(account);
    }

    @Override
    public CustomerUpdate updateCustomerInformation(CustomerUpdate customerUpdate, Long id) {
        Customer customer = customerRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "AccountId", id + ""));
        if (customerUpdate.getFirstName() != null) { customer.setFirstName(customerUpdate.getFirstName()); }
        if (customerUpdate.getLastName() != null) { customer.setLastName(customerUpdate.getLastName()); }
        boolean isPhoneExist = customerRepository.existsByPhone(customerUpdate.getPhone());
        if (customerUpdate.getPhone() != null && !isPhoneExist) { customer.setPhone(customerUpdate.getPhone()); }
        else if (isPhoneExist) { throw new APIException(HttpStatus.BAD_REQUEST, "Phone number already exists"); }
        if (customerUpdate.getAddress() != null) { customer.setAddress(customerUpdate.getAddress()); }
        if (customerUpdate.getAvatar() != null) { customer.setAvatar(customerUpdate.getAvatar()); }
        if (customerUpdate.getIdentityDocument() != null) { customer.setIdentityDocument(customerUpdate.getIdentityDocument()); }
        Account account = customer.getAccount();
        boolean isEmailExist = accountRepository.existsByEmail(customerUpdate.getNewEmail());
        if (customerUpdate.getNewEmail() != null && !isEmailExist) { account.setEmail(customerUpdate.getNewEmail()); }
        else if(isEmailExist) { throw new APIException(HttpStatus.BAD_REQUEST, "Email already exists"); }
        customerRepository.save(customer);
        accountRepository.save(account);
        CustomerUpdate customerUpdateResponse = new CustomerUpdate();
        customerUpdateResponse.setFirstName(customer.getFirstName());
        customerUpdateResponse.setLastName(customer.getLastName());
        customerUpdateResponse.setPhone(customer.getPhone());
        customerUpdateResponse.setAddress(customer.getAddress());
        customerUpdateResponse.setAvatar(customer.getAvatar());
        customerUpdateResponse.setIdentityDocument(customer.getIdentityDocument());
        customerUpdateResponse.setNewEmail(account.getEmail());
        return customerUpdateResponse;
    }

    @Override
    public CustomerDTO getCustomerByPhoneOrName(String phone, String name) {

        Customer customer = customerRepository.findCustomerByPhoneOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(
                phone, "%" + name + "%", "%" + name + "%").orElseThrow(() -> new ResourceNotFoundException("Customer", "phone or name", phone + name));

        return mapToDTO(customer);
    }


    private CustomerDTO mapToDTO(Customer customer) {
        return mapper.map(customer, CustomerDTO.class);
    }

    private Customer mapToEntity(CustomerDTO customerDTO) {
        return mapper.map(customerDTO, Customer.class);
    }


}
