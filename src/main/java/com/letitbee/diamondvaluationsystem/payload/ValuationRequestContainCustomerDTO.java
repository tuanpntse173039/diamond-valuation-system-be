package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

@Data
public class ValuationRequestContainCustomerDTO {
    private ValuationRequestDTO valuationRequestDTO;
    private CustomerDTO customerDTO;
}
