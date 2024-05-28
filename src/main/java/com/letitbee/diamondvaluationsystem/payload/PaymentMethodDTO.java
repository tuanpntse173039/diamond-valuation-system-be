package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Set;

@Data
public class PaymentMethodDTO {
    private long id;
    private String name;
    private Set<PaymentDTO> payments;
}
