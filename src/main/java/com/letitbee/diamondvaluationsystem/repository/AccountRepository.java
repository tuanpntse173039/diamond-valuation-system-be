package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsernameOrEmail(String username, String email);

    Boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByVerificationCode(String code);

    Optional<Account> findByUsername(String username);

    Account findByRole(Role role);

    @Query(value = "SELECT " +
            "SUM(CASE " +
            "        WHEN DATEPART(WEEK, a.creation_date) = DATEPART(WEEK, GETDATE()) AND DATEPART(YEAR, a.creation_date) = DATEPART(YEAR, GETDATE()) " +
            "        THEN 1 " +
            "        ELSE 0 " +
            "    END) AS currentWeekCount, " +
            "SUM(CASE " +
            "        WHEN DATEPART(WEEK, a.creation_date) = DATEPART(WEEK, DATEADD(WEEK, -1, GETDATE())) AND DATEPART(YEAR, a.creation_date) = DATEPART(YEAR, DATEADD(WEEK, -1, GETDATE())) " +
            "        THEN 1 " +
            "        ELSE 0 " +
            "    END) AS previousWeekCount " +
            "FROM account a " +
            "WHERE a.role = 'CUSTOMER' AND a.is_active = 1",
            nativeQuery = true)
    List<Object[]> findNewCustomerAccountCurrentAndPreviousWeek();

}
