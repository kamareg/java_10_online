package ua.com.alevel.service;

import ua.com.alevel.db.book.BookStorage;
import ua.com.alevel.db.bookAuthor.BookAuthorRelation;
import ua.com.alevel.entity.Book;
import ua.com.alevel.factory.BookStorageFactory;

import java.time.LocalDateTime;

public class BookService {
    private final BookStorage bookStorage = BookStorageFactory.getBookStorage();

    public boolean create(Book book) {
        if (bookValidator(book)) {
            bookStorage.create(book);
            return true;
        }
        return false;
    }

    public boolean update(Book book) {
        if (bookValidator(book)) {
            bookStorage.update(book);
            return true;
        }
        return false;
    }

    public void delete(String id) {
        bookStorage.delete(id);
        BookAuthorRelation.getInstance().removeDeleted(id);
    }

    public Book findById(String id) {
        return bookStorage.findById(id);
    }

    public Book[] findAll() {
        return bookStorage.findAll();
    }

    private boolean bookValidator(Book book) {
        return book.getTitle().length() > 0
                && book.getYear() != null && book.getYear() > 0
                && book.getYear() <= LocalDateTime.now().getYear();
    }
}
