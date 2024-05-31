package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.DiamondImage;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.enums.*;
import lombok.Data;

import java.util.Set;

@Data
public class DiamondValuationNoteDTO {
    private long id;
    private String certificateId;
    private String proportions;
    private String clarityCharacteristic;
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
    private Set<DiamondImageDTO> diamondImages;
}
