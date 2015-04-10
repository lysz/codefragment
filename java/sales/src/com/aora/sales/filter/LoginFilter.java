package com.aora.sales.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{

    private String[] excludedUrls;
    
    public void destroy() {
      
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();
        
        String resourcePath = contextPath + "/resources/";
        String userLogin = contextPath + "/user/login";
        
        if (requestUri.startsWith(resourcePath) || requestUri.endsWith(userLogin)){
            filterChain.doFilter(request, response);
            return;
        }
        
        for (String url : excludedUrls) {
            if (requestUri.contains(url.trim())) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        
        HttpSession session = request.getSession(false);
        if (null == session || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        String excludes = config.getInitParameter("excludedURLs");

        if (excludes != null) {
            this.excludedUrls = excludes.split(",");
        }
    }

}
