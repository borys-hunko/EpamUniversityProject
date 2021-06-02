package UniProject.utils;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;

import java.sql.SQLException;

/**
 * singleton class to perform some frequent tasks
 * */
public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class);
    private static DBManager instance;

    /**
     * @return instance of this class
     * if needed initialize this instance
     * */
    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }

    /**
     * @return DB connection
     * */
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            logger.info("getConnection: get context");
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            logger.info("getConnection: set data source");
            DataSource ds = (DataSource) envContext.lookup("jdbc/uni");
            connection = ds.getConnection();
            logger.info("getConnection: connection has been established");
        } catch (NamingException e) {
            logger.error("Cannot obtain a connection from the pool", e);
        }
        return connection;
    }


    /**
     * commit transaction and close connection
     * @param connection connection to close
     * */
    public void commitAndClose(Connection connection) throws SQLException {
        if (connection != null) {
            connection.commit();
            connection.close();
        }
    }

    /**
     * rollback transaction and close connection
     * @param connection connetcion to close
     * */
    public void rollBackAndClose(Connection connection) throws SQLException {
        if (connection != null) {
            connection.rollback();
            connection.close();
        }
    }

    /**
     * close autocloseable
     * because it is used only to close connection,statements and result set throws SQLException
     * @return instance of db manager for convenience
     * */
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
