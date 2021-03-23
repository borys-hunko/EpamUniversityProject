package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Subject;
import com.epam.EpamUniversityProject.repository.dao.interfaces.SubjectDao;
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
    private final Logger log = Logger.getLogger(SubjectDaoPostgresImpl.class);
    private static final String SQL_ADD_SUBJECT = "insert into subject(name) values (?);";

    @Override
    public void add(Subject item) throws IOException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_ADD_SUBJECT, PreparedStatement.RETURN_GENERATED_KEYS);
            rs = statement.executeQuery();
            if (!rs.next()) {
                throw new SQLException("add->subject wasn't inserted");
            }
            item.setId(rs.getLong(Fields.SUBJECT_ID));
            log.info("add->item successfully added");
            DBManager.getInstance().commitAndClose(connection);
        } catch (SQLException | IOException e) {
            log.error("add->" + e.getMessage());
            DBManager.getInstance().rollBackAndClose(connection);
            throw e;
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(statement);
        }
    }

    @Override
    public Subject get(long id) throws IOException, SQLException {
        return null;
    }

    @Override
    public void update(Subject newItem) throws IOException, SQLException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Subject> getAll() throws IOException, SQLException {
        return null;
    }

    public class SubjectMapper implements Mapper<Subject> {

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
}
