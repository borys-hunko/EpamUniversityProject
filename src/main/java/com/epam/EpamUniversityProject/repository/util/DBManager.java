package com.epam.EpamUniversityProject.repository.util;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;



public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class);
    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }


    public Connection getConnection() throws SQLException {
        Connection connection=null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            DataSource ds = (DataSource)envContext.lookup("jdbc/uni");
            connection=ds.getConnection();
            return connection;
        } catch (NamingException e) {
            e.printStackTrace();
            logger.error("Cannot obtain a connection from the pool", e);
            return dumbGetConnection();
        }

    }

    private Connection dumbGetConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/epam_university",
                "postgres","pass");
    }

    public void commitAndClose(Connection connection) throws SQLException {
        if (connection != null) {
            connection.commit();
            connection.close();
        }
    }

    public void rollBackAndClose(Connection connection) throws SQLException {
        if (connection != null) {
            connection.rollback();
            connection.close();
        }
    }

    public DBManager close(AutoCloseable closeable) throws SQLException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.error("close->" + e.getMessage());
                if (e instanceof SQLException)
                    throw (SQLException) e;
            }
        }
        return instance;
    }
}
