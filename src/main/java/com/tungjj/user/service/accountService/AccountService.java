package com.tungjj.user.service.accountService;

import java.util.Optional;

import com.tungjj.user.dto.accountDTO.AccountLoginRequest;
import com.tungjj.user.dto.accountDTO.AccountLoginResponse;
import com.tungjj.user.dto.accountDTO.AccountRequest;
import com.tungjj.user.dto.accountDTO.AccountResponse;
import com.tungjj.user.repository.accountRepository.Account;

public interface AccountService {
    void createNewAccount(AccountLoginRequest accountLoginRequest);

    Optional<AccountResponse> getAccountInfo(String accountId);

    void updateAccount(String accountId, AccountRequest accountRequest);

    void updatePassword(String accountId, String password);

    void deleteAccount(String accountId);

    Optional<AccountLoginResponse> login(AccountLoginRequest accountLoginRequest);

    void logout(Account account);
}
