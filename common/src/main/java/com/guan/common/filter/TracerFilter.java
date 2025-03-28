package com.guan.common.filter;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.guan.common.constant.BaseConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@WebFilter
@Component
@Order(1)
public class TracerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = MDC.get(BaseConstants.LOG_TRACE);
            if (StrUtil.isBlank(traceId)) {
                traceId = IdUtil.fastSimpleUUID();
            }
            MDC.put(BaseConstants.LOG_TRACE, traceId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
