package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class ValuationRequestDTO {
    private long id;
    private Date creationDate;
    private Date receiptDate;
    private Date returnDate;
    private int diamondAmount;
    private double totalServicePrice;
    private String receiptLink;
    private String returnLink;
    private String resultLink;
    private String feedback;
    private String cancelReason;
    private RequestStatus status;
    private Set<PaymentDTO> payment;
    private Set<ValuationRequestDetailDTO> valuationRequestDetails;
    private ServiceDTO service;
    private long customerID;
    private long staffID;
}
