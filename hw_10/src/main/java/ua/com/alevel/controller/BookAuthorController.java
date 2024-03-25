package ua.com.alevel.controller;

import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookAuthor;
import ua.com.alevel.service.BookAuthorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BookAuthorController {
    private static BookAuthorController instance;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final BookAuthorService bookAuthorService = new BookAuthorService();

    public static BookAuthorController getInstance() {
        if (instance == null) {
            instance = new BookAuthorController();
        }
        return instance;
    }

    public void create() throws IOException {
        bookAuthorService.create(bookAuthorFieldsFilling());
        System.out.println("The relational pair was created successfully.");
    }

    public void delete() throws IOException {
        bookAuthorService.delete(bookAuthorFieldsFilling());
        System.out.println("The relational pair was deleted successfully.");
    }

    public void readAllBooksByAuthor(Author authorByID) {
        ArrayList<Book> books = bookAuthorService.findAllBooksByID(authorByID.getId());
        if (books.size() > 0) {
            System.out.println("Here's list of books:");
            for (int i = 0; i < books.size(); i++) {
                System.out.println("#" + (i + 1) + " " + books.get(i));
            }
        } else {
            System.out.println("No matches found.");
        }
    }

    public void readAllAuthorsByBook(Book bookByID) {
        ArrayList<Author> authors = bookAuthorService.findAllAuthorsByBook(bookByID.getId());
        if (authors.size() > 0) {
            System.out.println("Here's list of authors:");
            for (int i = 0; i < authors.size(); i++) {
                System.out.println("#" + (i + 1) + " " + authors.get(i));
            }
        } else {
            System.out.println("No matches found.");
        }
    }

    private BookAuthor bookAuthorFieldsFilling() throws IOException {
        System.out.println("Please enter book ID");
        String bookID = reader.readLine();
        System.out.println("Please enter author ID");
        String authorID = reader.readLine();
        return new BookAuthor(bookID, authorID);
    }
}
