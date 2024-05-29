package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.enums.RequestDetailStatus;
import lombok.Data;

import java.util.Set;

@Data
public class ValuationRequestDetailDTO {
    private long id;
    private String resultPrice;
    private boolean isMode;
    private String sealingRecordLink;
    private float size;
    private RequestDetailStatus status;
    private boolean isDiamond;
    private DiamondValuationAssignDTO diamondValuationAssign;
    private long valuationRequestID;
    private double servicePrice;
    private DiamondValuationNoteDTO diamondValuationNote;
    private Set<DiamondValuationAssignDTO> diamondValuationAssigns;
}
