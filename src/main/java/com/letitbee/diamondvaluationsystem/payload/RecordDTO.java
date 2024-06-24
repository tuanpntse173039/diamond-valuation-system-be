package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.RecordType;
import lombok.Data;

import java.util.Date;

@Data
public class RecordDTO {
    private Long id;
    private String link;
    private Date creationDate;
    private Boolean status;
    private RecordType type;
    private Long valuationRequestId;
}
