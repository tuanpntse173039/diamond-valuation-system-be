package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findStaffByValuationRequestSetContaining(ValuationRequest valuationRequest);

    Staff findStaffByAccount_Id(Long accountId);

    List<Staff> findStaffByFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(String firstName, String lastName);

    @Query("Select v " +
            "from ValuationRequestDetail v " +
            "RIGHT JOIN DiamondValuationAssign d ON d.valuationRequestDetail = v " +
            "WHERE d.staff.id = :valuationStaffId")
    Page<ValuationRequestDetail> findAllByValuationStaff(Long valuationStaffId, Pageable pageable);

}
