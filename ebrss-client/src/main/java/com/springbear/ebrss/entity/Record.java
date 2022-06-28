package com.springbear.ebrss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO class - Record table of database named ebrss
 *
 * @author Spring-_-Bear
 * @date 2021-12-19 10:23
 */
public class Record implements Serializable {
    /**
     * The id of the download record
     */
    private Integer recordId;
    /**
     * The name of the book
     */
    private String title;
    /**
     * The author of the book
     */
    private String author;
    /**
     * The download time of the download record
     */
    private Date time;
    /**
     * The username of the download record
     */
    private String username;

    public Record() {
    }

    public Record(String title, String author, Date time, String username) {
        this.title = title;
        this.author = author;
        this.time = time;
        this.username = username;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
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
        return "Record{" +
                "recordId=" + recordId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", time=" + time +
                ", username='" + username + '\'' +
                '}';
    }
}