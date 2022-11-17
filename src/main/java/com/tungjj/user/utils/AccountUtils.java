package com.tungjj.user.utils;

import com.tungjj.user.dto.accountDTO.AccountResponse;
import com.tungjj.user.repository.accountRepository.Account;

public class AccountUtils {
    public AccountResponse generateAccountResponse(Account account) {
        System.out.println("accc safd asd 12123  " + account);
        AccountResponse newAccountResponse = new AccountResponse(
                account.get_id().toString(),
                account.getUsername(),
                account.getPassword(),
                account.getName(),
                account.getAge(),
                account.isDeleted());
        // newAccountResponse.setName(account.getName());
        // newAccountResponse.setAge(account.getAge());
        // newAccountResponse.setUsername(account.getUsername());
        // newAccountResponse.setId(account.get_id().toString());
        // newAccountResponse.setDeleted(account.isDeleted());

        System.out.println("acc response  " + newAccountResponse);
        return newAccountResponse;
    }
}
