package com.springbear.ebrss.thread;

import com.springbear.ebrss.util.FileUtil;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Receive the file byte data from the server then save it to the disk
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 11:41
 */
public class ReceiveByteData implements Runnable {
    /**
     * The file save path
     */
    private static String fileSavePath = "d:\\";
    private final BufferedReader bufferedReader;
    private final Socket socket;

    public ReceiveByteData(BufferedReader bufferedReader, Socket socket) {
        this.bufferedReader = bufferedReader;
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedWriter bufferedWriter = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            // Send the client a feedback that the server has received the request type
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("Server have received your request, please start sending now");
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // The full path of the file which the next will save
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分s秒");
            String formatDate = simpleDateFormat.format(date);
            fileSavePath = fileSavePath + formatDate + " .pdf";

            // Read the file byte data from client
            int readLen;
            byte[] buf = new byte[1024];
            inputStream = socket.getInputStream();
            byteArrayOutputStream = new ByteArrayOutputStream();
            do {
                readLen = inputStream.read(buf);
                byteArrayOutputStream.write(buf, 0, readLen);
            } while (readLen >= 1024);
            byte[] fileData = byteArrayOutputStream.toByteArray();

            String feedBack = "false";
            // Save the byte data to the specific path on the disk then send client a feedback
            if (FileUtil.saveFileToDisk(fileData, fileSavePath)) {
                feedBack = "true";
                fileSavePath = "d:\\upload\\";
            }

            // Send a feedback to the client
            bufferedWriter.write(feedBack);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(byteArrayOutputStream).close();
                inputStream.close();
                Objects.requireNonNull(bufferedWriter).close();
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
