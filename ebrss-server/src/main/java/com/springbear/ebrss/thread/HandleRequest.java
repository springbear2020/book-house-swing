package com.springbear.ebrss.thread;

import com.springbear.ebrss.Server;
import com.springbear.ebrss.model.RequestEnum;
import com.springbear.ebrss.util.ThreadPoolUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Classify requests from clients, then new thread to deal with the different requests
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:49
 */
public class HandleRequest implements Runnable {
    private final Socket socket;

    public HandleRequest(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader;

        try {
            // Read the request type from the client
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = bufferedReader.readLine();

            // Print the message of the client which connected with the server
            System.out.println("No. " + Server.getCountClients() + " client has connected，info " + socket.getRemoteSocketAddress() + " request type: " + request);

            // Start threads to process requests according to different request types
            if (RequestEnum.VERIFY_USERNAME_EXISTENCE.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.VERIFY_CODE_EXISTENCE.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.SAVE_REGISTER_USER.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.VERIFY_USERNAME_AND_PASSWORD.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.QUERY_USER_BY_USERNAME.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendObjects(bufferedReader, socket, request));
            }
            if (RequestEnum.UPDATE_USER_INFORMATION.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.ACCOUNT_CANCELLATION.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.QUERY_ALL_BOOKS_FROM_VIEW.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendObjects(bufferedReader, socket, request));
            }
            if (RequestEnum.QUERY_BOOKS_BY_TITLE_FROM_VIEW.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendObjects(bufferedReader, socket, request));
            }
            if (RequestEnum.QUERY_BOOKS_BY_AUTHOR_FROM_VIEW.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendObjects(bufferedReader, socket, request));
            }
            if (RequestEnum.QUERY_BOOKS_BY_KEYWORD_FROM_VIEW.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendObjects(bufferedReader, socket, request));
            }
            if (RequestEnum.QUERY_BOOKS_BY_CATEGORY_FROM_VIEW.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendObjects(bufferedReader, socket, request));
            }
            if (RequestEnum.DOWNLOAD_BOOK_BY_ISBN.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendByteData(bufferedReader, socket));
            }
            if (RequestEnum.AUTO_SAVE_DOWNLOAD_RECORD.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.QUERY_RECORD_BY_USERNAME.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendObjects(bufferedReader, socket, request));
            }
            if (RequestEnum.SAVE_USER_FAVORITE.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.QUERY_USER_FAVORITE.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendObjects(bufferedReader, socket, request));
            }
            if (RequestEnum.DELETE_USER_FAVORITE.toString().equals(request)) {
                ThreadPoolUtil.execute(new SendFeedback(bufferedReader, socket, request));
            }
            if (RequestEnum.UPLOAD_BOOK_FILE.toString().equals(request)) {
                ThreadPoolUtil.execute(new ReceiveByteData(bufferedReader, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
