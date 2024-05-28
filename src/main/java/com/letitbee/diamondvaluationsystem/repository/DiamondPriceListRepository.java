package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondPriceListRepository extends JpaRepository<DiamondValuationNote, Long> {
}
