package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuation;
import com.letitbee.diamondvaluationsystem.entity.SealingRecord;
import lombok.Data;

import java.util.Set;

@Data
public class ValuationNoteDTO {
    private long id;
    private String resultPrice;
    private boolean isMode;
    private String sealingRecordLink;
    private DiamondValuation diamondValuation;
    private SealingRecord sealingRecord;
    private ValuationRequestDTO valuationRequest;
    private DiamondDTO diamond;
    private Set<DiamondValuationDTO> diamondValuations;
}
