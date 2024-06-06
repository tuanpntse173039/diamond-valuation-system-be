package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

@Data
public class NotificationDTO {
    private long id;
    private String creationDate;
    private String message;
    private Long accountId;
}
