package ua.com.alevel.db.book;

import ua.com.alevel.entity.Book;

public interface BookStorage {
    void create(Book book);

    void update(Book book);

    void delete(String id);

    Book findById(String id);

    Book[] findAll();

    String generateID();
}
