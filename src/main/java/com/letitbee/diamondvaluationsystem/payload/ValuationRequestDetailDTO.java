package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import lombok.Data;

import java.util.Set;

@Data
public class ValuationRequestDetailDTO {
    private long id;
    private String resultPrice;
    private boolean isMode;
    private String sealingRecordLink;
    private float size;
    private String status;
    private boolean isDiamond;
    private DiamondValuationAssign diamondValuationAssign;
    private ValuationRequestDTO valuationRequest;
    private DiamondValuationNoteDTO diamond;
    private Set<DiamondValuationAssignDTO> diamondValuations;
}
