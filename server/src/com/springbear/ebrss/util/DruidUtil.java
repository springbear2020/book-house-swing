package com.springbear.ebrss.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Druid database connection pool tools class
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:45
 */
public class DruidUtil {
    static DataSource dataSource;
    static String path = "config\\druid.properties";

    // Read the config info from config file
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Get a database connection from the pool
     *
     * @return Connection
     * @throws SQLException Something goes wrong, throws the exception
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Close the connection and release resources
     *
     * @param resultSet  ResultSet
     * @param statement  Statement
     * @param connection Connection
     */
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
