package ua.com.alevel.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ua.com.alevel.config.JpaConfig;
import ua.com.alevel.dao.AuthorDao;
import ua.com.alevel.dao.BookDao;
import ua.com.alevel.dao.DataTableRequest;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.type.OrderType;

import java.util.*;

public class BookDaoImpl implements BookDao {
    private final JpaConfig jpaConfig = ObjectFactory.getInstance().getService(JpaConfig.class);
    private final EntityManager entityManager = jpaConfig.getEntityManagerFactory().createEntityManager();
    private final AuthorDao authorDao = ObjectFactory.getInstance().getService(AuthorDao.class);

    @Override
    public void create(Book entity) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
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
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(entity);
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
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(existingBook);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public ArrayList<Book> findAll() {
        return (ArrayList<Book>) entityManager.createQuery("select b from Book b").getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
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
            EntityTransaction transaction = null;
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.merge(author);
                entityManager.merge(book);
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

    @Override
    public ArrayList<Book> findSortAndLimit(DataTableRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> from = criteriaQuery.from(Book.class);
        if (request.getOrderType().equals(OrderType.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getColumn())));
        }
        if (request.getOrderType().equals(OrderType.DESC)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getColumn())));
        }
        int page = (request.getPage() - 1) * request.getSize();
        int size = request.getSize();
        return (ArrayList<Book>) entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }
}
