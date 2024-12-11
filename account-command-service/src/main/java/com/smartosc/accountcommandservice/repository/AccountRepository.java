package com.smartosc.accountcommandservice.repository;

import com.smartosc.accountcommandservice.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);

    boolean existsByNumber(String number);
}
