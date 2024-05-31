package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.DiamondMarket;
import com.letitbee.diamondvaluationsystem.entity.DiamondPriceList;
import com.letitbee.diamondvaluationsystem.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiamondMarketRepository extends JpaRepository<DiamondMarket, Long> {
    @Query("SELECT d " +
            "FROM DiamondMarket d " +
            "WHERE d.diamondOrigin = :diamondOrigin " +
            "AND d.caratWeight between :caratWeight - 1 and :caratWeight + 1" +
            "AND d.color = :color " +
            "AND d.clarity = :clarity ")
    List<DiamondMarket> findSelectedFieldsByDiamondProperties(
            @Param("diamondOrigin") DiamondOrigin diamondOrigin,
            @Param("caratWeight") float caratWeight,
            @Param("color") Color color,
            @Param("clarity") Clarity clarity);
}
