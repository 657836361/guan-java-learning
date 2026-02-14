package com.guan.common.filter;

import com.guan.common.constant.BaseConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@WebFilter
@Component
@Order(1)
public class TracerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = MDC.get(BaseConstants.LOG_TRACE);
            if (StringUtils.isBlank(traceId)) {
                traceId = UUID.randomUUID().toString();
            }
            MDC.put(BaseConstants.LOG_TRACE, traceId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
