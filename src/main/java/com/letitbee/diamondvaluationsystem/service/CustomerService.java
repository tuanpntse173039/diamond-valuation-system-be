package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.CustomerDTO;

public interface CustomerService {
    CustomerDTO getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir);

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDto);


}
