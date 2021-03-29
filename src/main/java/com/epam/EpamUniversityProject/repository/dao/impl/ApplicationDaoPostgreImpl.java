package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.*;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.GradeDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.UserDao;
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

public class ApplicationDaoPostgreImpl implements ApplicationDao {
    private final Logger log = Logger.getLogger(ApplicationDaoPostgreImpl.class);
    private final Mapper<Application> applicationMapper = new ApplicationMapper();
    private final DBManager manager = DBManager.getInstance();
    private GradeDao gradeDao;
    private FacultyDao facultyDao;
    private UserDao userDao;
    private final static String SQL_ADD_APPLICATION =
            "insert into application (\"user\", faculty, priority, status, education_type)" +
                    "values (?,?,?,?,?);";
    private final static String SQL_ADD_GRADE =
            "insert into grade(subject, score, application) VALUES (?,?,?);";
    private final static String SQL_GET_USERS_APPLICATIONS =
            "select * from application where \"user\"=?;";

    public ApplicationDaoPostgreImpl() {
        gradeDao = new GradeDaoPostgresImpl();
        userDao = new UserDaoPostgresImpl();
        facultyDao = new FacultyDaoPostgresImpl();
    }

    @Override
    public void add(Application item) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement addGradeStatement = null;
        try {
            connection = manager.getConnection();
            log.info("add->connected");
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            statement = connection.prepareStatement(SQL_ADD_APPLICATION,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, item.getApplicant().getId());
            statement.setLong(2, item.getFaculty().getId());
            statement.setInt(3, item.getPriority());
            statement.setString(4, item.getStatus().toString());
            statement.setString(5, item.getTypeOfEducation().toString());
            if (statement.executeUpdate() == 0) {
                throw new SQLException("application wasn't inserted");
            }
            resultSet = statement.getGeneratedKeys();
            log.info("add->application added");
            if (resultSet.next()) {
                item.setId(resultSet.getLong(Fields.APPLICATION_ID));
            }
            addGradeStatement = connection.prepareStatement(SQL_ADD_GRADE,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            for (Grade grade : item.getGrades()) {
                addGrade(addGradeStatement, grade, item);
            }
            log.info("add->grades added");
            manager.commitAndClose(connection);
            log.info("add->success");
        } catch (SQLException e) {
            log.error("add", e);
            manager.rollBackAndClose(connection);
            throw e;
        } finally {
            manager.close(resultSet)
                    .close(statement)
                    .close(addGradeStatement);
        }
    }

    public void addGrade(PreparedStatement statement, Grade grade, Application application) throws SQLException {
        ResultSet rs = null;
        try {
            statement.setLong(1, grade.getSubject().getId());
            statement.setInt(2, grade.getScore());
            statement.setLong(3, application.getId());
            statement.execute();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                grade.setId(rs.getLong(Fields.GRADE_ID));
            } else {
                throw new SQLException("addGrade: no generated keys");
            }
            log.info("addGrade: added grade, grade id=" + grade.getId());
        } catch (SQLException e) {
            log.error("addGrade", e);
            throw e;
        } finally {
            manager.close(rs);
        }
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
    public List<Application> getUsersApplication(long userID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Application> applications = new ArrayList<>();
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SQL_GET_USERS_APPLICATIONS);
            statement.setLong(1, userID);
            rs = statement.executeQuery();
            while (rs.next()) {
                Application application = applicationMapper.mapRow(rs);
                Faculty faculty = facultyDao.get(rs.getLong(Fields.APPLICATION_FACULTY));
                User user = userDao.get(rs.getLong(Fields.APPLICATION_USER));
                List<Grade> grades = gradeDao.getApplicationGrades(application.getId());
                application.setApplicant(user)
                        .setFaculty(faculty)
                        .setGrades(grades);
                applications.add(application);
            }
        } catch (SQLException e) {
            log.error("getUsersApplication->", e);
            throw e;
        } finally {
            manager.close(rs)
                    .close(statement)
                    .close(connection);
        }
        return applications;
    }

    private class ApplicationMapper implements Mapper<Application> {

        @Override
        public Application mapRow(ResultSet resultSet) {
            try {
                log.info("mapRow->mappingStarted");
                Application application = new Application();
                application.setId(resultSet.getLong(Fields.APPLICATION_ID))
                        .setPriority(resultSet.getInt(Fields.APPLICATION_PRIORITY))
                        .setStatus(ApplicationStatus.valueOf(
                                resultSet.getString(Fields.APPLICATION_STATUS)))
                        .setTypeOfEducation(TypeOfEducation.valueOf(
                                resultSet.getString(Fields.APPLICATION_EDUCATION_TYPE)));
                log.info("map->mappingFinished");
                return application;
            } catch (SQLException e) {
                log.error("mapRow->", e);
                return null;
            }
        }
    }

    public static void main(String[] args) {
        try {
            ApplicationDao dao = new ApplicationDaoPostgreImpl();
            List<Application> applications = dao.getUsersApplication(2);
            for (Application a : applications) {
                System.out.println(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
