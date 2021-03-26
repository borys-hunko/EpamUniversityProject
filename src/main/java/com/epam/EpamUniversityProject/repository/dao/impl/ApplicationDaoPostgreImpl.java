package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Application;
import com.epam.EpamUniversityProject.model.Grade;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.GradeDao;
import com.epam.EpamUniversityProject.repository.util.DBManager;
import com.epam.EpamUniversityProject.repository.util.Fields;
import com.epam.EpamUniversityProject.repository.util.Mapper;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ApplicationDaoPostgreImpl implements ApplicationDao {
    private final Logger log = Logger.getLogger(ApplicationDaoPostgreImpl.class);
    private final Mapper<Application> mapper = new ApplicationMapper();
    private final DBManager manager = DBManager.getInstance();
    private GradeDao gradeDao;
    private final static String SQL_ADD_APPLICATION =
            "insert into application(\"user\",faculty,priority,status) values (?,?,?,?);";

    public ApplicationDaoPostgreImpl() {
        gradeDao = new GradeDaoPostgresImpl();
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

    private class ApplicationMapper implements Mapper<Application> {

        @Override
        public Application mapRow(ResultSet resultSet) {
            Application application = null;
//            try {
////                application.setId(resultSet.getLong(Fields.APPLICATION_ID))
////                        .setApplicant(null);
////            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            return null;
        }
    }
}
