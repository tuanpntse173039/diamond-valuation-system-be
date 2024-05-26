package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.ValuationNote;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class DiamondValuationDTO {
    private long id;
    private String creationDate;
    private String comment;
    private String valuationPrice;
    private boolean status;
    private StaffNoValuationDTO staffNoValuationDTO;
}
