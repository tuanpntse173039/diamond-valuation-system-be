package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.List;

@Data
public class DiamondResponse {
    private List<DiamondContainCustomerDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;
    private boolean last;
}
