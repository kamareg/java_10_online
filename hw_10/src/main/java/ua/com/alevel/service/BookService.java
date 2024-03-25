package ua.com.alevel.service;

import ua.com.alevel.db.BookAuthorDb;
import ua.com.alevel.db.BookDb;
import ua.com.alevel.db.Db;
import ua.com.alevel.entity.Book;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookService {
    private final Db<Book> bookStorage = ObjectFactory.getInstance().getService(BookDb.class);

    public void create(Book book) {
        if (bookValidator(book)) {
            throw new WrongInputException("Please try again. Name can't be empty, year must be in range from 0 to current year.");
        }
        bookStorage.create(book);
    }

    public void update(Book book) {
        if (bookValidator(book)) {
            throw new WrongInputException("Please try again. Name can't be empty, year must be in range from 0 to current year.");
        }
        bookStorage.update(book);
    }

    public void delete(String id) {
        ObjectFactory.getInstance().getService(BookAuthorDb.class).delete(id);
        bookStorage.delete(id);
    }

    public Book findById(String id) {
        return bookStorage.findById(id).orElseThrow(() -> new EntityNotFoundException("Book was not found."));
    }

    public ArrayList<Book> findAll() {
        return bookStorage.findAll();
    }

    private boolean bookValidator(Book book) {
        return book.getTitle().length() <= 0
                || book.getYear() <= 0
                || book.getYear() > LocalDateTime.now().getYear();
    }
}
