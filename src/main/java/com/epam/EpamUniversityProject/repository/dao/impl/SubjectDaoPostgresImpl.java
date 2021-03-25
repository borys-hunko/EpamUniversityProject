package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Subject;
import com.epam.EpamUniversityProject.model.User;
import com.epam.EpamUniversityProject.repository.dao.interfaces.SubjectDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.UserDao;
import com.epam.EpamUniversityProject.repository.util.DBManager;
import com.epam.EpamUniversityProject.repository.util.Fields;
import com.epam.EpamUniversityProject.repository.util.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class SubjectDaoPostgresImpl implements SubjectDao {
    private static final Logger log = Logger.getLogger(SubjectDaoPostgresImpl.class);
    private Mapper<Subject> mapper = new SubjectMapper();
    private final DBManager manager = DBManager.getInstance();

    private static final String SQL_ADD_SUBJECT = "insert into subject(name) values (?);";
    private static final String SQL_GET_SUBJECT = "select * from subject where id=?;";

    @Override
    public void add(Subject item) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_ADD_SUBJECT, PreparedStatement.RETURN_GENERATED_KEYS);
            rs = statement.executeQuery();
            if (!rs.next()) {
                throw new SQLException("add->subject wasn't inserted");
            }
            item.setId(rs.getLong(Fields.SUBJECT_ID));
            log.info("add->item successfully added");
            manager.commitAndClose(connection);
        } catch (SQLException e) {
            log.error("add->" + e.getMessage());
            manager.rollBackAndClose(connection);
            throw e;
        } finally {
            manager.close(rs)
                    .close(statement);
        }
    }

    @Override
    public Subject get(long id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SQL_GET_SUBJECT);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            Subject subject = null;
            if (rs.next()) {
                subject = mapper.mapRow(rs);
            }
            return subject;
        } catch (SQLException e) {
            log.error("get->" + e.getMessage());
            throw e;
        } finally {
            manager.close(rs)
                    .close(statement)
                    .close(connection);
        }
    }

    @Override
    public void update(Subject newItem) throws SQLException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Subject> getAll() throws SQLException {
        return null;
    }

    public static class SubjectMapper implements Mapper<Subject> {

        @Override
        public Subject mapRow(ResultSet resultSet) {
            Subject subject = new Subject();
            try {
                subject.setId(resultSet.getLong(Fields.SUBJECT_ID));
                subject.setName(resultSet.getString(Fields.SUBJECT_NAME));
                return subject;
            } catch (SQLException e) {
                log.error("mapRow->" + e.getMessage());
                return null;
            }
        }
    }

    public static void main(String[] args) {
        try {
            SubjectDao dao = new SubjectDaoPostgresImpl();
            Subject subject = dao.get(1);
            System.out.println(subject.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
