package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Grade;
import com.epam.EpamUniversityProject.repository.dao.interfaces.GradeDao;
import com.epam.EpamUniversityProject.repository.util.Mapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GradeDaoPostgresImpl implements GradeDao {

    private static final String SQL_GET_ALL_BY_USER="select * from grade where user=?;";
    @Override
    public void add(Object item) throws IOException, SQLException {

    }

    @Override
    public Object get(long id) throws IOException, SQLException {
        return null;
    }

    @Override
    public void update(Object newItem) throws IOException, SQLException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List getAll() throws IOException, SQLException {
        return null;
    }

    @Override
    public List<Grade> getUsersGrades(long id) {
        return null;
    }

    private class GradeMapper implements Mapper<Grade>{

        @Override
        public Grade mapRow(ResultSet resultSet) {
            return null;
        }
    }
}
