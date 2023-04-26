package com.example.trainerapi.security.filters;

import com.example.trainerapi.security.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class RequestFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger("Log");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean headerValid = true;

        String username = "EMPTY";

        String header = request.getHeader("Authorization");

        if(header == null){
            headerValid = false;
        }else {
            if(!header.startsWith("Bearer eyJ")){
                headerValid = false;
            }
            String token = header.substring(7);
            username = JwtTokenUtil.getUsername(token);
        }

        logger.info("Request: {} || Method: {} || Valid auth header present: {} || User: {}", request.getRequestURI(), request.getMethod(),headerValid, username);

        filterChain.doFilter(request, response);
    }
}
