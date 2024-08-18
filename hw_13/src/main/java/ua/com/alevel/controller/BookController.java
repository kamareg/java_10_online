package ua.com.alevel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.alevel.dto.DataTableRequest;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.service.BookService;
import ua.com.alevel.type.OrderType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookController {

    @Autowired
    private BookService bookService;
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
        System.out.println("To show all books by page and sort them enter 10");
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
            case "10" -> findSortAndLimit();
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
        List<Book> books = bookService.findAll();
        if (!books.isEmpty()) {
            printList(books);
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

    private void findSortAndLimit() throws IOException {
        List<Book> books = bookService.findAll();
        if (books.isEmpty()) {
            System.out.println("Your books list is empty.");
            return;
        }
        DataTableRequest request = new DataTableRequest();
        System.out.println("If you want to sort by title please enter 1");
        System.out.println("If you want to sort by year please enter 2");
        switch (sortValidation()) {
            case 1 -> request.setColumn("title");
            case 2 -> request.setColumn("year");
        }
        System.out.println("If you want order sorting by asc please enter 1");
        System.out.println("If you want order sorting by desc please enter 2");
        switch (sortValidation()) {
            case 1 -> request.setOrderType(OrderType.ASC);
            case 2 -> request.setOrderType(OrderType.DESC);
        }
        System.out.println("Please enter reviewed page size.");
        String number = reader.readLine();
        numberValidation(number);
        int size = Integer.parseInt(number);
        if (size == 0) {
            throw new WrongInputException("Please enter value from 1.");
        }
        request.setSize(size);
        System.out.println("Please enter reviewed page number");
        number = reader.readLine();
        numberValidation(number);
        int page = Integer.parseInt(number);
        if (page == 0) {
            throw new WrongInputException("Please enter value from 1.");
        }
        request.setPage(page);
        printList(bookService.findSortAndLimit(request));
    }

    private int sortValidation() throws IOException {
        String number = reader.readLine();
        numberValidation(number);
        int parsNumber = Integer.parseInt(number);
        if (!(parsNumber == 1 || parsNumber == 2)) {
            throw new WrongInputException("You must enter 1 or 2.");
        }
        return parsNumber;
    }

    private void printList(List<Book> books) {
        System.out.println("Here's your books list:");
        Iterator<Book> iterator = books.iterator();
        int index = 1;
        while (iterator.hasNext()) {
            System.out.println("#" + index + " " + iterator.next());
            index++;
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

    private void numberValidation(String number) {
        if (!number.matches("\\d+")) {
            throw new WrongInputException("Please try again. It must be a number.");
        }
    }

    private Long idValidation(String id) {
        numberValidation(id);
        return Long.parseLong(id);
    }
}
