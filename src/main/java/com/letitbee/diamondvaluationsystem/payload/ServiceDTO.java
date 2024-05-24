package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Set;

@Data
public class ServiceDTO {
     private long id;
     private String name;
     private String description;
     private String period;
     private Set<ServicePriceListDTO> servicePriceList;
}
