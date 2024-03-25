package ua.com.alevel.service;

import ua.com.alevel.db.AuthorDb;
import ua.com.alevel.db.BookAuthorDb;
import ua.com.alevel.db.Db;
import ua.com.alevel.entity.Author;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;

import java.util.ArrayList;

public class AuthorService {
    private final Db<Author> authorStorage = ObjectFactory.getInstance().getService(AuthorDb.class);

    public void create(Author author) {
        if (authorValidator(author)) {
            throw new WrongInputException("Please try again. Name can't be empty.");
        }
        authorStorage.create(author);
    }

    public void update(Author author) {
        if (authorValidator(author)) {
            throw new WrongInputException("Please try again. Name can't be empty.");
        }
        authorStorage.update(author);
    }

    public void delete(String id) {
        ObjectFactory.getInstance().getService(BookAuthorDb.class).delete(id);
        authorStorage.delete(id);
    }

    public Author findById(String id) {
        return authorStorage.findById(id).orElseThrow(() -> new EntityNotFoundException("Author was not found."));
    }

    public ArrayList<Author> findAll() {
        return authorStorage.findAll();
    }

    private boolean authorValidator(Author author) {
        return author.getFirstName().length() <= 0
                || author.getLastName().length() <= 0;
    }
}
