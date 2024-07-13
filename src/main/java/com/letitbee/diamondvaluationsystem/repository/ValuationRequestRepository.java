package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import com.letitbee.diamondvaluationsystem.enums.RecordType;
import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ValuationRequestRepository extends JpaRepository<ValuationRequest, Long> {
    @Query("SELECT v.id " +
            "FROM ValuationRequest v " +
            "WHERE v.customer = :customer ")
    Set<Long> findAllByCustomer(Customer customer);

    Page<ValuationRequest> findByCreationDateBetween(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT v.id " +
            "FROM ValuationRequest v " +
            "WHERE v.staff = :staff ")
    Set<Long> findAllByStaff(Staff staff);

    int countValuationRequestsByStaff(Staff staff);

    @Query(value = "select count(vf.valuation_id) as valuation_request_count " +
            "from (" +
                    "select v.id as valuation_id, v.staff as staff from ValuationRequest v " +
                    "where v.status != 'FINISHED') vf " +
            "right join Staff s on s = vf.staff " +
            "where s = :staff " +
            "group by s.id")
    int countValuationRequestsIsProcessedByStaff(Staff staff);


    Page<ValuationRequest> findAllByStatusAndCreationDateBetween(RequestStatus status,Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT v " +
            "FROM ValuationRequest v " +
            "WHERE v.id = :requestId OR " +
            "LOWER(v.customer.firstName) LIKE LOWER(CONCAT('%', :customerName, '%')) OR " +
            "LOWER(v.customer.lastName) LIKE LOWER(CONCAT('%', :customerName, '%')) OR " +
            "v.customer.phone LIKE CONCAT('%', :phone, '%')")
    Page<ValuationRequest> searchValuationRequestByValuationRequestIdOrCustomerNameOrPhone(Long requestId, String customerName, String phone, Pageable pageable);

    @Query("SELECT v " +
            "FROM ValuationRequest v " +
            "WHERE v.customer.id = :customerId ")
    Page<ValuationRequest> findValuationRequestByCustomer_Id(Long customerId, Pageable pageable);

    @Query("SELECT v " +
            "FROM ValuationRequest v " +
            "WHERE v.staff = :staff ")
    Page<ValuationRequest> findValuationRequestByStaff_Id(Staff staff, Pageable pageable);

    @Query("SELECT v " +
            "FROM ValuationRequest v JOIN v.valuationRequestDetails d " +
            "WHERE d = :valuationRequestDetail ")
    ValuationRequest findValuationRequestByValuationRequestDetails(ValuationRequestDetail valuationRequestDetail);

    @Query("Select r.creationDate " +
            "from Record r " +
            "where r.type = :typeOfRecord and r.valuationRequest = :valuationRequest ")
    Date findCreatedDateByTypeOfRecord(RecordType typeOfRecord, ValuationRequest valuationRequest);

    @Query("SELECT YEAR(v.creationDate), MONTH(v.creationDate), SUM(v.totalServicePrice) " +
            "FROM ValuationRequest v " +
            "GROUP BY YEAR(v.creationDate), MONTH(v.creationDate)")
    List<Object[]> findMonthlyTotalServicePriceByMonths();

    @Query("SELECT YEAR(v.creationDate), MONTH(v.creationDate), COUNT(v.id) " +
            "FROM ValuationRequest v " +
            "GROUP BY YEAR(v.creationDate), MONTH(v.creationDate)")
    List<Object[]> findMonthlyAppointmentByMonths();

    @Query(value = "SELECT " +
            "SUM(CASE " +
            "        WHEN MONTH(v.creation_date) = MONTH(GETDATE()) AND YEAR(v.creation_date) = YEAR(GETDATE()) " +
            "        THEN v.total_service_price " +
            "        ELSE 0 " +
            "    END) AS totalServicePriceCurrentMonth, " +
            "SUM(CASE " +
            "        WHEN MONTH(v.creation_date) = MONTH(DATEADD(MONTH, -1, GETDATE())) AND YEAR(v.creation_date) = YEAR(DATEADD(MONTH, -1, GETDATE())) " +
            "        THEN v.total_service_price " +
            "        ELSE 0 " +
            "    END) AS totalServicePricePreviousMonth " +
            "FROM valuation_request v ",
            nativeQuery = true)
    List<Object[]> findTotalServicePriceCurrentAndPreviousMonth();

    @Query(value = "SELECT " +
            "COUNT(CASE " +
            "        WHEN MONTH(v.creation_date) = MONTH(GETDATE()) AND YEAR(v.creation_date) = YEAR(GETDATE()) " +
            "        THEN v.id " +
            "        ELSE NULL " +
            "    END) AS totalAppointmentCurrentMonth, " +
            "COUNT(CASE " +
            "        WHEN MONTH(v.creation_date) = MONTH(DATEADD(MONTH, -1, GETDATE())) AND YEAR(v.creation_date) = YEAR(DATEADD(MONTH, -1, GETDATE())) " +
            "        THEN v.id " +
            "        ELSE NULL " +
            "    END) AS totalAppointmentPreviousMonth " +
            "FROM valuation_request v ",
            nativeQuery = true)
    List<Object[]> findTotalAppointmentCurrentAndPreviousMonth();


    @Query(value = "SELECT TOP 5 " +
            "c.avatar AS avatar, " +
            "CONCAT(c.last_name, ' ', c.first_name) AS customerName, " +
            "COUNT(v.id) AS totalAppointment, " +
            "SUM(v.total_service_price) AS totalServicePrice " +
            "FROM valuation_request v " +
            "JOIN customer c ON v.customer_id = c.id " +
            "WHERE MONTH(v.creation_date) = :month " +
            "AND v.status != 'CANCEL' " +
            "AND v.status != 'PENDING' " +
            "GROUP BY c.avatar, c.first_name, c.last_name " +
            "ORDER BY totalServicePrice DESC",
            nativeQuery = true)
    List<Object[]> findTopCustomers(int month);


    @Query(value = "SELECT TOP 5 " +
            "CASE WHEN s.avatar IS NOT NULL THEN CAST(s.avatar AS varchar(max)) ELSE NULL END AS avatar, " +
            "CONCAT(s.last_name, ' ', s.first_name) AS staffName, " +
            "a.email AS staffEmail, " +
            "s.phone AS staffPhone, " +
            "COUNT(v.id) AS totalAppointment, " +
            "SUM(v.total_service_price) AS totalServicePrice " +
            "FROM valuation_request v " +
            "JOIN staff s ON v.staff_id = s.id " +
            "JOIN account a ON s.account_id = a.id " +
            "WHERE s.id IS NOT NULL and MONTH(v.creation_date) = :month " +
            "GROUP BY " +
            "CASE WHEN s.avatar IS NOT NULL THEN CAST(s.avatar AS varchar(max)) ELSE NULL END, " +
            "s.first_name, s.last_name, s.phone, a.email " +
            "ORDER BY totalServicePrice DESC",
            nativeQuery = true)
    List<Object[]> findTopConsultant(int month);



}

