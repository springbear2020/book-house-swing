package com.springbear.ebrss.entity;

import java.io.Serializable;

/**
 * POJO class -> Book table of database named ebrss
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 23:11
 */
public class Book implements Serializable {
    /**
     * The id of the book
     */
    private Integer bookId;
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
     * The price of the book
     */
    private Double price;
    /**
     * The publisher of the book
     */
    private String publisher;
    /**
     * The published time of the book
     */
    private String edition;
    /**
     * The keyword of the book
     */
    private String keyword;
    /**
     * The save path of the book
     */
    private String url;
    /**
     * The category of the book
     */
    private String category;
    /**
     * The state of the book - on or off
     */
    private String bookState;

    public Book() {
    }

    public Book(String isbn, String title, String author, Double price, String publisher, String edition, String keyword, String url, String category, String bookState) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.edition = edition;
        this.keyword = keyword;
        this.url = url;
        this.category = category;
        this.bookState = bookState;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookState() {
        return bookState;
    }

    public void setBookState(String bookState) {
        this.bookState = bookState;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", publisher='" + publisher + '\'' +
                ", edition='" + edition + '\'' +
                ", keyword='" + keyword + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", bookState='" + bookState + '\'' +
                '}';
    }
}
