package com.springbear.ebrss.ui;

import com.springbear.ebrss.thread.HandleRequest;
import com.springbear.ebrss.util.ThreadPoolUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Properties;

/**
 * Server - communicate with the client
 * @author Spring-_-Bear
 * @date 2021-12-18 20:46
 */
public class Server {
    /**
     * The path of the config file
     */
    private static final String PATH = "config\\port.properties";
    /**
     * The listening port of the server
     */
    private static int port;

    // Read the config info from the config file
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH));
            port = Integer.parseInt(properties.getProperty("port"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            serverSocket = new ServerSocket(port);
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
