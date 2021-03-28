package com.epam.EpamUniversityProject.web.servlet.admin;

import com.epam.EpamUniversityProject.model.User;
import com.epam.EpamUniversityProject.repository.dao.impl.UserDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.UserDao;
import com.epam.EpamUniversityProject.web.utils.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(Paths.URL_ADMIN_SET_BLOCKED_FOR_USER)
public class AdminSetBlockedForUserServlet extends HttpServlet {
    private final Logger log=Logger.getLogger(AdminSetBlockedForUserServlet.class);
    private UserDao dao;

    @Override
    public void init() throws ServletException {
        dao=new UserDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idAsString = req.getParameter("id");
        log.info("doGet: check if user parameter is specified");
        if (idAsString == null) {
            log.info("doGet: id wasn't specified");
            req.setAttribute("errorMsg", "id must be specified");
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }else {
            long id=Long.parseLong(idAsString);
            try {
                User user=dao.get(id);
                boolean newVal=!user.isBlocked();
                log.info("doGet: is blocked new value is "+newVal);
                user.setBlocked(newVal);
                dao.update(user);
                resp.sendRedirect(Paths.URL_ADMIN_USERS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
