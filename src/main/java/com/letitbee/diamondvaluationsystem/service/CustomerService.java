package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.payload.CustomerDTO;
import com.letitbee.diamondvaluationsystem.payload.CustomerUpdate;
import com.letitbee.diamondvaluationsystem.payload.PaymentMethodDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;


public interface CustomerService {

    Response<CustomerDTO> getAllCustomer(int pageNo, int pageSize, String sortBy, String sortDir);

    CustomerDTO getCustomerById(long id);

    void deleteCustomerById(long id);
    CustomerDTO createCustomerInformation(CustomerDTO customerDto);

    CustomerUpdate updateCustomerInformation(CustomerUpdate customerUpdate, Long id);
//    void deleteCustomerById(Long id);

    CustomerDTO  getCustomerByPhoneOrName(String phone,String name);

    interface PaymentMethod {
        Response<PaymentMethodDTO> getAllPaymentMethod();
    }
}
