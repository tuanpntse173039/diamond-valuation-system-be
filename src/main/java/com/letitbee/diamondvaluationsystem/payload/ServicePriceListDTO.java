package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

@Data
public class ServicePriceListDTO {
    private long id;
    private String name;
    private float minSize;
    private float maxSize;
    private String price;
}
