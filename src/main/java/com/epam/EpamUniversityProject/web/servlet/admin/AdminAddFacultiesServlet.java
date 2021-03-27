package com.epam.EpamUniversityProject.web.servlet.admin;

import com.epam.EpamUniversityProject.model.Faculty;
import com.epam.EpamUniversityProject.model.Subject;
import com.epam.EpamUniversityProject.repository.dao.impl.FacultyDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.impl.SubjectDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.SubjectDao;
import com.epam.EpamUniversityProject.web.utils.Paths;
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

@WebServlet(Paths.URL_ADMIN_ADD_FACULTY)
public class AdminAddFacultiesServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(AdminAddFacultiesServlet.class);
    private SubjectDao subjectDao;
    private FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        subjectDao = new SubjectDaoPostgresImpl();
        facultyDao = new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Subject> subjects = subjectDao.getAll();
            req.setAttribute("subjects", subjects);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ADMIN_ADD_FACULTY)
                    .forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
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
            for (String idAsString : subjectIDs) {
                long subjectId = Long.parseLong(idAsString);
                subjects.add(new Subject().setId(subjectId));
            }
            faculty.setName(facultyName)
                    .setBudgetPlaces(budgetPlaces)
                    .setTotalPlaces(totalPlaces)
                    .setRequiredSubjects(subjects);
            facultyDao.add(faculty);
            log.info("doPost: faculty was successfully added");
            resp.sendRedirect(Paths.URL_ADMIN_FACULTIES);
        } catch (SQLException e) {
            log.error("doPost", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }
}
