package com.ToDo.todoApp.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LoggerFilter implements Filter {
    private  static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest){
            var httpRequest = (HttpServletRequest) servletRequest;
            logger.info("[Do Filter]" + httpRequest.getMethod()+""+httpRequest.getRequestURI());
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

}
