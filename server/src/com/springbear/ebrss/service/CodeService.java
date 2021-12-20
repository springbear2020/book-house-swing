package com.springbear.ebrss.service;

import com.springbear.ebrss.dao.CodeDao;
import com.springbear.ebrss.entity.Code;

/**
 * Code service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 21:33
 */
public class CodeService {
    private final CodeDao codeDao = new CodeDao();

    /**
     * Get register code by the register code
     *
     * @param code Request content object
     * @return true - the code has exists or false
     */
    public synchronized boolean getRegisterCodeByCode(Code code) {
        Object[] params = new Object[]{code.getRegisterCode()};
        String sql = "SELECT registerCode FROM Code WHERE registerCode = ? AND codeState = 'unused';";
        return codeDao.getCell(sql, params) != null;
    }

    /**
     * Batch add register code
     *
     * @param code The object array of the register code
     * @return true - batch insert successfully or false
     */
    public boolean batchAddRegisterCode(Object[] code) {
        String sql = "INSERT INTO Code(registerCode) VALUES(?);";
        return codeDao.batchInsertOneSql(sql, code);
    }

    /**
     * Get the max value of the userId of the Code table
     *
     * @return The max value of the userId
     */
    public int queryCurrentIdValue() {
        Object[] params = new Object[]{};
        String sql = "SELECT MAX(codeId) FROM Code FOR UPDATE;";
        // Solve the NullPointerException when has no record in the table
        if (codeDao.getCell(sql, params) == null) {
            return 1;
        }
        return (Integer) codeDao.getCell(sql, params);
    }

    /**
     * Reset the value of the userId if transaction execute failed
     *
     * @param oldId The old value of the userId before transaction start
     */
    public void resetToOldId(int oldId) {
        Object[] params = new Object[]{oldId};
        String sql = "ALTER TABLE Code AUTO_INCREMENT = ?";
        codeDao.update(sql, params);
    }
}
