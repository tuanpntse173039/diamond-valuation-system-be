package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Diamond;
import com.letitbee.diamondvaluationsystem.entity.DiamondValuation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondValuationRepository extends JpaRepository<DiamondValuation, Long> {
}
