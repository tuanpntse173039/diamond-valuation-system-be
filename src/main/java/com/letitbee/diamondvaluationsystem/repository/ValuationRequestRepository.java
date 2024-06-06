package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface ValuationRequestRepository extends JpaRepository<ValuationRequest, Long> {
    Optional<Set<ValuationRequest>> findAllByCustomer(Customer customer);

    Page<ValuationRequest> findByCreationDateBetween(Date startDate, Date endDate, Pageable pageable);

    int countValuationRequestsByStaff(Staff staff);
}
