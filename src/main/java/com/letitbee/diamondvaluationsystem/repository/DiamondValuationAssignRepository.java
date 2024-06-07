package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface DiamondValuationAssignRepository extends JpaRepository<DiamondValuationAssign, Long> {
    int countDiamondValuationAssignByStaff(Staff staff);

    @Query("SELECT d.id " +
            "FROM DiamondValuationAssign d " +
            "WHERE d.staff = :staff ")
    Set<Long> findAllByStaff(Staff staff);

}
