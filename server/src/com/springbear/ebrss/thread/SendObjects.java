package com.springbear.ebrss.thread;

import com.springbear.ebrss.entity.Book;
import com.springbear.ebrss.entity.Favorite;
import com.springbear.ebrss.entity.Record;
import com.springbear.ebrss.entity.User;
import com.springbear.ebrss.model.RequestEnum;
import com.springbear.ebrss.service.BookService;
import com.springbear.ebrss.service.FavoriteService;
import com.springbear.ebrss.service.RecordService;
import com.springbear.ebrss.service.UserService;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

/**
 * Receive request content object from the client,
 * then do some operation by the request type and the request content object,
 * send operation result as objects to the client finally
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 08:26
 */
public class SendObjects implements Runnable {
    private final BufferedReader bufferedReader;
    private final Socket socket;
    private final String request;

    private final UserService userService = new UserService();
    private final BookService bookService = new BookService();
    private final RecordService recordService = new RecordService();
    private final FavoriteService favoriteService = new FavoriteService();

    public SendObjects(BufferedReader bufferedReader, Socket socket, String request) {
        this.bufferedReader = bufferedReader;
        this.socket = socket;
        this.request = request;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        BufferedWriter bufferedWriter = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            // Tell the client that server has received the request type
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("Server have received your request, please start sending now");
            bufferedWriter.newLine();
            bufferedWriter.flush();

            /*
             * Read the request content object from the client,
             * in fact, the object contains the parameters of the sql statement
             */
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();

            // All the operation result is a collection
            List<Object> objectList = null;

            // Do some specific operation
            if (object instanceof User && RequestEnum.QUERY_USER_BY_USERNAME.toString().equals(request)) {
                objectList = userService.getUserByUsername((User) object);
            }
            if (object instanceof Book && RequestEnum.QUERY_ALL_BOOKS_FROM_VIEW.toString().equals(request)) {
                objectList = bookService.getAllBooksFromView();
            }
            if (object instanceof Book && RequestEnum.QUERY_BOOKS_BY_TITLE_FROM_VIEW.toString().equals(request)) {
                objectList = bookService.getBooksByTitleFromView((Book) object);
            }
            if (object instanceof Book && RequestEnum.QUERY_BOOKS_BY_AUTHOR_FROM_VIEW.toString().equals(request)) {
                objectList = bookService.getBooksByAuthorFromView((Book) object);
            }
            if (object instanceof Book && RequestEnum.QUERY_BOOKS_BY_KEYWORD_FROM_VIEW.toString().equals(request)) {
                objectList = bookService.getBooksByKeywordFromView((Book) object);
            }
            if (object instanceof Book && RequestEnum.QUERY_BOOKS_BY_CATEGORY_FROM_VIEW.toString().equals(request)) {
                objectList = bookService.getBooksByCategoryFromView((Book) object);
            }
            if (object instanceof Record && RequestEnum.QUERY_RECORD_BY_USERNAME.toString().equals(request)) {
                objectList = recordService.queryDownloadRecordByUsername((Record) object);
            }
            if (object instanceof Favorite && RequestEnum.QUERY_USER_FAVORITE.toString().equals(request)) {
                objectList = favoriteService.getFavoriteRecordByUsername((Favorite) object);
            }

            // Send the operation result as objects to the client
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            for (Object obj : Objects.requireNonNull(objectList)) {
                objectOutputStream.writeObject(obj);
            }
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(objectOutputStream).close();
                Objects.requireNonNull(objectInputStream).close();
                Objects.requireNonNull(bufferedWriter).close();
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
