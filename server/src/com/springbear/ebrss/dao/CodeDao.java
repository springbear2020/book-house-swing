package com.springbear.ebrss.dao;

import com.springbear.ebrss.entity.Code;
import com.springbear.ebrss.service.CodeService;
import com.springbear.ebrss.util.DruidUtil;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAO class - complete curd of the Code table
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 21:33
 */
public class CodeDao extends BasicDao<Code> {
    /**
     * Execute the same sql statement in transaction mode to insert multiple records,
     * if all data is inserted successfully then submit, otherwise roll back
     *
     * @param sql    sql
     * @param params Parameters of sql
     * @return true - batch insert successfully or false
     */
    public boolean batchInsertOneSql(String sql, Object[] params) {
        CodeService codeService = new CodeService();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DruidUtil.getConnection();
            // Start transaction
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);

            // The records length that want to insert
            int paramsCounts = params.length;
            // Actual rows of data inserted
            int updateSuccessCounts = 0;
            // the return value of the preparedStatement.executeBatch() method
            int[] updateCounts;

            // All all sql statement to the batch package
            for (Object param : params) {
                preparedStatement.setObject(1, param);
                preparedStatement.addBatch();
            }

            // Obtain the value of the auto increment field named id before execute batch insert
            int oldId = codeService.queryCurrentIdValue();

            try {
                // If something cause some sql statement goes wrong, throw BatchUpdateException
                updateCounts = preparedStatement.executeBatch();
            } catch (BatchUpdateException e) {
                updateCounts = e.getUpdateCounts();
            }

            // Accumulate the number of rows affected by the successful execution of each sql in turn
            for (int updateCount : updateCounts) {
                updateSuccessCounts += updateCount;
            }

            // If all sql insert successfully then commit the transaction, or roll back
            if (paramsCounts == updateSuccessCounts) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                codeService.resetToOldId(oldId);
            }

            preparedStatement.clearBatch();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtil.close(null, preparedStatement, connection);
        }
        return false;
    }
}
