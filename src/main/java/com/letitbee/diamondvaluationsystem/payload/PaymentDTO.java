package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {
    private long id;
    private long valuationRequestID;
    private Date paytime;
    private String amount;
    private String externalTransaction;
    private PaymentMethodDTO paymentMethod;
}
