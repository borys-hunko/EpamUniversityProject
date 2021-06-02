package UniProject.repository.dao.impl;

import UniProject.model.Role;
import UniProject.model.User;
import UniProject.repository.dao.interfaces.UserDao;
import UniProject.utils.DBManager;
import UniProject.utils.Fields;
import UniProject.utils.Mapper;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgresImpl implements UserDao {
    private final Logger logger = Logger.getLogger(UserDaoPostgresImpl.class);
    private final UserMapper mapper = new UserMapper();
    private final DBManager manager = DBManager.getInstance();
    private static final String SQL_ADD_USER = "insert into university_user(email, password," +
            "role, first_name, last_name," +
            "fathers_name, region, city, school)" +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_READ_USER_BY_ID = "select * from university_user where id=?;";
    private static final String SQL_READ_USER_BY_EMAIL = "select * from university_user where email=?;";
    private static final String SQL_UPDATE_USER =
            "update university_user set email=?, password=?, is_banned=?, role=?, first_name=?, last_name=?," +
                    "fathers_name=?, region=?, city=?, school=? where id=?;";
    private static final String SQL_GET_ALL = "select * from university_user;";

    @Override
    public void add(User item) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            statement = connection.
                    prepareStatement(SQL_ADD_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getEmail());
            statement.setString(2, item.getPassword());
            statement.setString(3, item.getRole().toString());
            statement.setString(4, item.getFirstName());
            statement.setString(5, item.getLastName());
            statement.setString(6, item.getFathersName());
            statement.setString(7, item.getRegion());
            statement.setString(8, item.getCity());
            statement.setString(9, item.getSchool());
            statement.execute();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(Fields.USER_ID));
            }
            manager.commitAndClose(connection);
            logger.info("add() successfully added");
        } catch (SQLException e) {
            manager.rollBackAndClose(connection);
            logger.error("add() " + e.getMessage());
            throw e;
        } finally {
            manager.close(rs)
                    .close(statement);
        }
    }

    @Override
    public User get(long id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = manager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_READ_USER_BY_ID);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = mapper.mapRow(rs);
            } else {
                logger.info("get() no such element");
            }
            return user;
        } catch (SQLException e) {
            manager.rollBackAndClose(connection);
            logger.error("get() " + e.getMessage());
            throw e;
        } finally {
            manager.close(rs)
                    .close(preparedStatement)
                    .close(connection);
        }
    }

    @Override
    public void update(User newItem) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, newItem.getEmail());
            statement.setString(2, newItem.getPassword());
            statement.setBoolean(3,newItem.isBlocked());
            statement.setString(4,newItem.getRole().toString());
            statement.setString(5,newItem.getFirstName());
            statement.setString(6,newItem.getLastName());
            statement.setString(7,newItem.getFathersName());
            statement.setString(8,newItem.getRegion());
            statement.setString(9,newItem.getCity());
            statement.setString(10,newItem.getSchool());
            statement.setLong(11,newItem.getId());
            statement.executeUpdate();
            logger.info("item successfully updated");
        } catch (SQLException e) {
            logger.error("update->" + e.getMessage());
            throw e;
        } finally {
            manager.close(statement)
                    .close(connection);
        }
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<User> getAll() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            connection = manager.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                User user = mapper.mapRow(rs);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("getAll->" + e.getMessage());
            throw e;
        } finally {
            manager.close(rs)
                    .close(statement)
                    .close(connection);
        }
    }

    @Override
    public User getByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = manager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_READ_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = mapper.mapRow(rs);
            } else {
                logger.info("no such element");
            }
            rs.close();
            preparedStatement.close();
            return user;

        } catch (SQLException e) {
            logger.error("getByEmail() " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            manager.close(rs)
                    .close(preparedStatement)
                    .close(connection);
        }
    }

    private class UserMapper implements Mapper<User> {
        @Override
        public User mapRow(ResultSet resultSet) {
            User user = new User();
            try {
                user.setId(resultSet.getLong(Fields.USER_ID))
                        .setEmail(resultSet.getString(Fields.USER_EMAIL))
                        .setPassword(resultSet.getString(Fields.USER_PASSWORD))
                        .setBlocked(resultSet.getBoolean(Fields.USER_IS_BLOCKED))
                        .setRole(Role.valueOf(resultSet.getString(Fields.USER_ROLE)))
                        .setCity(resultSet.getString(Fields.USER_CITY))
                        .setRegion(resultSet.getString(Fields.USER_REGION))
                        .setSchool(resultSet.getString(Fields.USER_SCHOOL))
                        .setFirstName(resultSet.getString(Fields.USER_FIRST_NAME))
                        .setLastName(resultSet.getString(Fields.USER_LAST_NAME))
                        .setFathersName(resultSet.getString(Fields.USER_FATHERS_NAME));
                return user;
            } catch (SQLException e) {
                logger.error("mapRow->" + e.getMessage());
                return null;
            }
        }
    }

}
