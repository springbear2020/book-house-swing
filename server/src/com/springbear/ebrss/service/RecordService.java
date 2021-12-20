package com.springbear.ebrss.service;

import com.springbear.ebrss.dao.RecordDao;
import com.springbear.ebrss.entity.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Download record service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 10:35
 */
public class RecordService {
    private final RecordDao recordDao = new RecordDao();

    /**
     * Insert a download record
     *
     * @param record Request content object
     * @return true - insert successfully or false
     */
    public synchronized boolean saveDownloadRecord(Record record) {
        Object[] params = new Object[]{record.getTitle(), record.getAuthor(), record.getTime(), record.getUsername()};
        String sql = "INSERT INTO Record(title,author,time,username) VALUES (?,?,?,?);";
        return recordDao.update(sql, params) == 1;
    }

    /**
     * Query the download record by the username
     *
     * @param record Request content object
     * @return The collection of download record or null
     */
    public synchronized List<Object> queryDownloadRecordByUsername(Record record) {
        Object[] params = new Object[]{record.getUsername()};
        String sql = "SELECT * FROM Record WHERE username = ?;";
        return new ArrayList<>(recordDao.listRecords(sql, Record.class, params));
    }
}
