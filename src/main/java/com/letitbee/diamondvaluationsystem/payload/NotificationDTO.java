package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private Long id;
    private Date creationDate;
    private String message;
    private Boolean isRead;
    private Long accountId;
}
