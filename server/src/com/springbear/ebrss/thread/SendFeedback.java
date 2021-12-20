package com.springbear.ebrss.thread;

import com.springbear.ebrss.entity.Code;
import com.springbear.ebrss.entity.Favorite;
import com.springbear.ebrss.entity.Record;
import com.springbear.ebrss.entity.User;
import com.springbear.ebrss.model.RequestEnum;
import com.springbear.ebrss.service.CodeService;
import com.springbear.ebrss.service.FavoriteService;
import com.springbear.ebrss.service.RecordService;
import com.springbear.ebrss.service.UserService;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

/**
 * Receive request content object from the client,
 * then do some specific operation by the request content object and request type,
 * finally send the operation result as a feedback to the client
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:52
 */
public class SendFeedback implements Runnable {
    private final BufferedReader bufferedReader;
    private final Socket socket;
    private final String request;

    private final UserService userService = new UserService();
    private final CodeService codeService = new CodeService();
    private final RecordService recordService = new RecordService();
    private final FavoriteService favoriteService = new FavoriteService();

    public SendFeedback(BufferedReader bufferedReader, Socket socket, String request) {
        this.bufferedReader = bufferedReader;
        this.socket = socket;
        this.request = request;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        BufferedWriter bufferedWriter = null;

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

            String feedBack = "false";
            /*
             * Do some specific operation by the request type and the request content object,
             * send the operation result as a feedback to the client finally
             */
            if (RequestEnum.VERIFY_USERNAME_EXISTENCE.toString().equals(request)) {
                if (object instanceof User && userService.getUsernameByUsername((User) object)) {
                    feedBack = "true";
                }
            }
            if (RequestEnum.VERIFY_CODE_EXISTENCE.toString().equals(request)) {
                if (object instanceof Code && codeService.getRegisterCodeByCode((Code) object)) {
                    feedBack = "true";
                }
            }
            if (RequestEnum.SAVE_REGISTER_USER.toString().equals(request)) {
                if (object instanceof User && userService.saveUser((User) object)) {
                    feedBack = "true";
                }
            }
            if (RequestEnum.VERIFY_USERNAME_AND_PASSWORD.toString().equals(request)) {
                if (object instanceof User && userService.getUserRecordByUsernameAndPassword((User) object)) {
                    feedBack = "true";
                }
            }
            if (RequestEnum.UPDATE_USER_INFORMATION.toString().equals(request)) {
                if (object instanceof User && userService.updateUserInfo((User) object)) {
                    feedBack = "true";
                }
            }
            if (RequestEnum.ACCOUNT_CANCELLATION.toString().equals(request)) {
                if (object instanceof User && userService.accountCancellation((User) object)) {
                    feedBack = "true";
                }
            }
            if (RequestEnum.AUTO_SAVE_DOWNLOAD_RECORD.toString().equals(request)) {
                if (object instanceof Record && recordService.saveDownloadRecord((Record) object)) {
                    feedBack = "true";
                }
            }
            if (RequestEnum.SAVE_USER_FAVORITE.toString().equals(request)) {
                if (object instanceof Favorite && favoriteService.saveFavorite((Favorite) object)) {
                    feedBack = "true";
                }
            }
            if (RequestEnum.DELETE_USER_FAVORITE.toString().equals(request)) {
                if (object instanceof Favorite && favoriteService.deleteUserFavorite((Favorite) object)) {
                    feedBack = "true";
                }
            }
            // Send the operation result to the client
            bufferedWriter.write(feedBack);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
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
