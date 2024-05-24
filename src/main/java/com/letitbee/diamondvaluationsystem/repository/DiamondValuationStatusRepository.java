package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Diamond;
import com.letitbee.diamondvaluationsystem.entity.DiamondValuationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondValuationStatusRepository extends JpaRepository<DiamondValuationStatus, Long> {
}
