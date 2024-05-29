package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.DiamondPriceList;
import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
import com.letitbee.diamondvaluationsystem.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiamondPriceListRepository extends JpaRepository<DiamondPriceList, Long> {
    @Query("SELECT d.creationDate, d.fairPrice, d.minPrice, d.maxPrice, d.effectDate " +
            "FROM DiamondPriceList d " +
            "WHERE d.origin = :diamondOrigin " +
            "AND d.caratWeight = :caratWeight " +
            "AND d.color = :color " +
            "AND d.clarity = :clarity " +
            "AND d.cut = :cut " +
            "AND d.polish = :polish " +
            "AND d.symmetry = :symmetry " +
            "AND d.shape = :shape " +
            "AND d.fluorescence = :fluorescence")
    List<Object[]> findSelectedFieldsByDiamondProperties(
            @Param("diamondOrigin") DiamondOrigin diamondOrigin,
            @Param("caratWeight") float caratWeight,
            @Param("color") Color color,
            @Param("clarity") Clarity clarity,
            @Param("cut") Cut cut,
            @Param("polish") Polish polish,
            @Param("symmetry") Symmetry symmetry,
            @Param("shape") Shape shape,
            @Param("fluorescence") Fluorescence fluorescence);
}
