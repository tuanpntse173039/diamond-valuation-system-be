package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsernameOrEmail(String username, String email);

    Boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByVerificationCode(String code);

    Optional<Account> findByUsername(String username);

    Account findByRole(Role role);

}
