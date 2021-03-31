package com.epam.EpamUniversityProject.web.filter;


import com.epam.EpamUniversityProject.model.Role;
import com.epam.EpamUniversityProject.model.User;
import com.epam.EpamUniversityProject.utils.Paths;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * handle '/' url
 * */
@WebFilter(Paths.URL_NONE)
public class RootFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        if (user==null){
            response.sendRedirect(Paths.URL_LOG_IN);
        }else if (user.getRole().equals(Role.ADMIN)){
            response.sendRedirect(Paths.URL_ADMIN_HOME);
        }else {
            response.sendRedirect(Paths.URL_APPLICANT_HOME);
        }
    }

    @Override
    public void destroy() {

    }
}
