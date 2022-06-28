package com.springbear.ebrss;

import com.springbear.ebrss.thread.HandleRequest;
import com.springbear.ebrss.util.DruidUtil;
import com.springbear.ebrss.util.ThreadPoolUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * com.springbear.ebrss.Server - communicate with the client
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:46
 */
public class Server {
    /**
     * The listening port of the server
     */
    private static final Integer PORT = Integer.parseInt(DruidUtil.getServerListeningPort());

    /**
     * The amount of the clients which has connected with the server
     */
    private static int countClients = 0;

    public static int getCountClients() {
        return countClients;
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("The server has started, waiting to connect···");

            // Wait for the connection from the client in a loop
            while (true) {
                try {
                    socket = serverSocket.accept();
                    ++countClients;
                    // Handle the request from the client, then continue waiting
                    ThreadPoolUtil.execute(new HandleRequest(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(socket).close();
                serverSocket.close();
                ThreadPoolUtil.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
