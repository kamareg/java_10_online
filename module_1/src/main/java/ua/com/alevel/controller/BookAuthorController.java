package ua.com.alevel.controller;

import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookAuthor;
import ua.com.alevel.service.BookAuthorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookAuthorController {
    private final BookAuthorService bookAuthorService = new BookAuthorService();

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position;
        do {
            menu();
            position = reader.readLine();
            crud(position, reader);
        } while (!position.equals("0"));
    }

    public void menu() {
        System.out.println();
        System.out.println("If you want to add the author to the book please enter 1");
        System.out.println("If you want to show all authors of the book please enter 2");
        System.out.println("If you want to show all books of the author please enter 3");
        System.out.println("If you want to delete a relational pair enter 4");
        System.out.println("If you want exit to main menu please enter 0");
    }

    public void crud(String position, BufferedReader reader) throws IOException {
        switch (position) {
            case "1" -> create(reader);
            case "2" -> readAllAuthorsByBook(reader);
            case "3" -> readAllBooksByAuthor(reader);
            case "4" -> delete(reader);
        }
    }

    public void create(BufferedReader reader) throws IOException {
        if (bookAuthorService.create(bookAuthorFieldsFilling(reader))) {
            System.out.println("The relational pair was created successfully.");
        } else {
            System.out.println("Please try again.");
        }
    }

    public void readAllAuthorsByBook(BufferedReader reader) throws IOException {
        System.out.println("Please enter book ID");
        String bookID = reader.readLine();
        Author[] authors = bookAuthorService.findAllAuthors(bookID);
        if (authors.length > 0) {
            System.out.println("Here's your list:");
            for (int i = 0; i < authors.length; i++) {
                System.out.println("#" + (i + 1) + " " + authors[i].toString());
            }
        } else {
            System.out.println("No matches found.");
        }
    }

    public void readAllBooksByAuthor(BufferedReader reader) throws IOException {
        System.out.println("Please enter author ID");
        String authorID = reader.readLine();
        Book[] books = bookAuthorService.findAllBooks(authorID);
        if (books.length > 0) {
            System.out.println("Here's your list:");
            for (int i = 0; i < books.length; i++) {
                System.out.println("#" + (i + 1) + " " + books[i].toString());
            }
        } else {
            System.out.println("No matches found.");
        }
    }

    public void delete(BufferedReader reader) throws IOException {
        BookAuthor bookAuthor = bookAuthorFieldsFilling(reader);
        if (bookAuthorService.delete(bookAuthor)) {
            System.out.println("The relational pair was deleted successfully.");
        } else {
            System.out.println("No matches found.");
        }
    }

    private BookAuthor bookAuthorFieldsFilling(BufferedReader reader) throws IOException {
        System.out.println("Please enter book ID");
        String bookID = reader.readLine();
        System.out.println("Please enter author ID");
        String authorID = reader.readLine();
        return new BookAuthor(bookID, authorID);
    }
}
