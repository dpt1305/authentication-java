package com.tungjj.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tungjj.user.service.accountService.AccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.tungjj.user.dto.accountDTO.AccountLoginRequest;
import com.tungjj.user.dto.accountDTO.AccountLoginResponse;
import com.tungjj.user.dto.accountDTO.AccountResponse;
import com.tungjj.user.dto.commonDTO.CommonResponse;
import com.tungjj.user.repository.accountRepository.Account;

@RestController
@RequestMapping(value = "account-controller")
public class AccountController extends AbstractController<AccountService> {
    @PostMapping(value = "register")
    public ResponseEntity<CommonResponse<String>> register(@RequestBody AccountLoginRequest accountLoginRequest) {
        service.createNewAccount(accountLoginRequest);
        return new ResponseEntity<CommonResponse<String>>(
                new CommonResponse<String>(true, null, "Register successfully!", HttpStatus.OK.value()),
                null,
                HttpStatus.OK.value());
    }

    @PostMapping(value = "login")
    public ResponseEntity<CommonResponse<AccountLoginResponse>> login(
            @RequestBody() AccountLoginRequest accountLoginRequest) {
        return response(service.login(accountLoginRequest), "Login success!");
    }

    @PostMapping(value = "log-out")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CommonResponse<String>> logout(HttpServletRequest request) {
        Account account = validateTokenAndGetAccount(request).get();
        service.logout(account);
        return new ResponseEntity<CommonResponse<String>>(
                new CommonResponse<String>(true, null, "Logout successfully!",
                        HttpStatus.OK.value()),
                null,
                HttpStatus.OK.value());
    }

    @GetMapping(value = "get-account-information")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CommonResponse<AccountResponse>> getAccountInfo(HttpServletRequest request) {

        Account account = validateTokenAndGetAccount(request).get();
        System.out.println(account);
        System.out.println("123 " + account.get_id().toString());
        // return new ResponseEntity<CommonResponse<Account>>(
        // new CommonResponse<Account>(true, account, null, HttpStatus.OK.value()),
        // null,
        // HttpStatus.OK.value());
        return response(service.getAccountInfo(account.get_id().toString()), "Account Information!");
    }
}
