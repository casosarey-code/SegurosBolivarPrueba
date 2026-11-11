package com.example.polizas.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String API_KEY_VALUE = "123456";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        // Permitir health-check (opcional)
        if (path.equals("/actuator/health")) {
            chain.doFilter(request, response);
            return;
        }

        String key = req.getHeader(API_KEY_HEADER);
        if (key == null || !key.equals(API_KEY_VALUE)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"x-api-key inválido o ausente\"}");
            return;
        }
        chain.doFilter(request, response);
    }
}