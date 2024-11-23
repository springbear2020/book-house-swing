package com.springbear.ebrss.dao;

import com.springbear.ebrss.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO class - compete crud of the database table
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:56
 */
public class BasicDao<T> {
    QueryRunner queryRunner = new QueryRunner();

    /**
     * Execute DML statement
     *
     * @param sql    sql
     * @param params Parameters of sql
     * @return Affected rows
     */
    public int update(String sql, Object[] params) {
        sql = sql.toLowerCase();
        Connection connection = null;
        try {
            connection = DruidUtil.getConnection();
            return queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DruidUtil.close(null, null, connection);
        }
    }

    /**
     * Get multi rows record
     *
     * @param sql    sql
     * @param clazz  The class object of the specific POJO class
     * @param params Parameters of sql
     * @return Multi rows record or null
     */
    public List<T> listRecords(String sql, Class<T> clazz, Object[] params) {
        sql = sql.toLowerCase();
        Connection connection = null;
        try {
            connection = DruidUtil.getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtil.close(null, null, connection);
        }
    }

    /**
     * Get one record
     *
     * @param sql    sql
     * @param clazz  The class object of the specific POJO class
     * @param params Parameters of sql
     * @return One record
     */
    public T getRecord(String sql, Class<T> clazz, Object[] params) {
        sql = sql.toLowerCase();
        Connection connection = null;
        try {
            connection = DruidUtil.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<>(clazz), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtil.close(null, null, connection);
        }
    }

    /**
     * Get one cell object
     *
     * @param sql    sql
     * @param params Parameters of sql
     * @return One cell object
     */
    public Object getCell(String sql, Object[] params) {
        sql = sql.toLowerCase();
        Connection connection = null;
        try {
            connection = DruidUtil.getConnection();
            return queryRunner.query(connection, sql, new ScalarHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtil.close(null, null, connection);
        }
    }
}
