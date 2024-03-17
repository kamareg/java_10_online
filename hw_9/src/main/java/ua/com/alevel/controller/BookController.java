package ua.com.alevel.controller;

import ua.com.alevel.entity.Book;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BookController {
    private final BookService bookService = new BookService();
    private final BookAuthorController bookAuthorController = BookAuthorController.getInstance();
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
        System.out.println("What do you want to do?");
        System.out.println("To add new book enter 1");
        System.out.println("To show all books enter 2");
        System.out.println("To show book by id enter 3");
        System.out.println("To update book enter 4");
        System.out.println("To delete book enter 5");
        System.out.println("To add the author to the book enter 6");
        System.out.println("To delete a relational book-author pair enter 7");
        System.out.println("To show all authors of the book enter 8");
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
        }
    }

    private void create() throws IOException {
        bookService.create(bookFieldsFilling());
        System.out.println("The book has been created successfully.");
    }

    private void readAll() {
        ArrayList<Book> books = bookService.findAll();
        if (books.size() > 0) {
            System.out.println("Here's your books list:");
            for (int i = 0; i < books.size(); i++) {
                System.out.println("#" + (i + 1) + " " + books.get(i));
            }
        } else {
            System.out.println("Your books list is empty.");
        }
    }

    private void readById() throws IOException {
        Book bookByID = getBookByID();
        System.out.println("Here's your book:");
        System.out.println(bookByID);
    }

    private void update() throws IOException {
        Book bookByID = getBookByID();
        System.out.println("And now enter new book's data:");
        Book newBook = bookFieldsFilling();
        newBook.setId(bookByID.getId());
        bookService.update(newBook);
        System.out.println("The book has been updated successfully.");
    }

    private void delete() throws IOException {
        Book bookByID = getBookByID();
        bookService.delete(bookByID.getId());
        System.out.println("The book has been deleted successfully.");
    }

    private void addAuthorToBook() throws IOException {
        bookAuthorController.create();
    }

    private void deleteBookAuthor() throws IOException {
        bookAuthorController.delete();
    }

    private void readAllAuthorsByBook() throws IOException {
        Book bookByID = getBookByID();
        bookAuthorController.readAllAuthorsByBook(bookByID);
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

    private Book getBookByID() throws IOException {
        System.out.println("Please enter book id");
        String idString = reader.readLine();
        return bookService.findById(idString);
    }
}
