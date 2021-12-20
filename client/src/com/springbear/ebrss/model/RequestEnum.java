package com.springbear.ebrss.model;

/**
 * The request agreed upon by the client and the server
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:20
 */
public enum RequestEnum {
    /**
     * Request - verify the existence of the username
     */
    VERIFY_USERNAME_EXISTENCE,
    /**
     * Request - verify the existence the register code
     */
    VERIFY_CODE_EXISTENCE,
    /**
     * Request - save user register info
     */
    SAVE_REGISTER_USER,
    /**
     * Request - verify whether the username and password are right when user login
     */
    VERIFY_USERNAME_AND_PASSWORD,
    /**
     * Request - query user info though the username
     */
    QUERY_USER_BY_USERNAME,
    /**
     * Request - update user info though the username
     */
    UPDATE_USER_INFORMATION,
    /**
     * Request - account cancellation though the username
     */
    ACCOUNT_CANCELLATION,
    /**
     * Request - query all book from book view
     */
    QUERY_ALL_BOOKS_FROM_VIEW,
    /**
     * Request - query books' info by the name of the book from the book view
     */
    QUERY_BOOKS_BY_TITLE_FROM_VIEW,
    /**
     * Request - query books' info by the author of the book from the book view
     */
    QUERY_BOOKS_BY_AUTHOR_FROM_VIEW,
    /**
     * Request - query books' info by the category of the book from the book view
     */
    QUERY_BOOKS_BY_CATEGORY_FROM_VIEW,
    /**
     * Request - query books' info by the keyword of the book from the book view
     */
    QUERY_BOOKS_BY_KEYWORD_FROM_VIEW,
    /**
     * Request - download the book from the server by the book's isbn
     */
    DOWNLOAD_BOOK_BY_ISBN,
    /**
     * Request - auto add new download record of the user when user download book successfully
     */
    AUTO_SAVE_DOWNLOAD_RECORD,
    /**
     * Request - query the download record by the username
     */
    QUERY_RECORD_BY_USERNAME,
    /**
     * Request - save the favorite record of the user
     */
    SAVE_USER_FAVORITE,
    /**
     * Request - query the favorite record though the username
     */
    QUERY_USER_FAVORITE,
    /**
     * Request - delete the favorite record of the user selected
     */
    DELETE_USER_FAVORITE,
    /**
     * Request - user upload book file to the server
     */
    UPLOAD_BOOK_FILE,
}
