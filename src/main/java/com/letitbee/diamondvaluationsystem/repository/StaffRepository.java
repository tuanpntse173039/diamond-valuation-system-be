package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findStaffByValuationRequestSetContaining(ValuationRequest valuationRequest);
}
