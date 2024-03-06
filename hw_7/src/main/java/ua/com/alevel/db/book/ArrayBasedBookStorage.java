package ua.com.alevel.db.book;

import ua.com.alevel.entity.Book;

import java.util.ArrayList;
import java.util.UUID;

public class ArrayBasedBookStorage implements BookStorage {
    private final ArrayList<Book> books;

    public ArrayBasedBookStorage() {
        this.books = new ArrayList<>();
    }

    @Override
    public void create(Book book) {
        book.setId(generateID());
        books.add(book);
    }

    @Override
    public void update(Book book) {
        books.set(findIndex(book.getId()), book);
    }

    @Override
    public void delete(String id) {
        books.remove(findIndex(id));
    }

    @Override
    public Book findById(String id) {
        int index = findIndex(id);
        if (index != -1) {
            return books.get(index);
        }
        return null;
    }

    @Override
    public Book[] findAll() {
        return books.toArray(new Book[0]);
    }

    @Override
    public String generateID() {
        String id = UUID.randomUUID().toString();
        for (Book book : books) {
            if (book != null && book.getId().equals(id)) {
                return generateID();
            }
        }
        return id;
    }

    private int findIndex(String id) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i) != null && (books.get(i).getId().equals(id))) {
                return i;
            }
        }
        return -1;
    }
}
