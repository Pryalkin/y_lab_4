package com.pryalkin.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pryalkin.model.ServerPrincipal;
import com.pryalkin.model.UserPrincipal;
import com.pryalkin.constant.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal){
        String[] claims = getClaimsFromUser(userPrincipal);
        String token = JWT.create()
                .withIssuer(SecurityConstant.BUG_LLC)
                .withAudience(SecurityConstant.BUG_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
        log.info(token);
        return token;
    }

    public String generateJwtTokenForServer(ServerPrincipal server){
        String[] claims = getClaimsFromServer(server);
        String token = JWT.create()
                .withIssuer(SecurityConstant.BUG_LLC)
                .withAudience(SecurityConstant.BUG_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(server.getUsername())
                .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
        log.info(token);
        return token;
    }

    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthToken;
    }

    public boolean isTokenValid(String username, String token){
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && isTokenExpired(verifier, token);
    }

    public String getSubject(String token){
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.after(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(SecurityConstant.BUG_LLC).build();
        } catch (JWTVerificationException ex){
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities().stream().map((auth)-> auth.getAuthority()).toArray(String[]::new);
    }

    private String[] getClaimsFromServer(ServerPrincipal serverPrincipal) {
        return serverPrincipal.getAuthorities().stream().map((auth)-> auth.getAuthority()).toArray(String[]::new);
    }

    public List<GrantedAuthority> getAuthoritiesServer(String token) {
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
