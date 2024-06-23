package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.DiamondOrigin;
import com.letitbee.diamondvaluationsystem.enums.RequestDetailStatus;
import lombok.Data;

import java.util.Date;

@Data
public class DiamondValuationAssignResponse {
    private Long id;
    private String staffName;
    private Date deadline;
    private String serviceName;
    private String certificateId;
    private DiamondOrigin diamondOrigin;
    private Float caratWeight;
    private Double valuationPrice;
    private boolean status;
}
