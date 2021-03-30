package com.epam.EpamUniversityProject.web.servlet.admin;

import com.epam.EpamUniversityProject.model.Application;
import com.epam.EpamUniversityProject.model.Faculty;
import com.epam.EpamUniversityProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.utils.FacultySorter;
import com.epam.EpamUniversityProject.utils.Paths;
import com.epam.EpamUniversityProject.utils.Sorter;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(Paths.URL_ADMIN_APPLICATIONS_VIEW)
public class AdminApplicationsViewServlet extends HttpServlet {
    private ApplicationDao applicationDao;
    private Logger log=Logger.getLogger(AdminApplicationsViewServlet.class);

    @Override
    public void init() throws ServletException {
        applicationDao=new ApplicationDaoPostgreImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Application> applications=applicationDao.getAll();
            String sort=req.getParameter("sort");
            Sorter<Application> sorter=null;
            if (sort==null){
                sort=FacultySorter.NAME;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
