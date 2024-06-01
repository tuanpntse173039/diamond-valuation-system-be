package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.Clarity;
import com.letitbee.diamondvaluationsystem.enums.Color;
import com.letitbee.diamondvaluationsystem.enums.Cut;
import com.letitbee.diamondvaluationsystem.enums.DiamondOrigin;
import lombok.*;

import java.util.Set;

@Data
public class DiamondMarketDTO {
    private long id;
    private String diamondImage;
    private DiamondOrigin diamondOrigin;
    private float caratWeight;
    private Color color;
    private Clarity clarity;
    private double cutScore;
    private long supplierId;
}
