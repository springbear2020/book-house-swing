package com.springbear.ebrss.util;

import javax.swing.*;
import java.io.*;

/**
 * Database backup tools class
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 21:39
 */
public class DatabaseUtil {
    /**
     * Backup database
     *
     * @param fileSavePath The save path of the backup file
     */
    public static void databaseBackup(String fileSavePath) {
        // Combining the parameters into a command
        String command = "mysqldump -h" + DruidUtil.getServerIp() + " -u" + DruidUtil.getUsername() +
                " -p" + DruidUtil.getPassword() + " -B " + DruidUtil.getDatabase() + " > " + fileSavePath + "\\" + DruidUtil.getDatabase() + ".sql";
        try {
            Runtime.getRuntime().exec("cmd /c" + command);
            JOptionPane.showMessageDialog(null, "Database backup successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database backup failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}