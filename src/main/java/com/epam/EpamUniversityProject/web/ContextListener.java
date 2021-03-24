package com.epam.EpamUniversityProject.web;

import com.epam.EpamUniversityProject.repository.util.DBManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }
}
