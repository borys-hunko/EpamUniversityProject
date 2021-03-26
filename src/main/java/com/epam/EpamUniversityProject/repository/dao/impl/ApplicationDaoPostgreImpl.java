package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.*;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.GradeDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.UserDao;
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

public class ApplicationDaoPostgreImpl implements ApplicationDao {
    private final Logger log = Logger.getLogger(ApplicationDaoPostgreImpl.class);
    private final Mapper<Application> applicationMapper = new ApplicationMapper();
    private final DBManager manager = DBManager.getInstance();
    private GradeDao gradeDao;
    private FacultyDao facultyDao;
    private UserDao userDao;
    private final static String SQL_ADD_APPLICATION =
            "insert into application(\"user\",faculty,priority,status) values (?,?,?,?);";
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
        try {
            connection = manager.getConnection();
            log.info("add->connected");
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_ADD_APPLICATION,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, item.getApplicant().getId());
            statement.setLong(2, item.getFaculty().getId());
            statement.setInt(3, item.getPriority());
            statement.setString(4, item.getStatus().toString());
            resultSet = statement.executeQuery();
            log.info("add->application added");
            if (resultSet.next()) {
                item.setId(resultSet.getLong(Fields.APPLICATION_ID));
            }
            for (Grade grade : item.getGrades()) {
                gradeDao.add(grade);
            }
            log.info("add->grades added");
            manager.commitAndClose(connection);
            log.info("add->success");
        } catch (SQLException e) {
            log.error("add", e);
            manager.rollBackAndClose(connection);
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
                Application application=new Application();
                application.setId(resultSet.getLong(Fields.APPLICATION_ID))
                        .setPriority(resultSet.getInt(Fields.APPLICATION_PRIORITY))
                        .setStatus(ApplicationStatus.valueOf(
                                        resultSet.getString(Fields.APPLICATION_STATUS)
                                )
                        );
                log.info("map->mappingFinished");
                return application;
            } catch (SQLException e) {
                log.error("mapRow->",e);
                return null;
            }
        }
    }

    public static void main(String[] args) {
        try {
            ApplicationDao dao=new ApplicationDaoPostgreImpl();
            List<Application> applications=dao.getUsersApplication(2);
            for (Application a:applications){
                System.out.println(a);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
