package ua.com.alevel.controller;

import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BookController {
    private final BookService bookService = ObjectFactory.getInstance().getService(BookService.class);
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void start() throws IOException {
        String position;
        do {
            menu();
            position = reader.readLine();
            crud(position);
        } while (!position.equals("0"));
    }

    private void menu() {
        System.out.println("\nWhat do you want to do?");
        System.out.println("To add new book enter 1");
        System.out.println("To show all books enter 2");
        System.out.println("To show book by id enter 3");
        System.out.println("To update book enter 4");
        System.out.println("To delete book enter 5");
        System.out.println("To attach author to the book enter 6");
        System.out.println("To detach author from the book enter 7");
        System.out.println("To show all authors of the book enter 8");
        System.out.println("To show all books of the author enter 9");
        System.out.println("Exit to main menu please enter 0");
    }

    private void crud(String position) throws IOException {
        switch (position) {
            case "1" -> create();
            case "2" -> readAll();
            case "3" -> readById();
            case "4" -> update();
            case "5" -> delete();
            case "6" -> addAuthorToBook();
            case "7" -> deleteBookAuthor();
            case "8" -> readAllAuthorsByBook();
            case "9" -> readAllBooksByAuthor();
        }
    }

    private void create() throws IOException {
        bookService.create(bookFieldsFilling());
        System.out.println("The book has been created successfully.");
    }

    private void update() throws IOException {
        Long id = getBookID();
        Book newBook = bookFieldsFilling();
        newBook.setId(id);
        bookService.update(newBook);
        System.out.println("The book has been updated successfully.");
    }

    private void delete() throws IOException {
        Long id = getBookID();
        bookService.delete(id);
        System.out.println("The book has been deleted successfully.");
    }

    private void readAll() {
        ArrayList<Book> books = bookService.findAll();
        if (!books.isEmpty()) {
            System.out.println("Here's your books list:");
            for (int i = 0; i < books.size(); i++) {
                System.out.println("#" + (i + 1) + " " + books.get(i));
            }
        } else {
            System.out.println("Your books list is empty.");
        }
    }

    private void readById() throws IOException {
        Long id = getBookID();
        Book bookByID = bookService.findById(id);
        System.out.println("Here's your book:");
        System.out.println(bookByID);
    }

    private void addAuthorToBook() throws IOException {
        Long bookID = getBookID();
        Long authorID = getAuthorID();
        bookService.attachBookToAuthor(bookID, authorID);
        System.out.println("The relational pair was created successfully.");
    }

    private void deleteBookAuthor() throws IOException {
        Long bookID = getBookID();
        Long authorID = getAuthorID();
        bookService.detachBookFromAuthor(bookID, authorID);
        System.out.println("The relational pair was deleted successfully.");
    }

    private void readAllAuthorsByBook() throws IOException {
        Long id = getBookID();
        ArrayList<Author> authors = bookService.findAllAuthorsByBook(id);
        if (!authors.isEmpty()) {
            System.out.println("Here's your authors list:");
            for (int i = 0; i < authors.size(); i++) {
                System.out.println("#" + (i + 1) + " " + authors.get(i));
            }
        } else {
            System.out.println("No matches found.");
        }
    }

    private void readAllBooksByAuthor() throws IOException {
        Long id = getAuthorID();
        ArrayList<Book> books = bookService.findAllBooksByAuthor(id);
        if (!books.isEmpty()) {
            System.out.println("Here's your books list:");
            for (int i = 0; i < books.size(); i++) {
                System.out.println("#" + (i + 1) + " " + books.get(i));
            }
        } else {
            System.out.println("No matches found.");
        }
    }

    private Book bookFieldsFilling() throws IOException {
        System.out.println("Please enter title");
        String title = reader.readLine();
        System.out.println("Please enter year");
        Integer year = yearValidation(reader.readLine());
        return new Book(title, year);
    }

    private Integer yearValidation(String year) {
        if (!year.matches("\\d+")) {
            throw new WrongInputException("Please try again. Year must be a number.");
        }
        return Integer.parseInt(year);
    }

    private Long getBookID() throws IOException {
        System.out.println("Please enter book ID");
        String idString = reader.readLine();
        return idValidation(idString);
    }

    private Long getAuthorID() throws IOException {
        System.out.println("Please enter author ID");
        String idString = reader.readLine();
        return idValidation(idString);
    }

    private Long idValidation(String id) {
        if (!id.matches("\\d+")) {
            throw new WrongInputException("Please try again. ID must be a number.");
        }
        return Long.parseLong(id);
    }
}
