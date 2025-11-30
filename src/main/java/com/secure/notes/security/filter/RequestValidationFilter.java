package com.secure.notes.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestValidationFilter extends OncePerRequestFilter {
    private static final String X_VALID_REQUEST = "X-Valid-Request";
    private static final String TRUE = "true";
    private static final String INVALID_REQUEST = "Invalid Request";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(X_VALID_REQUEST);
        if (header == null || !header.equals(TRUE)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALID_REQUEST);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
