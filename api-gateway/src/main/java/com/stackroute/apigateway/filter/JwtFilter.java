package com.stackroute.apigateway.filter;

import com.stackroute.apigateway.aspect.LoggerAspect;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest servletRequest = (HttpServletRequest) request;
        final HttpServletResponse servletResponse = (HttpServletResponse) response;

        logger.info("Request URL: "+((HttpServletRequest) request).getRequestURI());
        logger.info("Request Header: "+((HttpServletRequest) request).getHeader("Authorization"));
        logger.info("Request Method: "+((HttpServletRequest) request).getMethod());

        final String authHeader = servletRequest.getHeader("Authorization");

        if(servletRequest.getMethod().equals("OPTIONS")) {
            servletResponse.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(servletRequest,servletResponse);
        } else if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid token");
        }

        String token = authHeader.substring(7);

        Claims claims = Jwts.parser().setSigningKey("quantumSecret").parseClaimsJws(token).getBody();
        servletRequest.setAttribute("claims", claims);
        chain.doFilter(servletRequest,servletResponse);

    }
}
