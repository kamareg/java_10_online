package ua.com.alevel.controller;

import ua.com.alevel.entity.Book;
import ua.com.alevel.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookController {
    private final BookService bookService = new BookService();

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
        System.out.println("If you want to add new book please enter 1");
        System.out.println("If you want to show all books please enter 2");
        System.out.println("If you want to show book by id please enter 3");
        System.out.println("If you want to update book please enter 4");
        System.out.println("If you want to delete book please enter 5");
        System.out.println("If you want exit to main menu please enter 0");
    }

    public void crud(String position, BufferedReader reader) throws IOException {
        switch (position) {
            case "1" -> create(reader);
            case "2" -> readAll();
            case "3" -> readById(reader);
            case "4" -> update(reader);
            case "5" -> delete(reader);
        }
    }

    public void create(BufferedReader reader) throws IOException {
        if (bookService.create(bookFieldsFilling(reader))) {
            System.out.println("The book has been created successfully.");
        } else {
            System.out.println("Please try again.");
        }
    }

    public void readAll() {
        Book[] books = bookService.findAll();
        if (books.length > 0) {
            System.out.println("Here's your books list:");
            for (int i = 0; i < books.length; i++) {
                System.out.println("#" + (i + 1) + " " + books[i].toString());
            }
        } else {
            System.out.println("Your books list is empty.");
        }
    }

    public void readById(BufferedReader reader) throws IOException {
        Book bookByID = getBookByID(reader);
        if (bookByID != null) {
            System.out.println("Here's your book:");
            System.out.println(bookByID);
        } else {
            System.out.println("Book was not found.");
        }
    }

    public void update(BufferedReader reader) throws IOException {
        Book bookByID = getBookByID(reader);
        if (bookByID != null) {
            System.out.println("And now enter new book's data:");
            Book newBook = bookFieldsFilling(reader);
            newBook.setId(bookByID.getId());
            if (bookService.update(newBook)) {
                System.out.println("The book has been updated successfully.");
                return;
            }
        }
        System.out.println("Please try again.");
    }

    public void delete(BufferedReader reader) throws IOException {
        Book bookByID = getBookByID(reader);
        if (bookByID != null) {
            bookService.delete(bookByID.getId());
            System.out.println("The book has been deleted successfully.");
        } else {
            System.out.println("Please try again.");
        }
    }

    private Book getBookByID(BufferedReader reader) throws IOException {
        System.out.println("Please enter id");
        String idString = reader.readLine();
        return bookService.findById(idString);
    }

    private Book bookFieldsFilling(BufferedReader reader) throws IOException {
        System.out.println("Please enter title");
        String title = reader.readLine();
        System.out.println("Please enter year");
        Integer year = yearValidation(reader.readLine());
        return new Book(title, year);
    }

    private Integer yearValidation(String year) {
        if (year.matches("\\d+")) {
            return Integer.parseInt(year);
        }
        return null;
    }
}
