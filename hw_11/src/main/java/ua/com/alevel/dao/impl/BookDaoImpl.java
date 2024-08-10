package ua.com.alevel.dao.impl;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.com.alevel.config.HibernateService;
import ua.com.alevel.dao.AuthorDao;
import ua.com.alevel.dao.BookDao;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;

import java.util.*;

public class BookDaoImpl implements BookDao {

    private final HibernateService hibernateService = ObjectFactory.getInstance().getService(HibernateService.class);
    private final AuthorDao authorDao = ObjectFactory.getInstance().getService(AuthorDao.class);

    @Override
    public void create(Book entity) {
        Transaction transaction = null;
        try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void update(Book entity) {
        Book existingBook = existById(entity.getId());
        entity.getAuthors().addAll(existingBook.getAuthors());
        Transaction transaction = null;
        try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(Long id) {
        Book existingBook = existById(id);
        for (Author author : existingBook.getAuthors()) {
            detachBookFromAuthor(id, author.getId());
        }
        Transaction transaction = null;
        try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(existingBook);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public ArrayList<Book> findAll() {
        Transaction transaction = null;
        try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Book");
            List<Book> books = query.getResultList();
            transaction.commit();
            return (ArrayList<Book>) books;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<Book> findById(Long id) {
        Transaction transaction = null;
        try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            Book book = session.find(Book.class, id);
            transaction.commit();
            return Optional.ofNullable(book);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.empty();
    }

    @Override
    public void attachBookToAuthor(Long bookId, Long authorId) {
        Book book = existById(bookId);
        Author author = authorDao.existById(authorId);
        if (!author.getBooks().contains(book) && !book.getAuthors().contains(author)) {
            author.getBooks().add(book);
            authorDao.update(author);
            book.getAuthors().add(author);
            update(book);
        } else {
            throw new WrongInputException("This book is already attached to the author.");
        }
    }

    @Override
    public void detachBookFromAuthor(Long bookId, Long authorId) {
        Book book = existById(bookId);
        Author author = authorDao.existById(authorId);
        if (author.getBooks().contains(book) && book.getAuthors().contains(author)) {
            author.getBooks().remove(book);
            book.getAuthors().remove(author);
            Transaction transaction = null;
            try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
                transaction = session.beginTransaction();
                session.update(author);
                session.update(book);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } else {
            throw new WrongInputException("Relationship was not found.");
        }
    }

    @Override
    public ArrayList<Book> findAllBooksByAuthor(Long authorId) {
        Set<Book> books = authorDao.existById(authorId).getBooks();
        return new ArrayList<>(books);
    }

    @Override
    public ArrayList<Author> findAllAuthorsByBook(Long bookId) {
        Set<Author> authors = existById(bookId).getAuthors();
        return new ArrayList<>(authors);
    }

    @Override
    public Book existById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Book was not found."));
    }
}
