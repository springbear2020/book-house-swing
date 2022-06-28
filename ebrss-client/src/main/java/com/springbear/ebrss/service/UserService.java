package com.springbear.ebrss.service;

import com.springbear.ebrss.client.Client;
import com.springbear.ebrss.entity.User;

import java.util.List;

/**
 * User service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:21
 */
public class UserService {
    private final Client client = new Client();

    /**
     * Verify the existence of the username when user register input
     *
     * @param request VERIFY_USERNAME_EXISTENCE
     * @param user    Request content object
     * @return true - username has exists or false
     */
    public boolean verifyUsernameExistence(String request, User user) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server then receive the feedback
        return client.sendRequestContentObjectThenReceiveFeedback(user);
    }

    /**
     * Save the user register info when user register successfully
     *
     * @param request SAVE_REGISTER_USER
     * @param user    Request content object
     * @return true - save user successfully or false
     */
    public boolean saveRegisterUserInfo(String request, User user) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the feedback
        return client.sendRequestContentObjectThenReceiveFeedback(user);
    }

    /**
     * Verify the correctness of the user name and password when the user login
     *
     * @param request VERIFY_USERNAME_PASSWORD_VALID
     * @param user    Request content object
     * @return true - the username and password are right or false
     */
    public boolean verifyUsernameAndPassword(String request, User user) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the feedback
        return client.sendRequestContentObjectThenReceiveFeedback(user);
    }

    /**
     * Query user info by the username
     *
     * @param request QUERY_USER_BY_USERNAME
     * @param user    Request content object
     * @return the user info of the specific username or null
     */
    public User queryUserByUsername(String request, User user) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the user objects
        List<Object> objectList = client.sendRequestContentObjectAndReceiveObjects(user);
        if (objectList.isEmpty()) {
            return null;
        }
        return (User) objectList.get(0);
    }

    /**
     * Update the user info by the specific username
     *
     * @param request UPDATE_USER_INFORMATION
     * @param user    Request content object
     * @return true - update successfully or false
     */
    public boolean updateUserInfo(String request, User user) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the feedback
        return client.sendRequestContentObjectThenReceiveFeedback(user);
    }

    /**
     * Account cancellation though the username
     *
     * @param request ACCOUNT_CANCELLATION
     * @param user    Request content object
     * @return true - account cancellation successfully or false
     */
    public boolean userCancellation(String request, User user) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the feedback
        return client.sendRequestContentObjectThenReceiveFeedback(user);
    }
}
