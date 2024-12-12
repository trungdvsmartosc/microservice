package com.smartosc.transactioncommandservice.repository;

import com.smartosc.transactioncommandservice.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String number);
}
