package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Application;
import com.epam.EpamUniversityProject.model.Grade;
import com.epam.EpamUniversityProject.model.Subject;
import com.epam.EpamUniversityProject.repository.dao.interfaces.GradeDao;
import com.epam.EpamUniversityProject.utils.DBManager;
import com.epam.EpamUniversityProject.utils.Fields;
import com.epam.EpamUniversityProject.utils.Mapper;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDaoPostgresImpl implements GradeDao {
    private final DBManager manager = DBManager.getInstance();
    private final GradeMapper mapper = new GradeMapper();
    private final Logger log = Logger.getLogger(GradeDaoPostgresImpl.class);
    private static final String SQL_ADD =
            "insert into grade(subject, score, application) VALUES (?,?,?);";
    private static final String SQL_GET_ALL_BY_APPLICATION =
            "select grade.id,s.name,grade.score,s.id from grade" +
                    " join subject s on grade.subject = s.id where application=?;";

    @Override
    public void add(Grade item) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Grade get(long id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Grade newItem) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List getAll() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Grade> getApplicationGrades(long userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Grade> grades = new ArrayList<>();
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SQL_GET_ALL_BY_APPLICATION);
            statement.setLong(1, userId);
            rs = statement.executeQuery();
            while (rs.next()) {
                grades.add(mapper.mapRow(rs));
            }
            log.info("getUsersGrades->success");
        } catch (SQLException e) {
            log.error("getUsersGrades", e);
            throw e;
        } finally {
            manager.close(rs)
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
                log.error("mapRow", e);
            }
            return grade;
        }
    }
}
