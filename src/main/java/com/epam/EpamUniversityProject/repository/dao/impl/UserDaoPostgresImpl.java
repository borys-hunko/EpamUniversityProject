package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Role;
import com.epam.EpamUniversityProject.model.User;
import com.epam.EpamUniversityProject.repository.dao.interfaces.Dao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.UserDao;
import com.epam.EpamUniversityProject.repository.util.DBManager;
import com.epam.EpamUniversityProject.repository.util.Fields;
import com.epam.EpamUniversityProject.repository.util.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgresImpl implements UserDao {
    private Logger logger = Logger.getLogger(UserDaoPostgresImpl.class);
    private UserMapper mapper = new UserMapper();
    private static final String SQL_ADD_USER = "insert into university_user(email, password, role)" +
            "values (?, ?, ?);";
    private static final String SQL_READ_USER_BY_ID = "select * from university_user where id=?;";
    private static final String SQL_READ_USER_BY_EMAIL = "select * from university_user where email=?;";
    private static final String SQL_UPDATE_USER = "update university_user set" +
            " email=?,password=?,is_banned=?,role=? where id=?;";
    private static final String SQL_GET_ALL = "select * from university_user;";

    @Override
    public void add(User item) throws IOException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.
                    prepareStatement(SQL_ADD_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getEmail());
            statement.setString(2, item.getPassword());
            statement.setString(3, item.getRole().toString());
            rs = statement.executeQuery();
            if (rs.next()) {
                item.setId(rs.getLong(Fields.USER_ID));
            }
            DBManager.getInstance().commitAndClose(connection);
            logger.info("add() successfully added");
        } catch (SQLException | IOException e) {
            DBManager.getInstance().rollBackAndClose(connection);
            logger.error("add() " + e.getMessage());
            throw e;
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(statement);
        }
    }

    @Override
    public User get(long id) throws IOException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_READ_USER_BY_ID);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = mapper.mapRow(rs);
            } else {
                logger.info("no such element");
            }
            return user;
        } catch (SQLException | IOException e) {
            DBManager.getInstance().rollBackAndClose(connection);
            logger.error("get() " + e.getMessage());
            throw e;
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(preparedStatement);
            DBManager.getInstance().close(connection);
        }
    }

    @Override
    public void update(User newItem) throws IOException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, newItem.getEmail());
            statement.setString(2, newItem.getPassword());
            statement.setString(3, newItem.getRole().toString());
            statement.setLong(4, newItem.getId());
            logger.info("item successfully updated");
        } catch (SQLException | IOException e) {
            logger.error("update->" + e.getMessage());
            throw e;
        } finally {
            DBManager.getInstance().close(statement);
            DBManager.getInstance().close(connection);
        }
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<User> getAll() throws IOException, SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                users.add(mapper.mapRow(rs));
            }
            return users;
        } catch (SQLException | IOException e) {
            logger.error("getAll->" + e.getMessage());
            throw e;
        } finally {
            DBManager.getInstance().close(rs)
                    .close(statement)
                    .close(connection);
        }
    }

    @Override
    public User getByEmail(String email) throws IOException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getInstance().getConnection();
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

        } catch (SQLException | IOException e) {
            logger.error("getByEmail() " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(preparedStatement);
            DBManager.getInstance().close(connection);
        }
    }

    private class UserMapper implements Mapper<User> {
        @Override
        public User mapRow(ResultSet resultSet) {
            User user = new User();
            try {
                user.setId(resultSet.getLong(Fields.USER_ID));
                user.setEmail(resultSet.getString(Fields.USER_EMAIL));
                user.setPassword(resultSet.getString(Fields.USER_PASSWORD));
                user.setBlocked(resultSet.getBoolean(Fields.USER_IS_BLOCKED));
                user.setRole(Role.valueOf(resultSet.getString(Fields.USER_ROLE)));
                return user;
            } catch (SQLException e) {
                logger.error("mapRow->" + e.getMessage());
                return null;
            }
        }
    }

    public static void main(String[] args) {
        UserDao dao = new UserDaoPostgresImpl();
        try {
            User user = dao.getByEmail("vanya_yyyy44@ukr.net");
            System.out.println(user.getEmail());
        } catch (IOException | SQLException exception) {
            exception.printStackTrace();
        }
    }
}
