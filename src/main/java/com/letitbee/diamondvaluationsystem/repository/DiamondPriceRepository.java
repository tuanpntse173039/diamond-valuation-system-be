package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondPriceRepository extends JpaRepository<Diamond, Long> {
}
