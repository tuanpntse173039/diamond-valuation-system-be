package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Date;

@Data
public class ValuationRequestResponseV2 {
    private long id;
    private Date creationDate;
    private String serviceName;
    private String customerFirstName;
    private String customerLastName;
    private Date receiptDate;
    private Date returnDate;
    private int diamondAmount;
    private double totalServicePrice;
    private String receiptLink;
    private String returnLink;
    private String resultLink;
    private String sealingRecordLink;
    private String feedback;
    private String cancelReason;
    private RequestStatus status;
}
