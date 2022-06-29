package com.springbear.ebrss.client;


import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Client - communicate with the server
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:23
 */
public class Client {
    /**
     * The ip of the server
     */
    private static String serverIp;
    /**
     * The listening port of the server
     */
    private static Integer serverPort;

    // Load the config file then read the config info of the server
    static {
        Properties properties = new Properties();
        try {
            InputStream resourceAsStream = Client.class.getClassLoader().getResourceAsStream("server.properties");
            properties.load(resourceAsStream);
            serverIp = properties.getProperty("serverIp");
            serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket socket;
    private BufferedWriter bufferedWriter = null;

    /**
     * Send the specific request type to the server
     *
     * @param request Request type
     */
    public void sendRequestToServer(String request) {
        try {
            // Try to make a connection with the server
            socket = new Socket(serverIp, serverPort);
            // Send the request to the server
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(request);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection timed out, try again later", "Connection failed", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Send request content object to the server,
     * in fact the request content object contains the parameters of sql,
     * finally receive the feedback form the server
     *
     * @param object Request content object
     * @return true or false
     */
    public boolean sendRequestContentObjectThenReceiveFeedback(Object object) {
        ObjectOutputStream objectOutputStream = null;
        BufferedReader bufferedReader = null;

        try {
            /*
             * Start sending the request content object until received the feedback from server,
             * the feedback content we expect that is Server have received your request, please start sending now,
             * then client start sending the request content object to the server
             */
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*
             * If nothing goes wrong, the next line return value is
             * Server have received your request, please start sending now
             */
            bufferedReader.readLine();

            // Send request content object to the server
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            // Receive the feedback message from the server, true or false
            String feedback = bufferedReader.readLine();
            return "true".equals(feedback);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                Objects.requireNonNull(bufferedReader).close();
                Objects.requireNonNull(objectOutputStream).close();
                bufferedWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send request content object to server,
     * in fact the request content object contains the parameters of sql,
     * then receive the objects from the server,
     * the objects from the server is the content that
     * the client want to query from the server
     *
     * @param object Request content object
     * @return object collection or null
     */
    public List<Object> sendRequestContentObjectAndReceiveObjects(Object object) {
        BufferedReader bufferedReader = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            /*
             * Start sending the request content object until received the feedback from the server,
             * the feedback content we expect that is
             * Server have received your request, please start sending now,
             * then client start sending the request content object to server
             */
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*
             * If nothing going wrong, the next line return value is
             * Server have received your request, please start sending now
             */
            bufferedReader.readLine();

            // Send request object content to the server
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            ArrayList<Object> objectArrayList = new ArrayList<>();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            /*
             * Receive the objects from the server,
             * the content of the objects is the some information that
             * the client want to get from the server
             */
            while (true) {
                /*
                 * The return value of objectInputStream.readObject() is not -1 or null,
                 * so make an exception to mark have read to the end of the object input stream
                 */
                try {
                    Object obj = objectInputStream.readObject();
                    objectArrayList.add(obj);
                } catch (EOFException e) {
                    break;
                }
            }
            return objectArrayList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                Objects.requireNonNull(objectInputStream).close();
                objectOutputStream.close();
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send request content object, and read the byte data from the server
     *
     * @param object Request content object
     * @return Byte data or null
     */
    public byte[] sendObjectToServerAndReceiveByteData(Object object) {
        BufferedReader bufferedReader = null;
        ObjectOutputStream objectOutputStream = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            // Receive the feedback content from the server
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // We expect the next line return value is Server have received your request, please start sending now
            bufferedReader.readLine();

            // Send request content object to the server
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            // Read the byte data from the server
            int readLen;
            byte[] buf = new byte[1024];
            inputStream = socket.getInputStream();
            byteArrayOutputStream = new ByteArrayOutputStream();
            while ((readLen = inputStream.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, readLen);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                Objects.requireNonNull(byteArrayOutputStream).close();
                Objects.requireNonNull(inputStream).close();
                Objects.requireNonNull(objectOutputStream).close();
                Objects.requireNonNull(bufferedReader).close();
                bufferedWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send byte data to server and receive the feedback of the server
     *
     * @param data Byte data
     * @return true - the server have received the byte data successfully or false
     */
    public boolean sendByteDataToServerAndReceiveFeedback(byte[] data) {
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;

        try {
            // Send the byte data until receive the feedback from the server
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // We expect the next line return value is Server have received your request, please start sending now
            bufferedReader.readLine();

            // Send request object content to the server
            outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();

            // Receive the feedback from the server, true or false
            String feedback = bufferedReader.readLine();
            return "true".equals(feedback);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
