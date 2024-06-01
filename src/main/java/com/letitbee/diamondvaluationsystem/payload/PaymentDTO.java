package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {
    private long id;
    private Date paytime;
    private double amount;
    private String externalTransaction;
    private PaymentMethodDTO paymentMethod;
    private long valuationRequestID;
}
