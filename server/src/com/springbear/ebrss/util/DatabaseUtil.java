package com.springbear.ebrss.util;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

/**
 * Database backup tools class
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 21:39
 */
public class DatabaseUtil {
    /**
     * The path of the config file
     */
    private static final String PATH = "server\\config\\druid.properties";
    /**
     * The ip of server
     */
    private static String serverIp;
    /**
     * The username of login database
     */
    private static String username;
    /**
     * The password of user
     */
    private static String password;
    /**
     * The database name
     */
    private static String dbName;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH));
            serverIp = properties.getProperty("serverIp");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            dbName = "ebrss";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Backup database
     *
     * @param fileSavePath The save path of the backup file
     */
    public static void databaseBackup(String fileSavePath) {
        // Combining the parameters into a command
        String command = "mysqldump -h" + serverIp + " -u" + username + " -p" + password + " -B " + dbName + " > " + fileSavePath + "\\" + dbName + ".sql";
        try {
            Runtime.getRuntime().exec("cmd /c" + command);
            JOptionPane.showMessageDialog(null, "Database backup successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database backup failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}