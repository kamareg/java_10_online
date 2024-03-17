package ua.com.alevel.service;

import ua.com.alevel.db.AuthorDb;
import ua.com.alevel.db.BookAuthorDb;
import ua.com.alevel.db.BookDb;
import ua.com.alevel.db.Db;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookAuthor;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookAuthorService {

    private final Db<Author> authorStorage = ObjectFactory.getInstance().getService(AuthorDb.class);
    private final Db<Book> bookStorage = ObjectFactory.getInstance().getService(BookDb.class);
    private final Db<BookAuthor> bookAuthorStorage = ObjectFactory.getInstance().getService(BookAuthorDb.class);


    public void create(BookAuthor bookAuthor) {
        if (findPair(bookAuthor).isPresent()) {
            throw new WrongInputException("Please try again. Pair has already exist.");
        }
        bookAuthorStorage.create(bookAuthor);
    }

    public void delete(BookAuthor bookAuthor) {
        Optional<BookAuthor> ba = findPair(bookAuthor);
        if (ba.isEmpty()) {
            throw new WrongInputException("Please try again. Pair was not found.");
        }
        bookAuthorStorage.delete(ba.get().getId());
    }

    public ArrayList<Book> findAllBooksByID(String id) {
        ArrayList<Book> books = bookStorage.findAll();
        ArrayList<String> bookIds = bookAuthorStorage.findAll().stream()
                .filter(ba -> ba.getAuthorId().equals(id))
                .map(BookAuthor::getBookId)
                .collect(Collectors.toCollection(ArrayList::new));
        return books.stream()
                .filter(book -> bookIds.contains(book.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Author> findAllAuthorsByBook(String id) {
        ArrayList<Author> authors = authorStorage.findAll();
        ArrayList<String> authorIds = bookAuthorStorage.findAll().stream()
                .filter(ba -> ba.getBookId().equals(id))
                .map(BookAuthor::getAuthorId)
                .collect(Collectors.toCollection(ArrayList::new));
        return authors.stream()
                .filter(author -> authorIds.contains(author.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isNotExistsId(BookAuthor bookAuthor) {
        return bookStorage.findById(bookAuthor.getBookId()).isEmpty()
                || authorStorage.findById(bookAuthor.getAuthorId()).isEmpty();
    }

    private Optional<BookAuthor> findPair(BookAuthor bookAuthor) {
        if (isNotExistsId(bookAuthor)) {
            throw new WrongInputException("Please try again. Id not found.");
        }
        return bookAuthorStorage.findAll().stream()
                .filter(ba -> ba.getAuthorId().equals(bookAuthor.getAuthorId())
                        && ba.getBookId().equals(bookAuthor.getBookId())).findFirst();
    }
}
