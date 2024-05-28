package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ValuationRequestRepository extends JpaRepository<ValuationRequest, Long> {
    Optional<Set<ValuationRequest>> findAllByCustomer(Customer customer);
}
