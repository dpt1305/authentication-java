package com.tungjj.user.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtils {
    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static String getUserIdFromJwt(String jwt, String JWT_SECRET) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }
}
