package com.epam.EpamUniversityProject.web.filter;

import com.epam.EpamUniversityProject.model.User;
import com.epam.EpamUniversityProject.repository.dao.impl.UserDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.UserDao;
import com.epam.EpamUniversityProject.utils.Paths;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebFilter(value = {Paths.URL_ADMIN, Paths.URL_APPLICANT})
public class BlockFilter implements Filter {
    private UserDao dao;
    private static final Logger log = Logger.getLogger(BlockFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dao = new UserDaoPostgresImpl();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(Paths.URL_LOG_IN);
            return;
        }

        try {
            //get user from db to check if he is blocked
            //because he an be blocked during session
            User checkUser = dao.get(user.getId());
            log.info("doFilter: retrieve user from db");
            if (checkUser.isBlocked()) {
                log.info("doFilter: user is blocked");
                request.setAttribute("errorMsg", "you are blocked");
                request.getServletContext()
                        .getRequestDispatcher(Paths.PAGE_ERROR)
                        .forward(request, response);
            }
        } catch (SQLException e) {
            log.debug("doFilter->", e);
            request.setAttribute("errorMsg", "oops... something went wrong");
            request.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(request, response);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
