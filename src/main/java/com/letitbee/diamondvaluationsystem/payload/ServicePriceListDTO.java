package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

@Data
public class ServicePriceListDTO {
    private long id;
    private float minSize;
    private float maxSize;
    private String initPrice;
    private String unitPrice;
}
