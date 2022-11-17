package com.tungjj.user.jwt;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tungjj.user.exception.ResourceNotFoundException;
import com.tungjj.user.repository.accountRepository.Account;
import com.tungjj.user.repository.accountRepository.AccountRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
    @Autowired
    private AccountRepository repository;

    @Value("${spring.key.jwt}")
    private String JWT_SECRET;

    public String generateToken(String id) {
        Date now = new Date();
        long JWT_EXPIRATION = 1 * 24 * 60 * 60 * 1000L;
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Optional<Account> getAccountFromToken(String token) {
        String accountId = JwtUtils.getUserIdFromJwt(token, JWT_SECRET);
        List<Account> accounts = repository.getAccounts(Map.ofEntries(Map.entry("_id", accountId)), "", 0, 0, "").get();
        if (accounts.size() == 0) {
            throw new ResourceNotFoundException("Not found user!");

        }
        return Optional.of(accounts.get(0));
    }

    public boolean validateToken(String jwt) {
        if (StringUtils.hasText(jwt)) {
            try {
                Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwt);
                return true;
            } catch (MalformedJwtException e) {
                log.error("Invalid JWT Token");
            } catch (ExpiredJwtException e) {
                log.error("Expired JWT Token or Deprecated JWT Token");
            } catch (UnsupportedJwtException e) {
                log.error("Unsupported JWT Token");
            } catch (IllegalArgumentException e) {
                log.error("JWT claims is empty string");
            } catch (SignatureException e) {
                log.error("JWT signature does not match locally computed signature!");
            }
        }
        return false;
    }

    // public String getJwtFromRequest(HttpServletRequest request) {
    // return JwtUtils.getJwtFromRequest(request);
    // }
}
