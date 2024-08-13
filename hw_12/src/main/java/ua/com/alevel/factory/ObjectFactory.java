package ua.com.alevel.factory;

import ua.com.alevel.config.impl.HibernateJpaConfig;
import ua.com.alevel.config.JpaConfig;
import ua.com.alevel.dao.AuthorDao;
import ua.com.alevel.dao.BookDao;
import ua.com.alevel.dao.impl.AuthorDaoImpl;
import ua.com.alevel.dao.impl.BookDaoImpl;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.service.BookService;
import ua.com.alevel.service.impl.AuthorServiceImpl;
import ua.com.alevel.service.impl.BookServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {

    private static final ObjectFactory instance = new ObjectFactory();
    private final Map<Class<?>, Object> objectMap = new HashMap<>();

    private ObjectFactory() {
    }

    public static ObjectFactory getInstance() {
        return instance;
    }

    public <S> S getService(Class<S> interfaceService) {
        return (S) objectMap.get(interfaceService);
    }

    public void initObjectFactory() {
        objectMap.put(JpaConfig.class, new HibernateJpaConfig());
        objectMap.put(AuthorDao.class, new AuthorDaoImpl());
        objectMap.put(BookDao.class, new BookDaoImpl());
        objectMap.put(AuthorService.class, new AuthorServiceImpl());
        objectMap.put(BookService.class, new BookServiceImpl());
    }
}
