package com.epam.EpamUniversityProject.repository.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class);
    private static final String PROPERTIES_PATH = "database.properties";
    private static DBManager instance;
    private final Properties DB_PROPERTIES =new Properties();

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }

    public Connection getConnection() throws SQLException, IOException {
        String url = getDBProperties().getProperty("database.url");
        String user = getDBProperties().getProperty("database.user");
        String password = getDBProperties().getProperty("database.password");
        return DriverManager.getConnection(url, user, password);
    }

    public void commitAndClose(Connection connection) throws SQLException {
        if (connection != null) {
            connection.commit();
            connection.close();
        }
    }

    private Properties getDBProperties() throws IOException {
        if (DB_PROPERTIES.isEmpty()) {
            try (InputStream stream = DBManager.class.
                    getClassLoader().
                    getResourceAsStream(DBManager.PROPERTIES_PATH)) {
                DB_PROPERTIES.load(stream);
            }
        }
        return DB_PROPERTIES;
    }

    public void rollBackAndClose(Connection connection) throws SQLException {
        if (connection != null) {
            connection.rollback();
            connection.close();
        }
    }

    public void close(AutoCloseable closeable) throws SQLException, IOException {
        if (closeable!=null){
            try {
                closeable.close();
            } catch (Exception e) {
                if (e instanceof SQLException)
                    throw (SQLException)e;
                else
                    throw (IOException)e;
            }
        }
    }
}
