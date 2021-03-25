package com.epam.EpamUniversityProject.web.servlet.applicant;

import com.epam.EpamUniversityProject.model.Faculty;
import com.epam.EpamUniversityProject.repository.dao.impl.FacultyDaoPostgresImpl;
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
import java.util.List;

@WebServlet(Paths.URL_APPLICANT_HOME)
public class UserHomeServlet extends HttpServlet {
    private FacultyDao facultyDao;
    private Logger log=Logger.getLogger(UserHomeServlet.class);

    @Override
    public void init() throws ServletException {
        facultyDao = new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long userId = (long) session.getAttribute("user");
        log.info("doGet->users id="+userId);
        try {
            List<Faculty> faculties = facultyDao.getAll();
            log.info("doGet->retrieve faculties");
            req.setAttribute("faculties", faculties);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_APPLICANT_HOME)
                    .forward(req, resp);
        } catch (SQLException e) {
            log.error("doGet->",e);
            resp.sendRedirect(Paths.URL_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
    }
}
