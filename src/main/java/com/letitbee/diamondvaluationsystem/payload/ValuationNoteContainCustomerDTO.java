package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.ValuationNote;
import lombok.Data;

@Data
public class ValuationNoteContainCustomerDTO {
    private ValuationNote valuationNote;
    private CustomerDTO customerDTO;
}
