package com.epam.EpamUniversityProject.web.servlet;

import com.epam.EpamUniversityProject.model.Role;
import com.epam.EpamUniversityProject.model.User;
import com.epam.EpamUniversityProject.repository.dao.impl.UserDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.UserDao;
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

@WebServlet(Paths.URL_SIGN_UP)
public class SignUpServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(SignUpServlet.class);
    private UserDao dao;

    @Override
    public void init() throws ServletException {
        dao = new UserDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext()
                .getRequestDispatcher(Paths.PAGE_SIGN_UP)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String fathersName = req.getParameter("fathersName");
        String region = req.getParameter("region");
        String city = req.getParameter("city");
        String school = req.getParameter("school");

        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                fathersName == null || fathersName.isEmpty() ||
                region == null || region.isEmpty() ||
                city == null || city.isEmpty() ||
                school == null || school.isEmpty()) {
            req.setAttribute("error", "All fields must be filled!");
            doGet(req, resp);
        }
        try {
            User user = dao.getByEmail(email);
            if (user != null) {
                req.setAttribute("error", "there is such user!");
                doGet(req, resp);
            }
            user = new User();
            user.setEmail(email)
                    .setPassword(password)
                    .setRole(Role.APPLICANT)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setFathersName(fathersName)
                    .setRegion(region)
                    .setCity(city)
                    .setSchool(school);
            dao.add(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(Paths.URL_APPLICANT_HOME);
        } catch (SQLException e) {
            log.error("doPost->", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }
}
