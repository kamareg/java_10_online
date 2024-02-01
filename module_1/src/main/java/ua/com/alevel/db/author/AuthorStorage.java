package ua.com.alevel.db.author;

import ua.com.alevel.entity.Author;

public interface AuthorStorage {
    void create(Author author);

    void update(Author author);

    void delete(String id);

    Author findById(String id);

    Author[] findAll();

    String generateID();
}
