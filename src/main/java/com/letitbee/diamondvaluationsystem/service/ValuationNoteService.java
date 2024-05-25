package com.letitbee.diamondvaluationsystem.service;

public interface ValuationNoteService {
    ValuationNoteDTO getAllValuationNotes(int pageNo, int pageSize, String sortBy, String sortDir);

    ValuationNoteDTO getValuationNoteById(Long id);

    ValuationNoteDTO createValuationNote(ValuationNoteDTO valuationNoteDto);

    void deleteValuationNoteById(Long id);
}
