package ua.com.alevel.controller;

import ua.com.alevel.dao.DataTableRequest;
import ua.com.alevel.entity.Author;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.type.OrderType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AuthorController {
    private final AuthorService authorService = ObjectFactory.getInstance().getService(AuthorService.class);
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
        System.out.println("To add new author enter 1");
        System.out.println("To show all authors enter 2");
        System.out.println("To show author by id enter 3");
        System.out.println("To update author enter 4");
        System.out.println("To delete author enter 5");
        System.out.println("To show all authors by page and sort them enter 6");
        System.out.println("Exit to main menu please enter 0");
    }

    private void crud(String position) throws IOException {
        switch (position) {
            case "1" -> create();
            case "2" -> readAll();
            case "3" -> readById();
            case "4" -> update();
            case "5" -> delete();
            case "6" -> findSortAndLimit();
        }
    }

    private void create() throws IOException {
        authorService.create(authorFieldsFilling());
        System.out.println("The author has been created successfully.");
    }

    private void update() throws IOException {
        Long id = getAuthorID();
        Author newAuthor = authorFieldsFilling();
        newAuthor.setId(id);
        authorService.update(newAuthor);
        System.out.println("The author has been updated successfully.");
    }

    private void delete() throws IOException {
        Long id = getAuthorID();
        authorService.delete(id);
        System.out.println("The author has been deleted successfully.");
    }

    private void readAll() {
        ArrayList<Author> authors = authorService.findAll();
        if (!authors.isEmpty()) {
            printList(authors);
        } else {
            System.out.println("Your authors list is empty.");
        }
    }

    private void readById() throws IOException {
        Long id = getAuthorID();
        Author authorByID = authorService.findById(id);
        System.out.println("Here's your author:");
        System.out.println(authorByID);
    }

    private void findSortAndLimit() throws IOException {
        ArrayList<Author> authors = authorService.findAll();
        if (authors.isEmpty()) {
            System.out.println("Your authors list is empty.");
            return;
        }
        DataTableRequest request = new DataTableRequest();
        System.out.println("If you want to sort by first name please enter 1");
        System.out.println("If you want to sort by last name please enter 2");
        switch (sortValidation()) {
            case 1 -> request.setColumn("firstName");
            case 2 -> request.setColumn("lastName");
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
        printList(authorService.findSortAndLimit(request));
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

    private void printList(ArrayList<Author> authors) {
        System.out.println("Here's your authors list:");
        for (int i = 0; i < authors.size(); i++) {
            System.out.println("#" + (i + 1) + " " + authors.get(i));
        }
    }

    private Author authorFieldsFilling() throws IOException {
        System.out.println("Please enter first name");
        String firstName = reader.readLine();
        System.out.println("Please enter last name");
        String lastName = reader.readLine();
        return new Author(firstName, lastName);
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
