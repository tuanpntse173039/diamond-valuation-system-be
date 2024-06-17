package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ValuationRequestDetailRepository extends JpaRepository<ValuationRequestDetail, Long> {

    @Query("Select v " +
            "from ValuationRequestDetail v " +
            "RIGHT JOIN DiamondValuationAssign d ON d.valuationRequestDetail = v " +
            "WHERE d.staff.id = :valuationStaffId")
    Page<ValuationRequestDetail> findAllByValuationStaff(Long valuationStaffId, Pageable pageable);
}
