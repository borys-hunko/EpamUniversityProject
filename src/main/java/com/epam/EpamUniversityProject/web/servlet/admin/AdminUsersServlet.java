package com.epam.EpamUniversityProject.web.servlet.admin;

import com.epam.EpamUniversityProject.model.Faculty;
import com.epam.EpamUniversityProject.model.User;
import com.epam.EpamUniversityProject.repository.dao.impl.UserDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.UserDao;
import com.epam.EpamUniversityProject.utils.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(Paths.URL_ADMIN_USERS)
public class AdminUsersServlet extends HttpServlet {
    private static final int ITEMS_PER_PAGE = 5;
    private final Logger log = Logger.getLogger(AdminUsersServlet.class);
    private UserDao dao;

    @Override
    public void init() throws ServletException {
        dao = new UserDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("doGet: retrieve user from db");
            List<User> users = dao.getAll();
            log.info("doGet: users are retrieved");
            PageManager<User> pageManager = new PageManager<>(users,
                    new UserSorter(),
                    new Paginator<>(ITEMS_PER_PAGE),
                    UserSorter.NAME);

            req.setAttribute("users", pageManager.getItemsForPage(req));
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ADMIN_USERS)
                    .forward(req, resp);
            log.info("doGet:success");
        } catch (SQLException e) {
            log.info("doGet:", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }
}
