package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.entity.DiamondImage;
import com.letitbee.diamondvaluationsystem.entity.ValuationNote;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DiamondDTO {
    private long id;
    private ValuationRequest valuationRequest;
    private String certificateId;
    private String proportions;
    private String clarityCharacteristic;
    private DiamondOrigin diamondOrigin;
    private float caratWeight;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private Clarity clarity;
    @Enumerated(EnumType.STRING)
    private Cut cut;
    @Enumerated(EnumType.STRING)
    private Polish polish;
    @Enumerated(EnumType.STRING)
    private Symmetry symmetry;
    @Enumerated(EnumType.STRING)
    private Shape shape;
    @Enumerated(EnumType.STRING)
    private Fluorescence fluorescence;
    private String fairPrice;
    private String minPrice;
    private String maxPrice;
    private boolean isStatus;
    private Set<DiamondImage> diamondImage;
}
