package com.springbear.ebrss.service;

import com.springbear.ebrss.client.Client;
import com.springbear.ebrss.entity.Record;

import java.util.List;

/**
 * Download record service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 10:30
 */
public class RecordService {
    private final Client client = new Client();

    /**
     * Auto add download record of the user when user download book successfully
     *
     * @param request AUTO_SAVE_DOWNLOAD_RECORD
     * @param record  Request content object
     */
    public void autoAddDownloadRecord(String request, Record record) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the feedback
        client.sendRequestContentObjectThenReceiveFeedback(record);
    }

    /**
     * Query the user's download record by the username
     *
     * @param request QUERY_RECORD_BY_USERNAME
     * @param record  Request content object
     * @return Download record array or null
     */
    public Object[][] queryRecordByUsername(String request, Record record) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the download record objects
        List<Object> objectList = client.sendRequestContentObjectAndReceiveObjects(record);
        if (objectList.isEmpty()) {
            return null;
        }

        // Convert the List the Array so we can easily make table to display favorite info
        int countsRows = objectList.size();
        Object[][] rowData = new Object[countsRows][];
        for (int i = 0; i < countsRows; i++) {
            Record recordObj = (Record) objectList.get(i);
            rowData[i] = new Object[]{
                    recordObj.getTitle(), recordObj.getAuthor(), recordObj.getTime()};
        }
        return rowData;
    }
}
