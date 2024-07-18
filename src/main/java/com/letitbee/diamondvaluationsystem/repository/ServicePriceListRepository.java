package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Service;
import com.letitbee.diamondvaluationsystem.entity.ServicePriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServicePriceListRepository extends JpaRepository<ServicePriceList, Long> {
    List<ServicePriceList> findByServiceId(Long serviceId);

    boolean existsByMinSizeAndMaxSizeAndServiceId(float minSize, float maxSize, Long serviceId);

    ServicePriceList findByMinSizeLessThanEqualAndMaxSizeGreaterThanEqualAndService
            (float sizeMinInput, float sizeMaxInput, Service service);

    @Query("SELECT COUNT(s) > 0 FROM ServicePriceList s WHERE s.service = :service AND s.id != :id AND (:minSize BETWEEN s.minSize AND s.maxSize)")
    boolean existsByMinSizeInRangeExcludingId(Long id, Service service, float minSize);

    @Query("SELECT COUNT(s) > 0 FROM ServicePriceList s WHERE s.service = :service AND s.id != :id AND (:maxSize BETWEEN s.minSize AND s.maxSize)")
    boolean existsByMaxSizeInRangeExcludingId(Long id, Service service, float maxSize);

    @Query("SELECT COUNT(s) > 0 FROM ServicePriceList s WHERE s.service = :service AND (:minSize BETWEEN s.minSize AND s.maxSize OR :maxSize BETWEEN s.minSize AND s.maxSize OR :minSize < s.minSize AND :maxSize > s.maxSize)")
    boolean existsSizeExist(Service service, float minSize, float maxSize);
}
