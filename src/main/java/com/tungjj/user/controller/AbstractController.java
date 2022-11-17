package com.tungjj.user.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tungjj.user.dto.commonDTO.CommonResponse;
import com.tungjj.user.exception.ResourceNotFoundException;
import com.tungjj.user.exception.UnauthorizationException;
import com.tungjj.user.jwt.JwtTokenProvider;
import com.tungjj.user.jwt.JwtUtils;
import com.tungjj.user.repository.accountRepository.Account;

public abstract class AbstractController<s> {
   @Autowired
   protected s service;

   @Autowired
   protected JwtTokenProvider jwtTokenProvider;

   protected <T> ResponseEntity<CommonResponse<T>> response(Optional<T> response, String successMessage) {
      return new ResponseEntity<>(new CommonResponse<>(true, response.orElseThrow(() -> {
         throw new ResourceNotFoundException("Resource not found");
      }), successMessage, HttpStatus.OK.value()), HttpStatus.OK);
   }

   protected Optional<Account> validateTokenAndGetAccount(HttpServletRequest request) {
      String jwt = JwtUtils.getJwtFromRequest(request);
      if (jwtTokenProvider.validateToken(jwt) == false) {
         throw new UnauthorizationException("Unauthorization!");

      } else {
         return jwtTokenProvider.getAccountFromToken(jwt);
      }
   }
}
