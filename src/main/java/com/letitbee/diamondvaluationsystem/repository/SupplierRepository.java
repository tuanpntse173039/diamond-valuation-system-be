package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
import com.letitbee.diamondvaluationsystem.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
