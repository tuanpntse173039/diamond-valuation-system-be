package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.*;
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
    private Polish polish;
    private Symmetry symmetry;
    private Shape shape;
    private Fluorescence fluorescence;
    private double cutScore;
    private Long supplierId;
}
