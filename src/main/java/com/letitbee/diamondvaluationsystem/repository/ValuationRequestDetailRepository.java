package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValuationRequestDetailRepository extends JpaRepository<ValuationRequestDetail, Long> {

    @Query(value = "WITH FilteredDetails AS ( " +
            "    SELECT " +
            "        vd.id, " +
            "        vd.valuation_price AS vd_valuation_price, " +
            "        vd.valuation_request_id, " +
            "        vd.status, " +
            "        vd.is_mode, " +
            "        d.valuation_price AS d_valuation_price, " +
            "        d.valuation_staff_id " +
            "    FROM " +
            "        valuation_request_detail vd " +
            "    LEFT JOIN " +
            "        diamond_valuation_assign d " +
            "    ON " +
            "        vd.id = d.valuation_request_detail_id " +
            "    JOIN " +
            "        valuation_request vr " +
            "    ON " +
            "        vd.valuation_request_id = vr.id " +
            "    WHERE " +
            "        vd.status != 'PENDING' AND " +
            "        vd.status != 'CANCEL' AND " +
            "        d.valuation_staff_id IS NOT NULL AND " +
            "        d.status != 0 AND " +
            "        MONTH(vr.creation_date) = MONTH(CURRENT_TIMESTAMP) AND " +
            "        YEAR(vr.creation_date) = YEAR(CURRENT_TIMESTAMP) " +
            "), StaffValuations AS ( " +
            "    SELECT " +
            "        f.valuation_staff_id, " +
            "        COUNT(CASE WHEN f.is_mode = 0 AND f.vd_valuation_price = f.d_valuation_price THEN 1 END) AS countValuation, " +
            "        COUNT(f.id) AS totalValuation " +
            "    FROM " +
            "        FilteredDetails f " +
            "    GROUP BY " +
            "        f.valuation_staff_id " +
            "), StaffDetails AS ( " +
            "    SELECT " +
            "        s.id AS staff_id, " +
            "        a.email, " +
            "        s.avatar, " +
            "        CONCAT(s.last_name, ' ', s.first_name) AS staffName, " +
            "        s.phone " +
            "    FROM " +
            "        staff s " +
            "    JOIN " +
            "        account a " +
            "    ON " +
            "        s.account_id = a.id " +
            ") " +
            "SELECT TOP 5 " +
            "    sd.avatar, " +
            "    sd.staffName, " +
            "    sd.email, " +
            "    sd.phone, " +
            "    sv.totalValuation, " +
            "    sv.countValuation " +
            "FROM " +
            "    StaffValuations sv " +
            "JOIN " +
            "    StaffDetails sd " +
            "ON " +
            "    sv.valuation_staff_id = sd.staff_id " +
            "ORDER BY " +
            "    sv.totalValuation DESC, " +
            "    sv.countValuation DESC",
            nativeQuery = true)
    List<Object[]> findTopValuation();

}
