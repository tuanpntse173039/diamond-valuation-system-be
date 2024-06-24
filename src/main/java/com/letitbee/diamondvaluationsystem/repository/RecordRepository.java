package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    Optional<List<Record>> findAllByValuationRequestId(Long valuationRequestId);

    Optional<Record> findByValuationRequestIdAndType(Long valuationRequestId, String type);
}
