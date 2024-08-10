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
import ua.com.alevel.factory.ObjectFactory;

import java.util.*;

public class AuthorDaoImpl implements AuthorDao {

    private final HibernateService hibernateService = ObjectFactory.getInstance().getService(HibernateService.class);
    private BookDao bookDao;

    @Override
    public void create(Author entity) {
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
    public void update(Author entity) {
        Author existingAuthor = existById(entity.getId());
        entity.getBooks().addAll(existingAuthor.getBooks());
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
        Author existingAuthor = existById(id);
        for (Book book : existingAuthor.getBooks()) {
            getBookDao().detachBookFromAuthor(book.getId(), id);
        }
        Transaction transaction = null;
        try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(existingAuthor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public ArrayList<Author> findAll() {
        Transaction transaction = null;
        try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Author");
            List<Author> authors = query.getResultList();
            transaction.commit();
            return (ArrayList<Author>) authors;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<Author> findById(Long id) {
        Transaction transaction = null;
        try (Session session = hibernateService.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            Author author = session.find(Author.class, id);
            transaction.commit();
            return Optional.ofNullable(author);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.empty();
    }

    @Override
    public Author existById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Author was not found."));
    }

    private BookDao getBookDao() {
        if (bookDao == null) {
            bookDao = ObjectFactory.getInstance().getService(BookDao.class);
        }
        return bookDao;
    }
}
