package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Service;
import com.letitbee.diamondvaluationsystem.entity.ServicePriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicePriceListRepository extends JpaRepository<ServicePriceList, Long> {
    List<ServicePriceList> findByServiceId(Long serviceId);

    boolean existsByMinSizeAndMaxSizeAndServiceId(float minSize, float maxSize, Long serviceId);

    ServicePriceList findByMinSizeLessThanEqualAndMaxSizeGreaterThanEqualAndService
            (float sizeMinInput, float sizeMaxInput, Service service);
}
