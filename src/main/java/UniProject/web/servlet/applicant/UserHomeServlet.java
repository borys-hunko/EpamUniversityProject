package UniProject.web.servlet.applicant;

import UniProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import UniProject.repository.dao.impl.FacultyDaoPostgresImpl;
import UniProject.model.Application;
import UniProject.model.Faculty;
import UniProject.model.User;
import UniProject.repository.dao.interfaces.ApplicationDao;
import UniProject.repository.dao.interfaces.FacultyDao;
import UniProject.utils.FacultySorter;
import UniProject.utils.PageManager;
import UniProject.utils.Paginator;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(Paths.URL_APPLICANT_HOME)
public class UserHomeServlet extends HttpServlet {
    private FacultyDao facultyDao;
    private ApplicationDao applicationDao;
    private final Logger log = Logger.getLogger(UserHomeServlet.class);
    private static final int ITEMS_PER_PAGE = 3;

    @Override
    public void init() throws ServletException {
        facultyDao = new FacultyDaoPostgresImpl();
        applicationDao = new ApplicationDaoPostgreImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        log.info("doGet->users id=" + user.getId());
        try {
            List<Faculty> allFaculties = facultyDao.getAll();
            log.info("doGet->retrieve faculties");
            List<Application> applications = applicationDao.getUsersApplication(user.getId());
            //get applied faculties to mark them as "applied" on the page
            List<Faculty> appliedFaculties = getAppliedFaculties(applications);
            PageManager<Faculty> pageManager = new PageManager<Faculty>(allFaculties,
                    new FacultySorter(),
                    new Paginator<Faculty>(ITEMS_PER_PAGE),
                    FacultySorter.NAME);

            req.setAttribute("faculties",pageManager.getItemsForPage(req));
            req.setAttribute("applied", appliedFaculties);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_APPLICANT_HOME)
                    .forward(req, resp);
        } catch (SQLException e) {
            log.error("doGet->", e);
            resp.sendRedirect(Paths.URL_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
    }

    //extract ot another class
    private List<Faculty> getAppliedFaculties(List<Application> applications) {
        List<Faculty> faculties = new ArrayList<>();
        for (Application a : applications) {
            faculties.add(a.getFaculty());
        }
        return faculties;
    }
}
