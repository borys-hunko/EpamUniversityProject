package UniProject.repository.dao.impl;

import UniProject.utils.Fields;
import UniProject.model.Faculty;
import UniProject.model.Subject;
import UniProject.repository.dao.interfaces.FacultyDao;
import UniProject.utils.DBManager;
import UniProject.utils.Mapper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDaoPostgresImpl implements FacultyDao {
    private final Logger log = Logger.getLogger(FacultyDaoPostgresImpl.class);
    private final Mapper<Faculty> facultyMapper = new FacultyMapper();
    private final Mapper<Subject> subjectMapper = new SubjectDaoPostgresImpl.SubjectMapper();
    private final DBManager manager = DBManager.getInstance();

    private static final String SQL_ADD_FACULTY =
            "insert into faculty(name, budget_places, total_places) VALUES (?,?,?);";
    private static final String SQL_ADD_REQUIRED_SUBJECT =
            "insert into required_subjects(faculty, subject) VALUES (?,?);";
    private static final String SQL_GET_FACULTY = "select * from faculty where id=?;";
    private static final String SQL_GET_REQUIRED_SUBJECTS =
            "select * from subject s " +
                    "join required_subjects rs on s.id = rs.subject " +
                    "join faculty f on f.id = rs.faculty where faculty=?;";
    private static final String SQL_GET_ALL_FACULTIES = "select * from faculty;";
    private static final String SQL_GET_ALL_REQUIRES_SUBJECTS = "select s.id,s.name " +
            "from subject s join required_subjects rs on s.id = rs.subject" +
            " join faculty f on f.id = rs.faculty where f.id=?;";
    private final static String SQL_UPDATE_FACULTY =
            "update faculty set name=?,budget_places=?,total_places=? where id=?;";
    private static final String SQL_DELETE_FACULTY="delete from faculty where id=?;";
    private static final String SQL_DELETE_REQUIRED_SUBJECTS =
            "delete from required_subjects where faculty=?;";

    @Override
    public void add(Faculty item) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_ADD_FACULTY,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setInt(2, item.getBudgetPlaces());
            statement.setLong(3, item.getTotalPlaces());
            if (statement.executeUpdate() == 0) {
                throw new SQLException("faculty wasn't inserted in table");
            }
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(Fields.FACULTY_ID));
                log.info("items id:" + item.getId());
            } else {
                throw new SQLException("primary key wasn't generated");
            }
            addRequiredSubjects(connection, item);
            manager.commitAndClose(connection);
        } catch (SQLException e) {
            log.error("add: ", e);
            manager.rollBackAndClose(connection);
            throw e;
        } finally {
            manager.close(rs)
                    .close(statement);
        }
    }

    private void addRequiredSubjects(Connection connection, Faculty faculty)
            throws SQLException {
        PreparedStatement statement = null;
        try {
            log.info("addRequiredSubjects: add req subjects...");
            statement = connection.prepareStatement(SQL_ADD_REQUIRED_SUBJECT);
            statement.setLong(1, faculty.getId());
            for (Subject subject : faculty.getRequiredSubjects()) {
                statement.setLong(2, subject.getId());
                if (statement.executeUpdate() == 0) {
                    throw new SQLException("row wasn't inserted in required subjects table");
                }
            }
            log.info("successfully added required subjects");
        } finally {
            manager.close(statement);
        }
    }

    @Override
    public Faculty get(long id) throws SQLException {
        Connection connection = null;
        PreparedStatement facsStatement = null;
        PreparedStatement subjsStatement = null;
        ResultSet facsRs = null;
        try {
            connection = manager.getConnection();
            log.info("get: connected");
            facsStatement = connection.prepareStatement(SQL_GET_FACULTY);
            subjsStatement = connection.prepareStatement(SQL_GET_REQUIRED_SUBJECTS);
            facsStatement.setLong(1, id);
            subjsStatement.setLong(1, id);
            facsRs = facsStatement.executeQuery();
            facsRs.next();
            Faculty faculty = facultyMapper.mapRow(facsRs);
            log.info("get: faculty retrieved");
            List<Subject> requiredSubjects = getRequiredSubjects(subjsStatement);
            log.info("get: required subjects retrieved, size=" + requiredSubjects.size());
            faculty.setRequiredSubjects(requiredSubjects);
            return faculty;
        } catch (SQLException e) {
            log.error("get: ", e);
            manager.rollBackAndClose(connection);
            throw e;
        } finally {
            manager.close(facsRs)
                    .close(subjsStatement)
                    .close(facsStatement)
                    .close(connection);
        }
    }

    @Override
    public void update(Faculty newItem) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_UPDATE_FACULTY);
            statement.setString(1, newItem.getName());
            statement.setInt(2, newItem.getBudgetPlaces());
            statement.setInt(3, newItem.getTotalPlaces());
            statement.setLong(4, newItem.getId());
            if (statement.executeUpdate() == 0) {
                throw new SQLException("faculty wasn't updated");
            }
            updatedRequiredSubjects(connection, newItem);
            manager.commitAndClose(connection);
            log.info("update: success");
        } catch (SQLException e) {
            log.error("update: ", e);
            manager.rollBackAndClose(connection);
            throw e;
        } finally {
            manager.close(statement);
        }
    }

    private void updatedRequiredSubjects(Connection connection, Faculty faculty) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_REQUIRED_SUBJECTS)) {
            log.info("updatedRequiredSubjects: delete required subjects");
            statement.setLong(1,faculty.getId());
            if (statement.executeUpdate() == 0) {
                throw new SQLException("req subjects wasn't deleted");
            }
            log.info("updatedRequiredSubjects: req subjects deleted");
            addRequiredSubjects(connection, faculty);
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            connection=manager.getConnection();
            connection.setAutoCommit(false);
            statement=connection.prepareStatement(SQL_DELETE_FACULTY);
            statement.setLong(1,id);
            if (statement.executeUpdate()==0){
                throw new SQLException("item wasn't deleted");
            }
            manager.commitAndClose(connection);
            log.info("delete: success");
        } catch (SQLException e) {
            log.error("delete:",e);
            manager.rollBackAndClose(connection);
            throw e;
        }finally {
            manager.close(statement);
        }
    }

    @Override
    public List<Faculty> getAll() throws SQLException {
        Connection connection = null;
        Statement facsStatement = null;
        PreparedStatement subjsStatement = null;
        ResultSet facsRs = null;
        List<Faculty> faculties = new ArrayList<>();
        try {
            connection = manager.getConnection();
            facsStatement = connection.createStatement();
            subjsStatement = connection.prepareStatement(SQL_GET_ALL_REQUIRES_SUBJECTS);
            facsRs = facsStatement.executeQuery(SQL_GET_ALL_FACULTIES);
            while (facsRs.next()) {
                Faculty faculty = facultyMapper.mapRow(facsRs);
                subjsStatement.setLong(1, faculty.getId());
                List<Subject> subjects = getRequiredSubjects(subjsStatement);
                faculty.setRequiredSubjects(subjects);
                faculties.add(faculty);
            }
            return faculties;
        } catch (SQLException e) {
            log.error("getAll->" + e.getMessage());
            throw e;
        } finally {
            manager.close(facsRs)
                    .close(facsStatement)
                    .close(subjsStatement)
                    .close(connection);
        }
    }

    private List<Subject> getRequiredSubjects(PreparedStatement subjsStatement) throws SQLException {
        ResultSet subjsRs;
        subjsRs = subjsStatement.executeQuery();
        List<Subject> subjects = new ArrayList<>();
        while (subjsRs.next()) {
            subjects.add(subjectMapper.mapRow(subjsRs));
        }
        return subjects;
    }

    private class FacultyMapper implements Mapper<Faculty> {

        @Override
        public Faculty mapRow(ResultSet resultSet) {
            Faculty faculty = new Faculty();
            try {
                faculty.setId(resultSet.getLong(Fields.FACULTY_ID));
                faculty.setName(resultSet.getString(Fields.FACULTY_NAME));
                faculty.setBudgetPlaces(resultSet.getInt(Fields.FACULTY_BUDGET_PLACES));
                faculty.setTotalPlaces(resultSet.getInt(Fields.FACULTY_TOTAL_PLACES));
                return faculty;
            } catch (SQLException e) {
                log.error("mapRow->" + e.getMessage());
                return null;
            }
        }
    }
}
