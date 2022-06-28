package com.springbear.ebrss.service;

import com.springbear.ebrss.dao.BookDao;
import com.springbear.ebrss.entity.Book;
import com.springbear.ebrss.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Book service class
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 23:26
 */
public class BookService {
    private final BookDao bookDao = new BookDao();

    /**
     * Add a new book record
     *
     * @param book Request content object
     * @return true - insert successfully or false
     */
    public boolean saveBook(Book book) {
        String sql = "INSERT INTO Book(isbn, title, author, price, publisher, edition, keyword, url, category, bookState) VALUES(?,?,?,?,?,?,?,?,?,?);";
        Object[] params = new Object[]{
                book.getIsbn(), book.getTitle(), book.getAuthor(),
                book.getPrice(), book.getPublisher(), book.getEdition(),
                book.getKeyword(), book.getUrl(), book.getCategory(), book.getBookState()
        };
        return bookDao.update(sql, params) == 1;
    }

    /**
     * Query all books' info from the view
     *
     * @return Collection of books or null
     */
    public synchronized List<Object> getAllBooksFromView() {
        String sql = "SELECT * FROM view_book;";
        return new ArrayList<>(bookDao.listRecords(sql, Book.class, null));
    }


    /**
     * Query books' info by the name of the book from the view
     *
     * @param book Request content object
     * @return Collection of books or null
     */
    public synchronized List<Object> getBooksByTitleFromView(Book book) {
        Object[] params = new Object[]{"%" + book.getTitle() + "%"};
        String sql = "SELECT * FROM view_book WHERE title LIKE ? AND bookState = 'on';";
        return new ArrayList<>(bookDao.listRecords(sql, Book.class, params));
    }

    /**
     * Query books' info by the author of the book from the view
     *
     * @param book Request content object
     * @return Collection of books or null
     */
    public synchronized List<Object> getBooksByAuthorFromView(Book book) {
        Object[] params = new Object[]{"%" + book.getAuthor() + "%"};
        String sql = "SELECT * FROM view_book WHERE author LIKE ? AND bookState = 'on';";
        return new ArrayList<>(bookDao.listRecords(sql, Book.class, params));
    }

    /**
     * Query books' info by the keyword of the book from the view
     *
     * @param book Request content object
     * @return Collection of books or null
     */
    public synchronized List<Object> getBooksByKeywordFromView(Book book) {
        Object[] params = new Object[]{"%" + book.getKeyword() + "%"};
        String sql = "SELECT * FROM view_book WHERE keyword LIKE ? AND bookState = 'on';";
        return new ArrayList<>(bookDao.listRecords(sql, Book.class, params));
    }

    /**
     * Query books' info by the category of the book from the view
     *
     * @param book Request content object
     * @return Collection of books or null
     */
    public synchronized List<Object> getBooksByCategoryFromView(Book book) {
        Object[] params = new Object[]{book.getCategory()};
        String sql = "SELECT * FROM view_book WHERE category = ? AND bookState = 'on';";
        return new ArrayList<>(bookDao.listRecords(sql, Book.class, params));
    }

    /**
     * Query the book's save path by the isbn of the book
     *
     * @param book Request content object
     * @return The url of book or null
     */
    public synchronized String getBookUrlByIsbn(Book book) {
        Object[] params = new Object[]{book.getIsbn()};
        String sql = "SELECT url FROM Book WHERE isbn = ?;";
        String url = (String) bookDao.getCell(sql, params);
        String realPath = FileUtil.getBookPath();
        return realPath + url;
    }
}
