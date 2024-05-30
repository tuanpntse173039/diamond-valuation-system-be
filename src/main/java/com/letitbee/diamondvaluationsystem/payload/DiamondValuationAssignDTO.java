package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Date;

@Data
public class DiamondValuationAssignDTO {
    private long id;
    private Date creationDate;
    private String comment;
    private double valuationPrice;
    private boolean status;
    private long staffID;
    private long valuationRequestDetailID;
}
