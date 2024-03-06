package ua.com.alevel.service;

import ua.com.alevel.db.book.BookStorage;
import ua.com.alevel.db.bookAuthor.BookAuthorRelation;
import ua.com.alevel.entity.Book;
import ua.com.alevel.factory.BookStorageFactory;

public class BookService {
    private final BookStorage bookStorage = BookStorageFactory.getBookStorage();

    public void create(Book book) {
        bookStorage.create(book);
    }

    public void update(Book book) {
        bookStorage.update(book);
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
}
