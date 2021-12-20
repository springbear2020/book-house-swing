package com.springbear.ebrss.service;

import com.springbear.ebrss.dao.UserDao;
import com.springbear.ebrss.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * User service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:55
 */
public class UserService {
    private final UserDao userDao = new UserDao();

    /**
     * Get username by the username
     *
     * @param user Request content object
     * @return true - username exists or false
     */
    public synchronized boolean getUsernameByUsername(User user) {
        Object[] params = new Object[]{user.getUsername()};
        String sql = "SELECT username FROM User WHERE username = ?;";
        return userDao.getCell(sql, params) != null;
    }

    /**
     * Add a user record
     *
     * @param user Request content object
     * @return true - insert successfully or false
     */
    public synchronized boolean saveUser(User user) {
        Object[] params = new Object[]{
                user.getUsername(), user.getPassword(), user.getName(),
                user.getSex(), user.getIdCard(), user.getPhone(),
                user.getMail(), user.getRegisterCode()
        };
        String sql = "INSERT INTO User(username,password,name,sex,idCard,phone,mail,registerCode) VALUES(?,?,?,?,?,?,?,?);";
        return userDao.update(sql, params) == 1;
    }

    /**
     * Get the user info by the username and the password
     *
     * @param user Request content object
     * @return true - username and password matched or false
     */
    public synchronized boolean getUserRecordByUsernameAndPassword(User user) {
        Object[] params = new Object[]{user.getUsername(), user.getPassword()};
        String sql = "SELECT username,password FROM User WHERE username = ? AND password = ?;";
        return userDao.getRecord(sql, User.class, params) != null;
    }

    /**
     * Get user info by the username
     *
     * @param user Request content object
     * @return The record of the username or null
     */
    public synchronized List<Object> getUserByUsername(User user) {
        Object[] params = new Object[]{user.getUsername()};
        String sql = "SELECT * FROM User WHERE username = ?;";
        User userObj = userDao.getRecord(sql, User.class, params);
        List<User> arrayList = new ArrayList<>();
        arrayList.add(userObj);
        return new ArrayList<>(arrayList);
    }

    /**
     * Update the user info
     *
     * @param user Request content object
     * @return true - update successfully or false
     */
    public synchronized boolean updateUserInfo(User user) {
        Object[] params = new Object[]{user.getPassword(), user.getPhone(), user.getMail(), user.getUsername()};
        String sql = "UPDATE User SET password = ?, phone = ?, mail = ? WHERE username = ?;";
        return userDao.update(sql, params) == 1;
    }

    /**
     * Delete user by the username
     *
     * @param user Request content object
     * @return true - username User delete successfully or false
     */
    public synchronized boolean accountCancellation(User user) {
        // Delete the user's all favorites though the username
        if (!deleteUserFavorites(user)) {
            return false;
        }
        // Delete the user's all download record though the username
        if (!deleteUserRecord(user)) {
            return false;
        }
        Object[] params = new Object[]{user.getUsername()};
        String sql = "DELETE FROM User WHERE username = ?;";
        return userDao.update(sql, params) == 1;
    }

    /**
     * Delete user's favorites though username
     *
     * @param user Request content object
     * @return true - user's favorites delete successfully
     */
    public synchronized boolean deleteUserFavorites(User user) {
        Object[] params = new Object[]{user.getUsername()};
        String sql = "DELETE FROM Favorite WHERE username = ?;";
        return userDao.update(sql, params) >= 0;
    }

    /**
     * Delete user's download record though username
     *
     * @param user Request content object
     * @return true - user's download record delete successfully
     */
    public synchronized boolean deleteUserRecord(User user) {
        Object[] params = new Object[]{user.getUsername()};
        String sql = "DELETE FROM Record WHERE username = ?;";
        return userDao.update(sql, params) >= 0;
    }
}
