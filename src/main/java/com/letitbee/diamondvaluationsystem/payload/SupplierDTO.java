package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Set;

@Data
public class SupplierDTO {
    private Long id;
    private String image;
    private Set<DiamondMarketDTO> diamondMarket;
}
