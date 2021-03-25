package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Grade;
import com.epam.EpamUniversityProject.model.Subject;
import com.epam.EpamUniversityProject.repository.dao.interfaces.GradeDao;
import com.epam.EpamUniversityProject.repository.util.DBManager;
import com.epam.EpamUniversityProject.repository.util.Fields;
import com.epam.EpamUniversityProject.repository.util.Mapper;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDaoPostgresImpl implements GradeDao {
    private final GradeMapper mapper = new GradeMapper();
    private final Logger log = Logger.getLogger(GradeDaoPostgresImpl.class);
    private static final String SQL_GET_ALL_BY_APPLICATION =
            "select grade.id,s.name,grade.score,s.id from grade" +
                    " join subject s on grade.subject = s.id where application=?;";

    @Override
    public void add(Object item) throws SQLException {

    }

    @Override
    public Object get(long id) throws  SQLException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            connection=DBManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Object newItem) throws SQLException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List getAll() throws SQLException {
        return null;
    }

    @Override
    public List<Grade> getApplicationGrades(long userId) throws  SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Grade> grades = new ArrayList<>();
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_ALL_BY_APPLICATION);
            statement.setLong(1, userId);
            rs = statement.executeQuery();
            while (rs.next()) {
                grades.add(mapper.mapRow(rs));
            }
            log.info("getUsersGrades->success");
        } catch (SQLException e) {
            log.error("getUsersGrades",e);
            throw e;
        } finally {
            DBManager.getInstance()
                    .close(rs)
                    .close(statement)
                    .close(connection);
        }
        return grades;
    }

    private class GradeMapper implements Mapper<Grade> {

        @Override
        public Grade mapRow(ResultSet resultSet) {
            Grade grade = null;
            try {
                grade = new Grade();
                grade.setId(resultSet.getLong(Fields.GRADE_ID));
                grade.setScore(resultSet.getInt(Fields.GRADE_SCORE));
                Subject subject = new SubjectDaoPostgresImpl
                        .SubjectMapper().mapRow(resultSet);
                grade.setSubject(subject);
            } catch (SQLException e) {
                log.error("mapRow",e);
            }
            return grade;
        }
    }
}
