package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

@Data
public class DiamondPriceDTO {
    private long id;
    private String creationDate;
    private String origin;
    private float caratWeight;
    private String shape;
    private String color;
    private String clarity;
    private String cut;
    private String polish;
    private String symmetry;
    private String fluorescence;
    private String fairPrice;
    private String minPrice;
    private String maxPrice;
    private String effectDate;
    private String certificateId;
}
