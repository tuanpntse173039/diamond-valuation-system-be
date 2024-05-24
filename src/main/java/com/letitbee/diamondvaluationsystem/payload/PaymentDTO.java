package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;
@Data
public class PaymentDTO {
    private long id;
    private String paytime;
    private String amount;
    private String externalTransaction;
    private PaymentModeDTO paymentMode;
}
