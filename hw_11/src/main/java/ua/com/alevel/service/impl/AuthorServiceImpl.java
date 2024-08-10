package ua.com.alevel.service.impl;

import ua.com.alevel.dao.AuthorDao;
import ua.com.alevel.entity.Author;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.service.AuthorService;

import java.util.ArrayList;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao = ObjectFactory.getInstance().getService(AuthorDao.class);

    public void create(Author author) {
        if (authorValidator(author)) {
            throw new WrongInputException("Please try again. Name can't be empty.");
        }
        authorDao.create(author);
    }

    public void update(Author author) {
        if (authorValidator(author)) {
            throw new WrongInputException("Please try again. Name can't be empty.");
        }
        authorDao.update(author);
    }

    public void delete(Long id) {
        authorDao.delete(id);
    }

    public ArrayList<Author> findAll() {
        return authorDao.findAll();
    }

    public Author findById(Long id) {
        return authorDao.existById(id);
    }

    private boolean authorValidator(Author author) {
        return author.getFirstName().length() <= 0
                || author.getLastName().length() <= 0;
    }
}
