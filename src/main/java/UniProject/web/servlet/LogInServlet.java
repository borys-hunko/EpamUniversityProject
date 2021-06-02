package UniProject.web.servlet;

import UniProject.model.Role;
import UniProject.model.User;
import UniProject.repository.dao.impl.UserDaoPostgresImpl;
import UniProject.repository.dao.interfaces.UserDao;
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

@WebServlet(value = {Paths.URL_LOG_IN})
public class LogInServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(LogInServlet.class);
    private UserDao dao;

    @Override
    public void init() throws ServletException {
        dao = new UserDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext()
                .getRequestDispatcher("/jsp/logIn.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User user = dao.getByEmail(email);
            String errorMsg = "";
            if (user == null || !user.getPassword().equals(password)) {
                errorMsg = "incorrect email/password";
                req.setAttribute("error", errorMsg);
                doGet(req, resp);
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                String path;
                if (user.getRole().equals(Role.ADMIN)){
                    path= Paths.URL_ADMIN_HOME;
                }else {
                    path=Paths.URL_APPLICANT_HOME;
                }
                resp.sendRedirect(path);
            }
        } catch (IOException | SQLException e) {
            log.debug(e.getMessage() + " " + e.getClass().getName());
            resp.sendRedirect(Paths.URL_ERROR);
        }
    }
}
