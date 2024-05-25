package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.ValuationNoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondValuationStatusRepository extends JpaRepository<ValuationNoteStatus, Long> {
}
