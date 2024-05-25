package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.ValuationNote;
import lombok.Data;

import java.util.List;

@Data
public class ValuationNoteResponse {
    private List<ValuationNoteContainCustomerDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;
    private boolean last;
}
