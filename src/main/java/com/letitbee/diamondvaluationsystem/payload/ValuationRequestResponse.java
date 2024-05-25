package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.List;
@Data
public class ValuationRequestResponse {
    private List<ValuationRequestDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;
    private boolean last;
}
