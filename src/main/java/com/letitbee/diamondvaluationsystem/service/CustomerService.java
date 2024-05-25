package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.CustomerDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;

public interface CustomerService {

    Response getAllCustomer(int pageNo, int pageSize, String sortBy, String sortDir);

    CustomerDTO getCustomerById(long id);

//    CustomerDTO createCustomer(CustomerDTO customerDto);
//
//    CustomerDTO updateCustomer(CustomerDTO customerDto, Long id);
//
//    void deleteCustomerById(Long id);
}
