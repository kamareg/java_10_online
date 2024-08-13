package ua.com.alevel.service.impl;

import ua.com.alevel.dao.BookDao;
import ua.com.alevel.dao.DataTableRequest;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.service.BookService;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao = ObjectFactory.getInstance().getService(BookDao.class);

    @Override
    public void create(Book book) {
        if (bookValidator(book)) {
            throw new WrongInputException("Please try again. Name can't be empty, year must be in range from 0 to current year.");
        }
        bookDao.create(book);
    }

    @Override
    public void update(Book book) {
        if (bookValidator(book)) {
            throw new WrongInputException("Please try again. Name can't be empty, year must be in range from 0 to current year.");
        }
        bookDao.update(book);
    }

    @Override
    public void delete(Long id) {
        bookDao.delete(id);
    }

    @Override
    public ArrayList<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookDao.existById(id);
    }

    @Override
    public void attachBookToAuthor(Long bookId, Long authorId) {
        bookDao.attachBookToAuthor(bookId, authorId);
    }

    @Override
    public void detachBookFromAuthor(Long bookId, Long authorId) {
        bookDao.detachBookFromAuthor(bookId, authorId);
    }

    @Override
    public ArrayList<Book> findAllBooksByAuthor(Long authorId) {
        return bookDao.findAllBooksByAuthor(authorId);
    }

    @Override
    public ArrayList<Author> findAllAuthorsByBook(Long bookId) {
        return bookDao.findAllAuthorsByBook(bookId);
    }

    @Override
    public ArrayList<Book> findSortAndLimit(DataTableRequest request) {
        return bookDao.findSortAndLimit(request);
    }

    private boolean bookValidator(Book book) {
        return book.getTitle().length() <= 0
                || book.getYear() <= 0
                || book.getYear() > LocalDateTime.now().getYear();
    }
}
