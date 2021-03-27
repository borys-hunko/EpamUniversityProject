package com.epam.EpamUniversityProject.web.servlet.admin;

import com.epam.EpamUniversityProject.repository.dao.impl.FacultyDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;
import com.epam.EpamUniversityProject.web.utils.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(Paths.URL_ADMIN_DELETE_FACULTY)
public class AdminDeleteFacultyServlet extends HttpServlet {
    private FacultyDao dao;
    private final Logger log = Logger.getLogger(AdminDeleteFacultyServlet.class);

    @Override
    public void init() throws ServletException {
        dao = new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idAsString = req.getParameter("id");
        log.info("doGet: check if id was specified");
        if (idAsString == null) {
            log.info("doGet: id wasn't specified");
            req.setAttribute("errorMsg", "id must be specified");
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        } else {
            try {
                log.info("doGet: delete faculty id=" + idAsString);
                dao.delete(Long.parseLong(idAsString));
                log.info("doGet: faculty has been deleted");
                resp.sendRedirect(Paths.URL_ADMIN_FACULTIES);
            } catch (SQLException e) {
                log.info("doGet:", e);
                req.getServletContext()
                        .getRequestDispatcher(Paths.PAGE_ERROR)
                        .forward(req, resp);
            }
        }
    }
}
