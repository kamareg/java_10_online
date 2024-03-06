package ua.com.alevel.db.bookAuthor;

import ua.com.alevel.entity.BookAuthor;

import java.util.ArrayList;

public class BookAuthorRelation {
    private static BookAuthorRelation instance;
    private final ArrayList<BookAuthor> bookAuthors;

    private BookAuthorRelation() {
        this.bookAuthors = new ArrayList<>();
    }

    public static BookAuthorRelation getInstance() {
        if (instance == null) {
            instance = new BookAuthorRelation();
        }
        return instance;
    }

    public void create(BookAuthor bookAuthor) {
        bookAuthors.add(bookAuthor);
    }

    public BookAuthor[] findAll() {
        return bookAuthors.toArray(new BookAuthor[0]);
    }

    public ArrayList<String> findAllAuthorsByBook(String id) {
        ArrayList<String> authors = new ArrayList<>();
        for (BookAuthor bookAuthor : bookAuthors) {
            if (bookAuthor != null && bookAuthor.getBookId().equals(id)) {
                authors.add(bookAuthor.getAuthorId());
            }
        }
        return authors;
    }

    public ArrayList<String> findAllBooksByAuthor(String id) {
        ArrayList<String> books = new ArrayList<>();
        for (BookAuthor bookAuthor : bookAuthors) {
            if (bookAuthor != null && bookAuthor.getAuthorId().equals(id)) {
                books.add(bookAuthor.getBookId());
            }
        }
        return books;
    }

    public void delete(BookAuthor bookAuthor) {
        bookAuthors.remove(findIndex(bookAuthor));
    }

    public void removeDeleted(String id) {
        for (BookAuthor bookAuthor : bookAuthors) {
            if (bookAuthor != null && (bookAuthor.getAuthorId().equals(id) || bookAuthor.getBookId().equals(id))) {
                delete(bookAuthor);
                break;
            }
        }
    }

    private int findIndex(BookAuthor bookAuthor) {
        for (int i = 0; i < bookAuthors.size(); i++) {
            if (bookAuthors.get(i) != null && (bookAuthors.get(i).getBookId().equals(bookAuthor.getBookId()))
                    && bookAuthors.get(i).getAuthorId().equals(bookAuthor.getAuthorId())) {
                return i;
            }
        }
        return -1;
    }
}
