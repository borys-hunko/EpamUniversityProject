package com.epam.EpamUniversityProject.web.servlet.admin;

import com.epam.EpamUniversityProject.model.Faculty;
import com.epam.EpamUniversityProject.repository.dao.impl.FacultyDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;
import com.epam.EpamUniversityProject.utils.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(Paths.URL_ADMIN_FACULTIES)
public class AdminFacultiesServlet extends HttpServlet {
    private Logger log = Logger.getLogger(AdminFacultiesServlet.class);
    private FacultyDao dao;

    @Override
    public void init() throws ServletException {
        dao = new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Faculty> faculties = dao.getAll();
            log.info("retrieve users from db");
            req.setAttribute("faculties",faculties);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ADMIN_FACULTIES)
                    .forward(req, resp);
            log.info("faculties retrieved,size="+faculties.size());
        } catch (SQLException e) {
            log.error("doGet: ", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }
}
