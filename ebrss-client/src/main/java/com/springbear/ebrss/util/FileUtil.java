package com.springbear.ebrss.util;

import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 * File operation tools class
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 09:58
 */
public class FileUtil {
    /**
     * Save the file data the the specific disk path,
     * the file name and the path are contained in the savePath
     *
     * @param fileData File byte data
     * @param savePath The full path of the file
     * @return true - save successfully or false
     */
    public static boolean saveFileToDisk(byte[] fileData, String savePath) {
        if (fileData == null) {
            return false;
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(savePath);
            fileOutputStream.write(fileData);
            fileOutputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                Objects.requireNonNull(fileOutputStream).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the byte data of the file from the file path on the disk
     *
     * @param filePath File full path
     * @return Byte file data of null
     */
    public static byte[] getFileDataFromDisk(String filePath) {
        File file = new File(filePath);

        // File not exists or not a file, return directly
        if (!file.exists() && file.isFile()) {
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            fileInputStream = new FileInputStream(file);

            int readLen;
            byte[] buf = new byte[1024];
            // Read byte data of file
            while ((readLen = fileInputStream.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, readLen);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                Objects.requireNonNull(byteArrayOutputStream).close();
                Objects.requireNonNull(fileInputStream).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the BeFree.jpg file url
     */
    public static URL getBeFreeUrl() {
        return FileUtil.class.getClassLoader().getResource("image/BeFree.jpg");
    }
}
