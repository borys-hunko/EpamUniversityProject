package UniProject.web.servlet.admin;

import UniProject.repository.dao.impl.FacultyDaoPostgresImpl;
import UniProject.utils.FacultySorter;
import UniProject.utils.PageManager;
import UniProject.utils.Paginator;
import UniProject.utils.Paths;
import UniProject.model.Faculty;
import UniProject.repository.dao.interfaces.FacultyDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(Paths.URL_ADMIN_FACULTIES)
public class AdminFacultiesServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(AdminFacultiesServlet.class);
    private FacultyDao dao;
    private static final int ITEMS_PER_PAGE=3;

    @Override
    public void init() throws ServletException {
        dao = new FacultyDaoPostgresImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Faculty> faculties = dao.getAll();
            log.info("retrieve users from db");
            PageManager<Faculty> pageManager = new PageManager<>(faculties,
                    new FacultySorter(),
                    new Paginator<>(ITEMS_PER_PAGE),
                    FacultySorter.NAME);

            req.setAttribute("faculties",pageManager.getItemsForPage(req));
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ADMIN_FACULTIES)
                    .forward(req, resp);
            log.info("faculties retrieved,size="+faculties.size());
        } catch (SQLException e) {
            log.error("doGet: ", e);
            req.getServletContext()
                    .getRequestDispatcher(Paths.PAGE_ERROR)
                    .forward(req, resp);
        }
    }
}
