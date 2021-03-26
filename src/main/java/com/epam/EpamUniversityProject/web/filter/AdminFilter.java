package com.epam.EpamUniversityProject.web.filter;

import com.epam.EpamUniversityProject.model.Role;
import com.epam.EpamUniversityProject.web.utils.Paths;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = {Paths.URL_ADMIN})
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session=request.getSession();
        Role role= (Role) session.getAttribute("role");
        if (!Role.ADMIN.equals(role)){
            request.setAttribute("errorMsg","You have no access.\nLog in as admin");
            request.getServletContext().getRequestDispatcher(Paths.PAGE_ERROR).forward(request,response);
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
