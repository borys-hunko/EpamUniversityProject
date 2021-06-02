package UniProject.web.servlet.applicant;

import UniProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import UniProject.utils.Paths;
import UniProject.model.Application;
import UniProject.model.User;
import UniProject.repository.dao.interfaces.ApplicationDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(Paths.URL_APPLICANT_DELETE_APPLICATION)
public class ApplicantDeleteApplicationServlet extends HttpServlet {
    private Logger logger=Logger.getLogger(ApplicantDeleteApplicationServlet.class);
    private ApplicationDao dao;

    @Override
    public void init() throws ServletException {
        dao=new ApplicationDaoPostgreImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idAsString=req.getParameter("id");
        long id=Long.parseLong(idAsString);
        HttpSession session=req.getSession();
        User user= (User) session.getAttribute("user");
        try {
            Application application=dao.get(id);
            if (!application.getApplicant().equals(user)){
                logger.error("doGet: applications doesn't belong to user");
                req.setAttribute("errorMsg","you cannot delete this application");
                req.getServletContext()
                        .getRequestDispatcher(Paths.PAGE_ERROR)
                        .forward(req,resp);
            }else {
                dao.delete(id);
                logger.info("doGet: delete");
                resp.sendRedirect(Paths.URL_APPLICANT_APPLICATIONS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
