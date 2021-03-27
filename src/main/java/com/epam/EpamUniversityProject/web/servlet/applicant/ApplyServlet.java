package com.epam.EpamUniversityProject.web.servlet.applicant;

import com.epam.EpamUniversityProject.model.Faculty;
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
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(Paths.URL_APPLICANT_APPLY)
public class ApplyServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(ApplyServlet.class);
    private ApplicationDao applicationDao;
    private FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        applicationDao = new ApplicationDaoPostgreImpl();
        facultyDao = new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String facultyIdAsString =req.getParameter("facId");
//        long facultyId=(long)req.getAttribute("facId");
        long facultyId=Long.parseLong(facultyIdAsString);
        try {
            Faculty faculty = facultyDao.get(facultyId);
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
        Object o=req.getParameter("results");
        log.info(o.toString());
        doGet(req,resp);
    }
}
