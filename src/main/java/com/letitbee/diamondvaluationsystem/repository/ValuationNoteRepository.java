package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Diamond;
import com.letitbee.diamondvaluationsystem.entity.ValuationNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValuationNoteRepository extends JpaRepository<ValuationNote, Long> {
}
