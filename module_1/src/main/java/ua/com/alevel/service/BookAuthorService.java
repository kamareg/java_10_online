package ua.com.alevel.service;

import ua.com.alevel.db.author.AuthorStorage;
import ua.com.alevel.db.book.BookStorage;
import ua.com.alevel.db.bookAuthor.BookAuthorRelation;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookAuthor;
import ua.com.alevel.factory.AuthorStorageFactory;
import ua.com.alevel.factory.BookStorageFactory;

public class BookAuthorService {
    private final BookAuthorRelation bookAuthorRelation = BookAuthorRelation.getInstance();
    private final AuthorStorage authorStorage = AuthorStorageFactory.getAuthorStorage();
    private final BookStorage bookStorage = BookStorageFactory.getBookStorage();

    public boolean create(BookAuthor bookAuthor) {
        if (isTrueParameters(bookAuthor)) {
            return false;
        }
        if (findPair(bookAuthor)) {
            return false;
        }
        bookAuthorRelation.create(bookAuthor);
        return true;
    }

    public Author[] findAllAuthors(String id) {
        if (bookStorage.findById(id) == null) {
            return new Author[0];
        }
        String[] authorsID = bookAuthorRelation.findAllAuthorsByBook(id);
        int count = 0;
        for (String s : authorsID) {
            if (s != null) {
                count++;
            }
        }
        Author[] allAuthors = authorStorage.findAll();
        Author[] authors = new Author[count];
        count = 0;
        for (Author allAuthor : allAuthors) {
            for (String s : authorsID) {
                if (allAuthor != null && allAuthor.getId().equals(s)) {
                    authors[count++] = allAuthor;
                }
            }
        }
        return authors;
    }

    public Book[] findAllBooks(String id) {
        if (authorStorage.findById(id) == null) {
            return new Book[0];
        }
        String[] booksID = bookAuthorRelation.findAllBooksByAuthor(id);
        int count = 0;
        for (String s : booksID) {
            if (s != null) {
                count++;
            }
        }
        Book[] allBooks = bookStorage.findAll();
        Book[] books = new Book[count];
        count = 0;
        for (Book allBook : allBooks) {
            for (String s : booksID) {
                if (allBook != null && allBook.getId().equals(s)) {
                    books[count++] = allBook;
                }
            }
        }
        return books;
    }

    public boolean delete(BookAuthor bookAuthor) {
        if (isTrueParameters(bookAuthor)) {
            return false;
        }
        if (!findPair(bookAuthor)) {
            return false;
        }
        bookAuthorRelation.delete(bookAuthor);
        return true;
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
