package ua.com.alevel.db.book;

import ua.com.alevel.entity.Book;

import java.util.UUID;

public class ArrayBasedBookStorage implements BookStorage {
    private Book[] books;
    private int count;

    public ArrayBasedBookStorage() {
        this.books = new Book[10];
        this.count = 0;
    }

    @Override
    public void create(Book book) {
        if (books.length <= count) {
            createNewArray();
        }
        book.setId(generateID());
        books[count++] = book;
    }

    @Override
    public void update(Book book) {
        books[findIndex(book.getId())] = book;
    }

    @Override
    public void delete(String id) {
        int index = findIndex(id);
        Book[] copyBooks = new Book[books.length - 1];
        System.arraycopy(books, 0, copyBooks, 0, index);
        System.arraycopy(books, index + 1, copyBooks, index, books.length - 1 - index);
        books = copyBooks;
        count--;
    }

    @Override
    public Book findById(String id) {
        int index = findIndex(id);
        if (index != -1) {
            return books[index];
        }
        return null;
    }

    @Override
    public Book[] findAll() {
        Book[] booksArray = new Book[count];
        System.arraycopy(books, 0, booksArray, 0, count);
        return booksArray;
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

    private void createNewArray() {
        Book[] copyBooks = new Book[books.length * 2];
        System.arraycopy(books, 0, copyBooks, 0, books.length);
        books = copyBooks;
    }

    private int findIndex(String id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && (books[i].getId().equals(id))) {
                return i;
            }
        }
        return -1;
    }
}
