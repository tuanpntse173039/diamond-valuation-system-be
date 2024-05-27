package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.ValuationNote;
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
    private Set<DiamondDTO> diamonds;
    private PaymentDTO paymentDTO;
    private ServiceDTO services;
    private ValuationRequestStatusDTO valuationRequestStatus;
    private long customerID;
    private long staffID;
}
