package ua.com.alevel.dao;

import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;

import java.util.ArrayList;

public interface BookDao extends CrudDao<Book> {
    void attachBookToAuthor(Long bookId, Long authorId);

    void detachBookFromAuthor(Long bookId, Long authorId);

    ArrayList<Book> findAllBooksByAuthor(Long authorId);

    ArrayList<Author> findAllAuthorsByBook(Long bookId);
}
