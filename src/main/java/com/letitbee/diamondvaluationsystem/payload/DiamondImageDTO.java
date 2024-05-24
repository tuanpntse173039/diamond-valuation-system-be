package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.Diamond;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class DiamondImageDTO {
    private long id;
    private String image;
}
