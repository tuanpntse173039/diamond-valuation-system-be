package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.DiamondValuationNoteDTO;

public interface DiamondValuationNoteService {
    DiamondValuationNoteDTO updateDiamondValuationNote(long id, DiamondValuationNoteDTO valuationNoteDTO);
}
