package ua.com.alevel.service;

import ua.com.alevel.db.author.AuthorStorage;
import ua.com.alevel.db.bookAuthor.BookAuthorRelation;
import ua.com.alevel.entity.Author;
import ua.com.alevel.factory.AuthorStorageFactory;

public class AuthorService {
    private final AuthorStorage authorStorage = AuthorStorageFactory.getAuthorStorage();

    public boolean create(Author author) {
        if (authorValidator(author)) {
            authorStorage.create(author);
            return true;
        }
        return false;
    }

    public boolean update(Author author) {
        if (authorValidator(author)) {
            authorStorage.update(author);
            return true;
        }
        return false;
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

    private boolean authorValidator(Author author) {
        return author.getFirstName().length() > 0
                && author.getLastName().length() > 0;
    }
}
