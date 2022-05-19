package com.example.school.utils;

import com.example.school.login.LoginService;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    /**
     * If the request has a JWT token, validate it and set up the Spring Authentication object
     *
     * @param request The request object.
     * @param response The response object.
     * @param chain The FilterChain object represents the chain of filters that will be applied to the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (existeJWTToken(request, response)) {
                Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    /**
     * It takes the token from the header, removes the prefix, and then parses the token using the secret key
     *
     * @param request The request object that contains the token.
     * @return The claims of the token.
     */
    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(LoginService.HEADER).replace(LoginService.PREFIX, "");
        return Jwts.parser().setSigningKey(LoginService.getSecretJWT().getBytes()).parseClaimsJws(jwtToken).getBody();
    }


    /**
     * It takes the claims from the JWT and uses them to create a new UsernamePasswordAuthenticationToken
     *
     * @param claims The claims object is the object that contains the information about the user.
     */
    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    /**
     * If the request has a header named "Authorization" and it starts with "Bearer ", then return true
     *
     * @param request The request object
     * @param res The response object
     * @return A boolean value.
     */
    public static boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(LoginService.HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(LoginService.PREFIX))
            return false;
        return true;
    }
}
