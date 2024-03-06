package ua.com.alevel.service;

import ua.com.alevel.db.author.AuthorStorage;
import ua.com.alevel.db.bookAuthor.BookAuthorRelation;
import ua.com.alevel.entity.Author;
import ua.com.alevel.factory.AuthorStorageFactory;

public class AuthorService {
    private final AuthorStorage authorStorage = AuthorStorageFactory.getAuthorStorage();

    public void create(Author author) {
        authorStorage.create(author);
    }

    public void update(Author author) {
        authorStorage.update(author);
    }

    public void delete(String id) {
        authorStorage.delete(id);
        BookAuthorRelation.getInstance().removeDeleted(id);
    }

    public Author findById(String id) {
        return authorStorage.findById(id);
    }

    public Author[] findAll() {
        return authorStorage.findAll();
    }
}
