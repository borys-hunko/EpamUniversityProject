package com.epam.EpamUniversityProject.web.servlet.applicant;

import com.epam.EpamUniversityProject.model.*;
import com.epam.EpamUniversityProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import com.epam.EpamUniversityProject.repository.dao.impl.FacultyDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;
import com.epam.EpamUniversityProject.web.utils.Paths;
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

@WebServlet(Paths.URL_APPLICANT_APPLY)
public class ApplyServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(ApplyServlet.class);
    private ApplicationDao applicationDao;
    private FacultyDao facultyDao;
    private Faculty faculty;

    @Override
    public void init() throws ServletException {
        applicationDao = new ApplicationDaoPostgreImpl();
        facultyDao = new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String facultyIdAsString = req.getParameter("facId");
//        long facultyId=(long)req.getAttribute("facId");
        long facultyId = Long.parseLong(facultyIdAsString);
        try {
            faculty = facultyDao.get(facultyId);
            req.setAttribute("faculty", faculty);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_APPLICANT_APPLY)
                    .forward(req, resp);
        } catch (SQLException e) {
            log.error("doGet:", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String priorityAsString = req.getParameter("priority");
        String educationTypeAsString = req.getParameter("education_type");
        String[] resultsAsString = req.getParameterValues("results");
        if (priorityAsString == null || priorityAsString.isEmpty() ||
                resultsAsString == null || resultsAsString.length != faculty.getRequiredSubjects().size()) {
            log.info("doPost: some fields are empty");
            req.setAttribute("errorMsg", "ALL fields must be filled!!!");
            doGet(req, resp);
        } else {
            int priority = Integer.parseInt(priorityAsString);
            HttpSession session = req.getSession();
            log.info("doPost: application mapping started");
            List<Grade> grades = mapGrades(resultsAsString);
            Application application = new Application();
            User user = (User) session.getAttribute("user");
            application.setApplicant(user)
                    .setFaculty(faculty)
                    .setTypeOfEducation(TypeOfEducation.valueOf(educationTypeAsString))
                    .setStatus(ApplicationStatus.NOT_PROCEED)
                    .setGrades(grades)
                    .setPriority(priority);
            log.info("doPost: mapped application");
            try {
                log.info("add application");
                applicationDao.add(application);
                resp.sendRedirect(Paths.URL_APPLICANT_HOME);
            } catch (SQLException e) {
                log.error("doPost: ", e);
                req.getServletContext()
                        .getRequestDispatcher(Paths.PAGE_ERROR)
                        .forward(req, resp);
            }
        }
    }

    private List<Grade> mapGrades(String[] resultsAsString) {
        log.info("mapGrades: grades mapping started");
        List<Grade> grades = new ArrayList<>();
        for (int i = 0; i < resultsAsString.length; ++i) {
            Grade grade = new Grade();
            grade.setSubject(faculty.getRequiredSubjects().get(i));
            int score = Integer.parseInt(resultsAsString[i]);
            grade.setScore(score);
            grades.add(grade);
        }
        return grades;
    }
}
