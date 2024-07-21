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

    Boolean existsByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByVerificationCode(String code);

    Optional<Account> findByUsername(String username);

    Account findByRole(Role role);

    @Query(value = "WITH CurrentWeek AS (" +
            "    SELECT DATEADD(DAY, -((DATEPART(WEEKDAY, GETDATE()) + @@DATEFIRST - 2) % 7), GETDATE()) AS StartOfWeek" +
            "), PreviousWeek AS (" +
            "    SELECT DATEADD(DAY, -7, StartOfWeek) AS StartOfWeek" +
            "    FROM CurrentWeek" +
            ")" +
            "SELECT " +
            "    SUM(CASE " +
            "            WHEN a.creation_date >= cw.StartOfWeek AND a.creation_date < DATEADD(DAY, 7, cw.StartOfWeek) " +
            "            THEN 1 " +
            "            ELSE 0 " +
            "        END) AS currentWeekCount," +
            "    SUM(CASE " +
            "            WHEN a.creation_date >= pw.StartOfWeek AND a.creation_date < cw.StartOfWeek " +
            "            THEN 1 " +
            "            ELSE 0 " +
            "        END) AS previousWeekCount " +
            "FROM account a, CurrentWeek cw, PreviousWeek pw " +
            "WHERE a.role = 'CUSTOMER' AND a.is_active = 1",
            nativeQuery = true)
    List<Object[]> findNewCustomerAccountCurrentAndPreviousWeek();


}
