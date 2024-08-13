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
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.type.OrderType;

import java.util.ArrayList;
import java.util.Optional;

public class AuthorDaoImpl implements AuthorDao {

    private final JpaConfig jpaConfig = ObjectFactory.getInstance().getService(JpaConfig.class);
    private final EntityManager entityManager = jpaConfig.getEntityManagerFactory().createEntityManager();
    private BookDao bookDao;

    @Override
    public void create(Author entity) {
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
    public void update(Author entity) {
        Author existingAuthor = existById(entity.getId());
        entity.getBooks().addAll(existingAuthor.getBooks());
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
        Author existingAuthor = existById(id);
        for (Book book : existingAuthor.getBooks()) {
            getBookDao().detachBookFromAuthor(book.getId(), id);
        }
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(existingAuthor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public ArrayList<Author> findAll() {
        return (ArrayList<Author>) entityManager.createQuery("select a from Author a").getResultList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public Author existById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Author was not found."));
    }

    @Override
    public ArrayList<Author> findSortAndLimit(DataTableRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> from = criteriaQuery.from(Author.class);
        if (request.getOrderType().equals(OrderType.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getColumn())));
        }
        if (request.getOrderType().equals(OrderType.DESC)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getColumn())));
        }
        int page = (request.getPage() - 1) * request.getSize();
        int size = request.getSize();
        return (ArrayList<Author>) entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }

    private BookDao getBookDao() {
        if (bookDao == null) {
            bookDao = ObjectFactory.getInstance().getService(BookDao.class);
        }
        return bookDao;
    }
}
