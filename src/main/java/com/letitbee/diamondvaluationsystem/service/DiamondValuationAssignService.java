package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationAssignDTO;

public interface DiamondValuationAssignService {
    DiamondValuationAssignDTO createDiamondValuationAssign(DiamondValuationAssignDTO diamondValuationAssignDTO);
    DiamondValuationAssignDTO updateDiamondValuationAssign(long id, DiamondValuationAssignDTO diamondValuationAssignDTO);

}
