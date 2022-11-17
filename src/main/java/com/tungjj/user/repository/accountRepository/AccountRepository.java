package com.tungjj.user.repository.accountRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountRepository {
    void insertOrUpdate(Account account);

    Optional<List<Account>> getAccounts(Map<String, String> allParams, String keySort, int page, int pageSize,
            String sortField);

    // void deleteAccount(String accountId);
}
