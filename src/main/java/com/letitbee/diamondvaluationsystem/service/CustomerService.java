package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.CustomerDTO;
import com.letitbee.diamondvaluationsystem.payload.CustomerResponse;

public interface CustomerService {
    CustomerResponse getAllCustomer(int pageNo, int pageSize, String sortBy, String sortDir);
    CustomerDTO getCustomerById(long id);
}
