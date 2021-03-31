package com.epam.EpamUniversityProject.web.servlet.admin;

import com.epam.EpamUniversityProject.model.Application;
import com.epam.EpamUniversityProject.model.Faculty;
import com.epam.EpamUniversityProject.model.Statement;
import com.epam.EpamUniversityProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import com.epam.EpamUniversityProject.repository.dao.impl.FacultyDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;
import com.epam.EpamUniversityProject.utils.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(Paths.URL_ADMIN_FINAL_STATEMENT)
public class AdminMakeFinalStatementServlet extends HttpServlet {
    private ApplicationDao applicationDao;
    private FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        applicationDao = new ApplicationDaoPostgreImpl();
        facultyDao = new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Application> applications = applicationDao.getAll();
            List<Faculty> faculties = facultyDao.getAll();
            Statement statement = new Statement(faculties, applications);
            statement.makeFinalStatement();
            applicationDao.updateStatusForAllApplications(statement.getApplications());
            resp.sendRedirect(Paths.URL_ADMIN_APPLICATIONS_VIEW);
        } catch (SQLException e) {
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req,resp);
        }
    }
}
