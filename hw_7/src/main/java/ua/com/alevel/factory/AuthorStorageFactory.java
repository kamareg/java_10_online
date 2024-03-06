package ua.com.alevel.factory;

import ua.com.alevel.db.author.ArrayBasedAuthorStorage;
import ua.com.alevel.db.author.AuthorStorage;

public class AuthorStorageFactory {
    private final static AuthorStorage authorStorage = new ArrayBasedAuthorStorage();

    public static AuthorStorage getAuthorStorage() {
        return authorStorage;
    }
}
