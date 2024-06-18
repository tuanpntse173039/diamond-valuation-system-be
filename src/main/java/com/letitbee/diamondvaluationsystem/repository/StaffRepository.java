package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.*;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationAssignDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findStaffByValuationRequestSetContaining(ValuationRequest valuationRequest);

    Staff findStaffByAccount_Id(Long accountId);

    List<Staff> findStaffByFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(String firstName, String lastName);

    @Query("SELECT s " +
            "FROM Staff s " +
            "INNER JOIN  Account a ON s.account = a " +
            "WHERE a.role = :role or :role IS NULL ")
    Page<Staff> findStaffByRole(Role role, org.springframework.data.domain.Pageable pageable);

    @Query("Select d " +
            "from DiamondValuationAssign d " +
            "WHERE d.staff = :staff")
    Page<DiamondValuationAssign> findAllByValuationStaff(Staff staff, Pageable pageable);

}
