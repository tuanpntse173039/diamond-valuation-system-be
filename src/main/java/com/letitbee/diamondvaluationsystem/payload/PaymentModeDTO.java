package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Set;

@Data
public class PaymentModeDTO {
    private long id;
    private String name;
    private Set<PaymentDTO> paymentDTOSet;
}
