package com.epam.EpamUniversityProject.web.filter;


import com.epam.EpamUniversityProject.model.Role;
import com.epam.EpamUniversityProject.web.utils.Paths;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * authorization filter
 * check if user log in
 **/

@WebFilter(value = {Paths.URL_SIGN_UP, Paths.URL_ADMIN})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            Role role = (Role) session.getAttribute("role");
            if (!Role.APPLICANT.equals(role)) {
                response.sendRedirect(Paths.URL_APPLICANT_HOME);
            } else {
                response.sendRedirect(Paths.URL_ADMIN_HOME);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
