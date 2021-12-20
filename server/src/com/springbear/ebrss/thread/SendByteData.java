package com.springbear.ebrss.thread;

import com.springbear.ebrss.entity.Book;
import com.springbear.ebrss.service.BookService;
import com.springbear.ebrss.util.FileUtil;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

/**
 * Receive the request content object from the client,
 * then load the file from the disk according to the request content object,
 * send client the file byte data finally
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 10:06
 */
public class SendByteData implements Runnable {
    private final BufferedReader bufferedReader;
    private final Socket socket;

    private final BookService bookService = new BookService();

    public SendByteData(BufferedReader bufferedReader, Socket socket) {
        this.bufferedReader = bufferedReader;
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        BufferedWriter bufferedWriter = null;
        OutputStream outputStream = null;

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

            // Read the file save path from the database table
            String filePath = bookService.getBookUrlByIsbn((Book) object);
            // Load the byte data of the file from the disk
            byte[] fileData = FileUtil.getFileDataFromDisk(filePath);

            // Send file byte data to the client
            outputStream = socket.getOutputStream();
            outputStream.write(fileData != null ? fileData : new byte[0]);
            outputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(outputStream).close();
                Objects.requireNonNull(bufferedWriter).close();
                Objects.requireNonNull(objectInputStream).close();
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
