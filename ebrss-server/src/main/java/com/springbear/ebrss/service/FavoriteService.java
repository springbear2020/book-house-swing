package com.springbear.ebrss.service;

import com.springbear.ebrss.dao.FavoriteDao;
import com.springbear.ebrss.entity.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * Favorite service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 11:06
 */
public class FavoriteService {
    private final FavoriteDao favoriteDao = new FavoriteDao();

    /**
     * Insert a favorite record
     *
     * @param favorite Request content object
     * @return true - insert successfully or false
     */
    public synchronized boolean saveFavorite(Favorite favorite) {
        Object[] params = new Object[]{
                favorite.getIsbn(), favorite.getTitle(), favorite.getAuthor(),
                favorite.getKeyword(), favorite.getCategory(), favorite.getTime(), favorite.getUsername()
        };
        String sql = "INSERT INTO Favorite(isbn,title,author,keyword,category,time,username) VALUES(?,?,?,?,?,?,?);";
        return favoriteDao.update(sql, params) == 1;
    }

    /**
     * Query favorite info by the username
     *
     * @param favorite Request content object
     * @return The favorite info objects collection or null
     */
    public synchronized List<Object> getFavoriteRecordByUsername(Favorite favorite) {
        Object[] params = new Object[]{favorite.getUsername()};
        String sql = "SELECT * FROM Favorite WHERE username = ?;";
        return new ArrayList<>(favoriteDao.listRecords(sql, Favorite.class, params));
    }

    /**
     * Delete a favorite record by the username and the isbn of the book
     *
     * @param favorite Request content object
     * @return true - delete successfully or false
     */
    public synchronized boolean deleteUserFavorite(Favorite favorite) {
        Object[] params = new Object[]{favorite.getIsbn(), favorite.getUsername()};
        String sql = "DELETE FROM Favorite WHERE isbn = ? AND username = ?;";
        return favoriteDao.update(sql, params) == 1;
    }
}
