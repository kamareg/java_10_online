package ua.com.alevel.service;

import ua.com.alevel.db.author.AuthorStorage;
import ua.com.alevel.db.book.BookStorage;
import ua.com.alevel.db.bookAuthor.BookAuthorRelation;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookAuthor;
import ua.com.alevel.factory.AuthorStorageFactory;
import ua.com.alevel.factory.BookStorageFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class BookAuthorService {
    private final BookAuthorRelation bookAuthorRelation = BookAuthorRelation.getInstance();
    private final AuthorStorage authorStorage = AuthorStorageFactory.getAuthorStorage();
    private final BookStorage bookStorage = BookStorageFactory.getBookStorage();

    public void create(BookAuthor bookAuthor) {
        if (!isTrueParameters(bookAuthor) && !findPair(bookAuthor)) {
            bookAuthorRelation.create(bookAuthor);
        }
    }

    public Author[] findAllAuthors() {
        return authorStorage.findAll();
    }

    public Book[] findAllBooks() {
        return bookStorage.findAll();
    }

    public Author[] findAllAuthorsByID(String id) {
        if (bookStorage.findById(id) == null) {
            return new Author[0];
        }
        ArrayList<String> authorsID = bookAuthorRelation.findAllAuthorsByBook(id);
        Author[] allAuthors = authorStorage.findAll();
        return Arrays.stream(allAuthors)
                .filter(author -> authorsID.contains(author.getId()))
                .toArray(Author[]::new);
    }

    public Book[] findAllBooksByID(String id) {
        if (authorStorage.findById(id) == null) {
            return new Book[0];
        }
        ArrayList<String> booksID = bookAuthorRelation.findAllBooksByAuthor(id);
        Book[] allBooks = bookStorage.findAll();
        return Arrays.stream(allBooks)
                .filter(book -> booksID.contains(book.getId()))
                .toArray(Book[]::new);
    }

    public void delete(BookAuthor bookAuthor) {
        if (!isTrueParameters(bookAuthor) && findPair(bookAuthor)) {
            bookAuthorRelation.delete(bookAuthor);
        }
    }

    private boolean isTrueParameters(BookAuthor bookAuthor) {
        return bookStorage.findById(bookAuthor.getBookId()) == null
                || authorStorage.findById(bookAuthor.getAuthorId()) == null;
    }

    private boolean findPair(BookAuthor bookAuthor) {
        for (BookAuthor bA : bookAuthorRelation.findAll()) {
            if (bA.getAuthorId().equals(bookAuthor.getAuthorId())
                    && bA.getBookId().equals(bookAuthor.getBookId())) {
                return true;
            }
        }
        return false;
    }
}
