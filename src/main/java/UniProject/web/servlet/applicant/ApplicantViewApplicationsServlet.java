package UniProject.web.servlet.applicant;

import UniProject.model.Application;
import UniProject.model.Faculty;
import UniProject.model.User;
import UniProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import UniProject.repository.dao.impl.FacultyDaoPostgresImpl;
import UniProject.utils.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(Paths.URL_APPLICANT_APPLICATIONS)
public class ApplicantViewApplicationsServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(ApplicantViewApplicationsServlet.class);
    private ApplicationDaoPostgreImpl applicationDao;
    private FacultyDaoPostgresImpl facultyDao;

    @Override
    public void init() throws ServletException {
        applicationDao = new ApplicationDaoPostgreImpl();
        facultyDao=new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            List<Application> applications = applicationDao.getUsersApplication(user.getId());
            Map<Faculty,Integer> applicationsNumber=new HashMap<>();

            for (Application a: applications){
                applicationsNumber.put(a.getFaculty(),applicationDao.getApplicationsNumber(a.getFaculty().getId()));
            }

            req.setAttribute("applications", applications);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_APPLICANT_APPLICATIONS)
                    .forward(req, resp);
        } catch (SQLException e) {
            log.error("doGet", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }
}
