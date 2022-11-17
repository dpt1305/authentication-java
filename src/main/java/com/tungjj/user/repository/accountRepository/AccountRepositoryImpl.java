package com.tungjj.user.repository.accountRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.tungjj.user.repository.AbstractMongoRepository;
import com.tungjj.user.utils.DateFormat;

@Repository
public class AccountRepositoryImpl extends AbstractMongoRepository implements AccountRepository {
    @Override
    public void insertOrUpdate(Account account) {
        account.setUpdatedAt(DateFormat.getCurrentDate());
        authenticationTemplate.save(account);
    }

    @Override
    public Optional<List<Account>> getAccounts(Map<String, String> allParams, String keySort, int page, int pageSize,
            String sortField) {
        Query query = generateQueryMongoDB(allParams, Account.class, keySort, sortField, page, pageSize);
        return replaceFind(query, Account.class);
    }

    // @Override
    // public void deleteAccount(String accountId) {
    // // TODO Auto-generated method stub
    // Account account =
    // }

}
