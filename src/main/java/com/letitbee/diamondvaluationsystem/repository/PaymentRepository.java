package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT YEAR(v.paytime), MONTH(v.paytime), SUM(v.amount) " +
            "FROM Payment v " +
            "GROUP BY YEAR(v.paytime), MONTH(v.paytime)")
    List<Object[]> findMonthlyTotalServicePriceByMonths();


    @Query(value = "SELECT " +
            "SUM(CASE " +
            "        WHEN MONTH(v.paytime) = MONTH(GETDATE()) AND YEAR(v.paytime) = YEAR(GETDATE()) " +
            "        THEN v.amount " +
            "        ELSE 0 " +
            "    END) AS totalServicePriceCurrentMonth, " +
            "SUM(CASE " +
            "        WHEN MONTH(v.paytime) = MONTH(DATEADD(MONTH, -1, GETDATE())) AND YEAR(v.paytime) = YEAR(DATEADD(MONTH, -1, GETDATE())) " +
            "        THEN v.amount " +
            "        ELSE 0 " +
            "    END) AS totalServicePricePreviousMonth " +
            "FROM Payment v ",
            nativeQuery = true)
    List<Object[]> findTotalServicePriceCurrentAndPreviousMonth();
}
