package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.Set;

@Data
public class ValuationRequestDTO {
    private Long id;
    private Date creationDate;
    private Date receiptDate;
    private Date returnDate;
    @Min(value = 1, message = "Diamond amount must be greater than 0")
    private int diamondAmount;
    private Double totalServicePrice;
    private String receiptLink;
    private String returnLink;
    private String resultLink;
    private String feedback;
    private String cancelReason;
    private RequestStatus status;
    private Set<PaymentDTO> payment;
    private Set<ValuationRequestDetailDTO> valuationRequestDetails;
    private ServiceDTO service;
    private Long customerID;
    private Long staffID;
}
