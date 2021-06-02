package UniProject.web.servlet.admin;

import UniProject.model.Faculty;
import UniProject.model.Subject;
import UniProject.repository.dao.impl.FacultyDaoPostgresImpl;
import UniProject.repository.dao.impl.SubjectDaoPostgresImpl;
import UniProject.repository.dao.interfaces.FacultyDao;
import UniProject.repository.dao.interfaces.SubjectDao;
import UniProject.utils.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(Paths.URL_ADMIN_UPDATE_FACULTY)
public class AdminUpdateFacultyServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(AdminUpdateFacultyServlet.class);
    private FacultyDao facultyDao;
    private SubjectDao subjectDao;
    private long facultyId;
    @Override
    public void init() throws ServletException {
        facultyDao = new FacultyDaoPostgresImpl();
        subjectDao = new SubjectDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet:retrieve id from path");
        String idAsString = req.getParameter("id");
        if (idAsString == null) {
            log.info("doGet: no parameters in path");
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
            return;
        }
        facultyId = Long.parseLong(idAsString);
        log.debug("doGet:id=" + facultyId);
        try {
            Faculty faculty = facultyDao.get(facultyId);
            List<Subject> subjects = subjectDao.getAll();
            req.setAttribute("faculty", faculty);
            req.setAttribute("subjects", subjects);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ADMIN_UPDATE_FACULTY)
                    .forward(req, resp);
            log.info("doGet: forward to update page");
        } catch (SQLException e) {
            log.error("doGet", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String facultyName = req.getParameter("name");
        String budgetPlacesAsString = req.getParameter("budgetPlaces");
        String totalPlacesAsString = req.getParameter("totalPlaces");
        String[] subjectIDs = req.getParameterValues("subjects");
        log.info("doPost: retrieve parameters");
        if (facultyName == null || facultyName.isEmpty() ||
                budgetPlacesAsString == null || budgetPlacesAsString.isEmpty() ||
                totalPlacesAsString == null || totalPlacesAsString.isEmpty()) {
            log.info("doPost: some of parameters are empty");
            req.setAttribute("errorMsg", "fields must not be empty");
            doGet(req, resp);
            return;
        } else if (subjectIDs == null || subjectIDs.length == 0) {
            log.info("doPost: there is any chosen subject");
            req.setAttribute("errorMsg", "choose at least one subject");
            doGet(req, resp);
            return;
        }
        log.info("doPost: parameters are not empty");
        int budgetPlaces = Integer.parseInt(budgetPlacesAsString);
        int totalPlaces = Integer.parseInt(totalPlacesAsString);
        Faculty faculty = new Faculty();
        List<Subject> subjects = new ArrayList<>();
        try {
            for (String subjectIdAsString : subjectIDs) {
                long subjectId = Long.parseLong(subjectIdAsString);
                subjects.add(new Subject().setId(subjectId));
            }
            faculty.setName(facultyName)
                    .setBudgetPlaces(budgetPlaces)
                    .setTotalPlaces(totalPlaces)
                    .setRequiredSubjects(subjects)
                    .setId(facultyId);
            facultyDao.update(faculty);
            log.info("doPost: faculty was successfully updated");
            resp.sendRedirect(Paths.URL_ADMIN_FACULTIES);
        } catch (SQLException e) {
            log.error("doPost", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }
}
