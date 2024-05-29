package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.*;
import lombok.Data;

@Data
public class DiamondPriceListDTO {
    private long id;
    private String creationDate;
    private DiamondOrigin diamondOrigin;
    private float caratWeight;
    private Color color;
    private Clarity clarity;
    private Cut cut;
    private Polish polish;
    private Symmetry symmetry;
    private Shape shape;
    private Fluorescence fluorescence;
    private double fairPrice;
    private double minPrice;
    private double maxPrice;
    private String effectDate;
}
