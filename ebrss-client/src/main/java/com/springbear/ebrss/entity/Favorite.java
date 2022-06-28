package com.springbear.ebrss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO class - Favorite table of database named ebrss
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 10:54
 */
public class Favorite implements Serializable {
    /**
     * The isbn of the book
     */
    private String isbn;
    /**
     * The name of the book
     */
    private String title;
    /**
     * The author of the book
     */
    private String author;
    /**
     * The keyword of the book
     */
    private String keyword;
    /**
     * The category of the book
     */
    private String category;
    /**
     * The collect time of the user collect the book info
     */
    private Date time;
    /**
     * The user of the collect record
     */
    private String username;

    public Favorite() {
    }

    public Favorite(String isbn, String title, String author, String keyword, String category, Date time, String username) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.keyword = keyword;
        this.category = category;
        this.time = time;
        this.username = username;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", keyword='" + keyword + '\'' +
                ", category='" + category + '\'' +
                ", time=" + time +
                ", username='" + username + '\'' +
                '}';
    }
}
