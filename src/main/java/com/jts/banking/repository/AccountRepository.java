package com.jts.banking.repository;

import com.jts.banking.domain.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);
    Optional<Account> findById(String id);
    List<Account> findAll();
    void deleteAll();

}
