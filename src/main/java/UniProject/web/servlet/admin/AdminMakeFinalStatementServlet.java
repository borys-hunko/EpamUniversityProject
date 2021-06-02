package UniProject.web.servlet.admin;

import UniProject.model.Application;
import UniProject.model.Faculty;
import UniProject.model.Statement;
import UniProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import UniProject.repository.dao.impl.FacultyDaoPostgresImpl;
import UniProject.repository.dao.interfaces.ApplicationDao;
import UniProject.repository.dao.interfaces.FacultyDao;
import UniProject.utils.Paths;

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

            applicationDao.updateStatusForAllApplications(statement.makeFinalStatement());
            resp.sendRedirect(Paths.URL_ADMIN_HOME);
        } catch (SQLException e) {
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req,resp);
        }
    }
}