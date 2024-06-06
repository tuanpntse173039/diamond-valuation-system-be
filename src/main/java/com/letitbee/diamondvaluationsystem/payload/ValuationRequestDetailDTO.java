package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.enums.RequestDetailStatus;
import lombok.Data;

import java.util.Set;

@Data
public class ValuationRequestDetailDTO {
    private Long id;
    private double valuationPrice;
    private boolean isMode;
    private String sealingRecordLink;
    private Float size;
    private RequestDetailStatus status;
    private boolean isDiamond;
    private DiamondValuationAssignDTO diamondValuationAssign;
    private Long valuationRequestID;
    private double servicePrice;
    private DiamondValuationNoteDTO diamondValuationNote;
    private Set<DiamondValuationAssignDTO> diamondValuationAssigns;
}
