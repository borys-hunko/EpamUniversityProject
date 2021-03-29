package com.epam.EpamUniversityProject.web.servlet.applicant;

import com.epam.EpamUniversityProject.model.Application;
import com.epam.EpamUniversityProject.model.User;
import com.epam.EpamUniversityProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.utils.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(Paths.URL_APPLICANT_APPLICATIONS)
public class ApplicantViewApplicationsServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(ApplicantViewApplicationsServlet.class);
    private ApplicationDao applicationDao;

    @Override
    public void init() throws ServletException {
        applicationDao = new ApplicationDaoPostgreImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            List<Application> applications = applicationDao.getUsersApplication(user.getId());
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
