package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondValuationAssignRepository extends JpaRepository<DiamondValuationAssign, Long> {
    int countDiamondValuationAssignByStaff(Staff staff);
}
