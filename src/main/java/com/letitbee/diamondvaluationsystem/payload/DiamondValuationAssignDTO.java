package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

@Data
public class DiamondValuationAssignDTO {
    private long id;
    private String creationDate;
    private String comment;
    private double valuationPrice;
    private boolean status;
    private long staffID;
    private long valuationRequestDetailID;
}
