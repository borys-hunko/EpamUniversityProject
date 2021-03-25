package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Application;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;

import java.sql.SQLException;
import java.util.List;

public class ApplicationDaoPostgreImpl implements ApplicationDao {
    @Override
    public void add(Application item) throws SQLException {

    }

    @Override
    public Application get(long id) throws SQLException {
        return null;
    }

    @Override
    public void update(Application newItem) throws SQLException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Application> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<Application> getByUser() {
        return null;
    }
}
