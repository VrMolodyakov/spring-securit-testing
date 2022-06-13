package com.project.finance.jwt;

import com.project.finance.jwt.parser.JwtParser;
import com.project.finance.jwt.validator.JwtTokenValidator;
import com.project.finance.services.ClientDetailsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ClientAuthTokenFilter extends OncePerRequestFilter {


    @Autowired
    private ClientDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(ClientAuthTokenFilter.class);
    private final String TOKEN_TYPE = "Bearer";
    private final int BEARER_LENGTH = 7;
    @Autowired
    private JwtTokenValidator tokenValidator;
    @Autowired
    private JwtParser tokenParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractJwtToken(request);
            if (jwt != null) {
                String login = tokenParser.getLoginFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(login);
                if(tokenValidator.validateJwtToken(jwt,userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String extractJwtToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_TYPE)) {
            return headerAuth.substring(BEARER_LENGTH, headerAuth.length());
        }
        return null;
    }
}
