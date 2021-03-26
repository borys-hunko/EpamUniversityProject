package com.epam.EpamUniversityProject.web.servlet.applicant;

import com.epam.EpamUniversityProject.model.Application;
import com.epam.EpamUniversityProject.model.Faculty;
import com.epam.EpamUniversityProject.model.User;
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

@WebServlet(Paths.URL_APPLICANT_HOME)
public class UserHomeServlet extends HttpServlet {
    private FacultyDao facultyDao;
    private ApplicationDao applicationDao;
    private final Logger log = Logger.getLogger(UserHomeServlet.class);

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
            List<Faculty> faculties = facultyDao.getAll();
            log.info("doGet->retrieve faculties");
            List<Application> applications = applicationDao.getUsersApplication(user.getId());
            List<Faculty> appliedFaculties=getAppliedFaculties(applications);
            req.setAttribute("faculties", faculties);
            req.setAttribute("applied",appliedFaculties);
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
    private List<Faculty> getAppliedFaculties(List<Application> applications){
        List<Faculty> faculties=new ArrayList<>();
        for (Application a:applications){
            faculties.add(a.getFaculty());
        }
        return faculties;
    }
}
