package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import lombok.Data;

import java.util.Set;

@Data
public class ValuationRequestDTO {
    private long id;
    private String creationDate;
    private String receiptDate;
    private String returnDate;
    private int diamondAmount;
    private String totalPrice;
    private String receiptLink;
    private String returnLink;
    private String feedback;
    private RequestStatus status;
    private PaymentDTO payment;
    private ServiceDTO service;
    private CustomerDTO customer;
    private StaffDTO staff;
}
