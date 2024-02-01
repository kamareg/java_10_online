package ua.com.alevel.db.bookAuthor;

import ua.com.alevel.entity.BookAuthor;

public class BookAuthorRelation {
    private static BookAuthorRelation instance;
    private BookAuthor[] bookAuthors;
    private int count;

    private BookAuthorRelation() {
        this.bookAuthors = new BookAuthor[10];
        this.count = 0;
    }

    public static BookAuthorRelation getInstance() {
        if (instance == null) {
            instance = new BookAuthorRelation();
        }
        return instance;
    }

    public void create(BookAuthor bookAuthor) {
        if (bookAuthors.length <= count) {
            createNewArray();
        }
        bookAuthors[count++] = bookAuthor;
    }

    public BookAuthor[] findAll() {
        BookAuthor[] bookAuthorsArray = new BookAuthor[count];
        System.arraycopy(bookAuthors, 0, bookAuthorsArray, 0, count);
        return bookAuthorsArray;
    }

    public String[] findAllAuthorsByBook(String id) {
        String[] authors = new String[count];
        for (int i = 0; i < bookAuthors.length; i++) {
            if (bookAuthors[i] != null && bookAuthors[i].getBookId().equals(id)) {
                authors[i] = bookAuthors[i].getAuthorId();
            }
        }
        return authors;
    }

    public String[] findAllBooksByAuthor(String id) {
        String[] books = new String[count];
        for (int i = 0; i < bookAuthors.length; i++) {
            if (bookAuthors[i] != null && bookAuthors[i].getAuthorId().equals(id)) {
                books[i] = bookAuthors[i].getBookId();
            }
        }
        return books;
    }

    public void delete(BookAuthor bookAuthor) {
        int index = findIndex(bookAuthor);
        BookAuthor[] bookAuthorsArray = new BookAuthor[bookAuthors.length - 1];
        System.arraycopy(bookAuthors, 0, bookAuthorsArray, 0, index);
        System.arraycopy(bookAuthors, index + 1, bookAuthorsArray, index, bookAuthors.length - 1 - index);
        bookAuthors = bookAuthorsArray;
        count--;
    }

    public void removeDeleted(String id) {
        for (BookAuthor bookAuthor : bookAuthors) {
            if (bookAuthor != null && (bookAuthor.getAuthorId().equals(id) || bookAuthor.getBookId().equals(id))) {
                delete(bookAuthor);
            }
        }
    }

    private void createNewArray() {
        BookAuthor[] copyBookAuthors = new BookAuthor[bookAuthors.length * 2];
        System.arraycopy(bookAuthors, 0, copyBookAuthors, 0, bookAuthors.length);
        bookAuthors = copyBookAuthors;
    }

    private int findIndex(BookAuthor bookAuthor) {
        for (int i = 0; i < bookAuthors.length; i++) {
            if (bookAuthors[i] != null && (bookAuthors[i].getBookId().equals(bookAuthor.getBookId()))
                    && bookAuthors[i].getAuthorId().equals(bookAuthor.getAuthorId())) {
                return i;
            }
        }
        return -1;
    }
}
