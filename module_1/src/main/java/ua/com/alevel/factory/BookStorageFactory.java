package ua.com.alevel.factory;

import ua.com.alevel.db.book.ArrayBasedBookStorage;
import ua.com.alevel.db.book.BookStorage;

public class BookStorageFactory {
    private final static BookStorage bookStorage = new ArrayBasedBookStorage();

    public static BookStorage getBookStorage() {
        return bookStorage;
    }
}
