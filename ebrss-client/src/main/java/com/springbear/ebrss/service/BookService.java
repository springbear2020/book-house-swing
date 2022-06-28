package com.springbear.ebrss.service;

import com.springbear.ebrss.client.Client;
import com.springbear.ebrss.entity.Book;

import java.util.List;

/**
 * Book service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 09:18
 */
public class BookService {
    private final Client client = new Client();

    /**
     * Query books' info by the specific filter condition from the server
     *
     * @param request QUERY_BOOKS_BY_TITLE or QUERY_BOOKS_BY_AUTHOR or
     *                QUERY_BOOKS_BY_CATEGORY or QUERY_BOOKS_BY_KEYWORD of
     *                QUERY_ALL_BOOKS
     * @param book    Request content object
     * @return The books' info array or null
     */
    public Object[][] queryBooksByCondition(String request, Book book) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Receive book objects from server
        List<Object> list = client.sendRequestContentObjectAndReceiveObjects(book);
        if (list.isEmpty()) {
            return null;
        }

        // Convert the List the Array so that we can easily make table to display the book' info
        int countsRows = list.size();
        Object[][] rowData = new Object[countsRows][];
        for (int i = 0; i < countsRows; i++) {
            Book bookObject = (Book) list.get(i);
            rowData[i] = new Object[]{
                    bookObject.getIsbn(), bookObject.getTitle(),
                    bookObject.getAuthor(), bookObject.getKeyword(),
                    bookObject.getCategory(),};
        }
        return rowData;
    }

    /**
     * Download book from server by the book's isbn number
     *
     * @param request DOWNLOAD_BOOK_BY_ISBN
     * @param book    Request content object
     * @return Book byte data or null
     */
    public byte[] downloadBookByIsbn(String request, Book book) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Receive the book byte data from the server
        return client.sendObjectToServerAndReceiveByteData(book);
    }

    /**
     * Upload the book byte data to the server
     *
     * @param request  UPLOAD_FILE
     * @param fileData Book byte data
     * @return true - upload successfully or false
     */
    public boolean uploadBookFile(String request, byte[] fileData) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send book byte data to the server and receive the feedback from the server
        return client.sendByteDataToServerAndReceiveFeedback(fileData);
    }
}
