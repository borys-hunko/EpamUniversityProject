package UniProject.repository.dao.impl;

import UniProject.model.*;
import UniProject.repository.dao.interfaces.ApplicationDao;
import UniProject.repository.dao.interfaces.FacultyDao;
import UniProject.repository.dao.interfaces.GradeDao;
import UniProject.repository.dao.interfaces.UserDao;
import UniProject.utils.DBManager;
import UniProject.utils.Fields;
import UniProject.utils.Mapper;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDaoPostgreImpl implements ApplicationDao {
    private static final String SQL_UPDATE_STATUS = "update application set status=? where id=?;";
    private static final String SQL_GET = "select * from application where id=?";
    private final Logger log = Logger.getLogger(ApplicationDaoPostgreImpl.class);
    private final Mapper<Application> applicationMapper = new ApplicationMapper();
    private final DBManager manager = DBManager.getInstance();
    private GradeDao gradeDao;
    private FacultyDao facultyDao;
    private UserDao userDao;
    private final static String SQL_ADD_APPLICATION =
            "insert into application (\"user\", faculty, status, education_type)" +
                    "values (?,?,?,?);";
    private final static String SQL_ADD_GRADE =
            "insert into grade(subject, score, application) VALUES (?,?,?);";
    private final static String SQL_GET_ALL = "select * from application;";
    private final static String SQL_GET_USERS_APPLICATIONS =
            "select * from application where \"user\"=?;";
    private static final String SQL_DELETE = "delete from application where id=?;";
    private static final String SQL_GET_APPLICATION_NUMBER="select count(*) from application where faculty=?;";
    public ApplicationDaoPostgreImpl() {
        gradeDao = new GradeDaoPostgresImpl();
        userDao = new UserDaoPostgresImpl();
        facultyDao = new FacultyDaoPostgresImpl();
    }

    public int getApplicationsNumber(long facultyId) throws SQLException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;
        try {
            connection= manager.getConnection();
            statement=connection.prepareStatement(SQL_GET_APPLICATION_NUMBER);
            statement.setLong(1,facultyId);
            set=statement.executeQuery();
            if (set.next()){
                return set.getInt("count");
            }else {
                return 0;
            }
        } catch (SQLException e) {
            log.error("getApplicationsNumber:",e);
            throw e;
        }finally {
            manager.close(set)
                    .close(statement)
                    .close(connection);
        }
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
            statement.setString(3, item.getStatus().toString());
            statement.setString(4, item.getTypeOfEducation().toString());
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
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Application application = null;
        try {
            log.info("get: start getting application");
            connection = manager.getConnection();
            statement = connection.prepareStatement(SQL_GET);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                application = applicationMapper.mapRow(resultSet);
                addPropertiesFromOtherTables(resultSet, application);
            }
            log.info("get: got application");
        } catch (SQLException e) {
            log.error("get:", e);
            throw e;
        } finally {
            manager.close(resultSet)
                    .close(statement)
                    .close(connection);
        }
        return application;
    }

    @Override
    public void update(Application newItem) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            log.info("delete: start deleting application");
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                throw new SQLException("delete didn't occur");
            }
            manager.commitAndClose(connection);
            log.info("delete: deleted application");
        } catch (SQLException e) {
            manager.rollBackAndClose(connection);
            log.error("delete: ", e);
            throw e;
        } finally {
            manager.close(statement);
        }
    }

    @Override
    public List<Application> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Application> applications = new ArrayList<>();
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SQL_GET_ALL);
            rs = statement.executeQuery();
            mapApplicationList(rs, applications);
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

    /**
     * create applications,fill its fields and add it to application list
     *
     * @param rs           result set, which contains data from application table
     * @param applications applications list
     */
    private void mapApplicationList(ResultSet rs, List<Application> applications) throws SQLException {
        while (rs.next()) {
            Application application = applicationMapper.mapRow(rs);
            addPropertiesFromOtherTables(rs, application);
            applications.add(application);
        }
    }

    /**
     * retrieve properties(faculty,applicant,grades) from outer tables
     * because applications has a lot of foreign keys
     *
     * @param rs          result set, which contains data from application table(including foreign keys on outer tables)
     * @param application application to be filled
     */
    private void addPropertiesFromOtherTables(ResultSet rs, Application application) throws SQLException {
        Faculty faculty = facultyDao.get(rs.getLong(Fields.APPLICATION_FACULTY));
        User user = userDao.get(rs.getLong(Fields.APPLICATION_USER));
        List<Grade> grades = gradeDao.getApplicationGrades(application.getId());
        application.setApplicant(user)
                .setFaculty(faculty)
                .setGrades(grades);
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
            mapApplicationList(rs, applications);
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

    @Override
    public void updateStatusForAllApplications(List<Application> applications) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_UPDATE_STATUS);
            log.info("updateStatusForAllApplications: started");
            for (Application application : applications) {
                statement.setString(1, application.getStatus().toString());
                statement.setLong(2, application.getId());
                statement.executeUpdate();
            }
            log.info("updateStatusForAllApplications: finished");
            manager.commitAndClose(connection);
        } catch (SQLException e) {
            log.error("updateStatusForAllApplications: ", e);
            manager.rollBackAndClose(connection);
            throw e;
        } finally {
            manager.close(statement);
        }
    }

    private class ApplicationMapper implements Mapper<Application> {

        @Override
        public Application mapRow(ResultSet resultSet) {
            try {
                log.info("mapRow->mappingStarted");
                Application application = new Application();
                application.setId(resultSet.getLong(Fields.APPLICATION_ID))
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


}
