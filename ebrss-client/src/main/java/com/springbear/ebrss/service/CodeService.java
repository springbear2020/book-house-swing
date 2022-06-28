package com.springbear.ebrss.service;

import com.springbear.ebrss.client.Client;
import com.springbear.ebrss.entity.Code;

/**
 * Code serviced class
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 21:29
 */
public class CodeService {
    private final Client client = new Client();

    /**
     * Verify the existence of the register code when user register input
     *
     * @param request VERIFY_CODE_EXISTENCE
     * @param code    Request content object
     * @return true - the register code has exists or false
     */
    public boolean verifyCodeExistence(String request, Code code) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server then receive the feedback
        return client.sendRequestContentObjectThenReceiveFeedback(code);
    }
}
