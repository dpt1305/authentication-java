package com.tungjj.user.service.accountService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.tungjj.user.dto.accountDTO.AccountLoginRequest;
import com.tungjj.user.dto.accountDTO.AccountLoginResponse;
import com.tungjj.user.dto.accountDTO.AccountRequest;
import com.tungjj.user.dto.accountDTO.AccountResponse;
import com.tungjj.user.exception.InvalidRequestException;
import com.tungjj.user.exception.ResourceNotFoundException;
import com.tungjj.user.jwt.JwtTokenProvider;
import com.tungjj.user.repository.accountRepository.Account;
import com.tungjj.user.repository.accountRepository.AccountRepository;
import com.tungjj.user.service.AbstractService;
import com.tungjj.user.utils.AccountUtils;
import com.tungjj.user.utils.DateFormat;

@Service
public class AccountServiceImpl extends AbstractService<AccountRepository> implements AccountService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private AccountUtils accountUtils;

    @Override
    public void createNewAccount(AccountLoginRequest accountRequest) {
        validate(accountRequest);
        List<Account> accounts = repository
                .getAccounts(Map.ofEntries(Map.entry("username", accountRequest.getUsername())), "", 0, 0, "")
                .get();
        if (accounts.size() != 0) {
            throw new ResourceNotFoundException("Username existed!");
            // Map<String, String> errors = generateError(accountRequest.getClass());
            // errors.put("usename", "username existed!");
            // throw new InvalidRequestException(errors, "username existed!");
        }
        String password = bCryptPasswordEncoder().encode(accountRequest.getPassword());
        accountRequest.setPassword(password);
        Account newAccount = objectMapper.convertValue(accountRequest, Account.class);
        Date currentDate = DateFormat.getCurrentDate();

        newAccount.setDeleted(false);
        newAccount.setCreatedAt(currentDate);
        newAccount.setJwt("");
        newAccount.set_2FA("");
        newAccount.setAge(0);
        newAccount.setName("");

        repository.insertOrUpdate(newAccount);
    }

    @Override
    public void updateAccount(String accountId, AccountRequest accountRequest) {
        List<Account> accounts = repository.getAccounts(Map.ofEntries(Map.entry("_id", accountId)), "", 0, 0, "").get();
        if (accounts.size() != 0) {
            Map<String, String> errors = generateError(accountRequest.getClass());
            errors.put("_id", "account id not exist");
            throw new InvalidRequestException(errors, "account not exist!");
        }
        validate(accountRequest);
        Account oldAccount = accounts.get(0);
        Account updateAccount = objectMapper.convertValue(accountRequest, Account.class);

        updateAccount.set_id(new ObjectId(accountId));
        updateAccount.setJwt(oldAccount.getJwt());
        updateAccount.set_2FA(null);

        repository.insertOrUpdate(updateAccount);
    }

    @Override
    public void updatePassword(String accountId, String password) {
        List<Account> accounts = repository.getAccounts(Map.ofEntries(Map.entry("_id", accountId)), "", 0, 0, "").get();
        if (accounts.size() == 0) {
            throw new NotFoundException("Account not exist!");
        }
        Account account = accounts.get(0);
        String newPassword = bCryptPasswordEncoder().encode(password);

        account.setPassword(newPassword);
        account.setJwt(null);
        account.set_2FA(null);

        repository.insertOrUpdate(account);
    }

    @Override
    public void deleteAccount(String accountId) {
        List<Account> accounts = repository.getAccounts(Map.ofEntries(Map.entry("_id", accountId)), "", 0, 0, "").get();
        if (accounts.size() == 0) {
            throw new NotFoundException("Account not exist!");
        }

        Account oldAccount = accounts.get(0);

        oldAccount.setDeleted(true);
        oldAccount.setJwt(null);
        oldAccount.set_2FA(null);

        repository.insertOrUpdate(oldAccount);
    }

    @Override
    public Optional<AccountLoginResponse> login(AccountLoginRequest accountLoginRequest) {
        try {
            List<Account> accounts = repository
                    .getAccounts(Map.ofEntries(Map.entry("username", accountLoginRequest.getUsername())), "", 0, 0, "")
                    .get();
            if (accounts.size() == 0) {
                throw new ResourceNotFoundException("username or password is wrong!");
            }
            Account account = accounts.get(0);

            boolean checkPassword = bCryptPasswordEncoder().matches(accountLoginRequest.getPassword(),
                    account.getPassword());
            boolean checkValidateToken = jwtTokenProvider.validateToken(account.getJwt());

            if (checkPassword && checkValidateToken) {
                AccountLoginResponse accountLoginResponse = new AccountLoginResponse(account.getJwt(),
                        account.get_id().toString());

                return Optional.of(accountLoginResponse);

            } else if (checkPassword) {
                String jwt = jwtTokenProvider.generateToken(account.get_id().toString());
                AccountLoginResponse accountLoginResponse = new AccountLoginResponse(jwt, account.get_id().toString());

                account.setJwt(jwt);
                repository.insertOrUpdate(account);

                return Optional.of(accountLoginResponse);
            }
            throw new ResourceNotFoundException("username or password is wrong!");
        } catch (Exception e) {
            throw new ResourceNotFoundException("username or password is wrong!");
        }
    }

    @Override
    public void logout(Account account) {
        account.setJwt("");
        repository.insertOrUpdate(account);
    }

    @Override
    public Optional<AccountResponse> getAccountInfo(String accountId) {
        List<Account> acc = repository.getAccounts(Map.ofEntries(Map.entry("_id", accountId)), "", 0, 0, "").get();
        if (acc.size() == 0) {
            throw new NotFoundException("Not found this account!");
        }
        Account account = acc.get(0);
        System.out.println("123123123 tung  " + account);
        return Optional.of(accountUtils.generateAccountResponse(account));

    }
}
