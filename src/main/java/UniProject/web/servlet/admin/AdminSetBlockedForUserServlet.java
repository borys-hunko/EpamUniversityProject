package UniProject.web.servlet.admin;

import UniProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import UniProject.repository.dao.impl.UserDaoPostgresImpl;
import UniProject.repository.dao.interfaces.ApplicationDao;
import UniProject.repository.dao.interfaces.UserDao;
import UniProject.utils.Paths;
import UniProject.model.Application;
import UniProject.model.ApplicationStatus;
import UniProject.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(Paths.URL_ADMIN_SET_BLOCKED_FOR_USER)
public class AdminSetBlockedForUserServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(AdminSetBlockedForUserServlet.class);
    private UserDao userDao;
    private ApplicationDao applicationDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDaoPostgresImpl();
        applicationDao = new ApplicationDaoPostgreImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idAsString = req.getParameter("id");
        log.info("doGet: check if user parameter is specified");
        if (idAsString == null) {
            log.info("doGet: id wasn't specified");
            req.setAttribute("errorMsg", "id must be specified");
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        } else {
            long id = Long.parseLong(idAsString);
            try {
                User user = userDao.get(id);
                List<Application> applications = applicationDao.getUsersApplication(user.getId());
                //if we block user,his applications will be rejected
                //otherwise their status will become NOT_PROCEED
                if (user.isBlocked()) {
                    for (Application application : applications) {
                        application.setStatus(ApplicationStatus.NOT_PROCEED);
                    }
                } else {
                    for (Application application : applications) {
                        application.setStatus(ApplicationStatus.NOT_APPLIED);
                    }
                }
                boolean newVal = !user.isBlocked();
                log.info("doGet: is blocked new value is " + newVal);
                user.setBlocked(newVal);
                userDao.update(user);
                applicationDao.updateStatusForAllApplications(applications);
                resp.sendRedirect(Paths.URL_ADMIN_USERS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
