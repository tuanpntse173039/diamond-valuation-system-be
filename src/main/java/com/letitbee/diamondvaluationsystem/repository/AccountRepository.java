package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
