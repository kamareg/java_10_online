package ua.com.alevel.factory;


import ua.com.alevel.config.JdbcService;
import ua.com.alevel.config.impl.MysqlJdbcService;
import ua.com.alevel.db.AuthorDb;
import ua.com.alevel.db.BookAuthorDb;
import ua.com.alevel.db.BookDb;
import ua.com.alevel.db.impl.mysql.MySqlAuthorDbImpl;
import ua.com.alevel.db.impl.mysql.MySqlBookAuthorDbImpl;
import ua.com.alevel.db.impl.mysql.MySqlBookDbImpl;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookAuthor;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {

    private static final ObjectFactory instance = new ObjectFactory();
    private final Map<Class<?>, Object> objectMap = new HashMap<>();
    private final Map<Class<?>, String> fileMap = new HashMap<>();

    private ObjectFactory() {
    }

    public static ObjectFactory getInstance() {
        return instance;
    }

    public <S> S getService(Class<S> interfaceService) {
        return (S) objectMap.get(interfaceService);
    }

    public <S> String getFile(Class<S> entity) {
        return fileMap.get(entity);
    }

    public void initObjectFactory() {
        fileMap.put(Author.class, "authors.json");
        fileMap.put(Book.class, "books.json");
        fileMap.put(BookAuthor.class, "book_authors.json");

        objectMap.put(JdbcService.class, new MysqlJdbcService());

        objectMap.put(AuthorDb.class, new MySqlAuthorDbImpl());
        objectMap.put(BookDb.class, new MySqlBookDbImpl());
        objectMap.put(BookAuthorDb.class, new MySqlBookAuthorDbImpl());
    }
}
