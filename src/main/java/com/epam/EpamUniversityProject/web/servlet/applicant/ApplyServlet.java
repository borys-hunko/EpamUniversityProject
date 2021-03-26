package com.epam.EpamUniversityProject.web.servlet.applicant;

import com.epam.EpamUniversityProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.web.utils.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Paths.PAGE_APPLICANT_APPLY)
public class ApplyServlet extends HttpServlet {
    private final Logger log=Logger.getLogger(ApplyServlet.class);
    private ApplicationDao dao;

    @Override
    public void init() throws ServletException {
        dao=new ApplicationDaoPostgreImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long facultyId=Long.getLong(req.getParameter("facId"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
