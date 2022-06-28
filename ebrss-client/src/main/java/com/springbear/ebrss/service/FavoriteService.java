package com.springbear.ebrss.service;

import com.springbear.ebrss.client.Client;
import com.springbear.ebrss.entity.Favorite;

import java.util.List;

/**
 * Favorites service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 11:02
 */
public class FavoriteService {
    private final Client client = new Client();

    /**
     * Save the user's favorite info
     *
     * @param request  SAVE_USER_FAVORITE
     * @param favorite Request content object
     * @return true - save successfully or false
     */
    public boolean saveUserFavorite(String request, Favorite favorite) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the feedback
        return client.sendRequestContentObjectThenReceiveFeedback(favorite);
    }

    /**
     * Query the user's favorite info by the username
     *
     * @param request  QUERY_USER_FAVORITE
     * @param favorite Request content object
     * @return The user' favorite record or null
     */
    public Object[][] queryFavoritesByUsername(String request, Favorite favorite) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the favorite objects
        List<Object> list = client.sendRequestContentObjectAndReceiveObjects(favorite);
        if (list.isEmpty()) {
            return null;
        }

        // Convert the List the Array so we can easily make table to display favorite info
        int countsRows = list.size();
        Object[][] rowData = new Object[countsRows][];
        for (int i = 0; i < countsRows; i++) {
            Favorite favoriteObj = (Favorite) list.get(i);
            rowData[i] = new Object[]{
                    favoriteObj.getIsbn(), favoriteObj.getTitle(), favoriteObj.getAuthor(),
                    favoriteObj.getKeyword(), favoriteObj.getCategory(), favoriteObj.getTime()};
        }
        return rowData;
    }

    /**
     * Delete the favorite record of the user selected
     *
     * @param request  DELETE_USER_FAVORITE
     * @param favorite Request content object
     * @return true - delete successfully or false
     */
    public boolean deleteUserFavorite(String request, Favorite favorite) {
        // Send request to the server
        client.sendRequestToServer(request);
        // Send request content object to the server and receive the feedback
        return client.sendRequestContentObjectThenReceiveFeedback(favorite);
    }
}
