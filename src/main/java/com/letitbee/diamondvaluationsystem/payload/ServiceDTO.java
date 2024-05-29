package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Set;

@Data
public class ServiceDTO {
     private long id;
     private String name;
     private String description;
     private int period;
     private Set<ServicePriceListDTO> servicePriceList;
}
