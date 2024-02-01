package ua.com.alevel.controller;

import ua.com.alevel.entity.Author;
import ua.com.alevel.service.AuthorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthorController {
    private final AuthorService authorService = new AuthorService();

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
        System.out.println("If you want to add new author please enter 1");
        System.out.println("If you want to show all authors please enter 2");
        System.out.println("If you want to show author by id please enter 3");
        System.out.println("If you want to update author please enter 4");
        System.out.println("If you want to delete author please enter 5");
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
        if (authorService.create(authorFieldsFilling(reader))) {
            System.out.println("The author has been created successfully.");
        } else {
            System.out.println("Please try again.");
        }
    }

    public void readAll() {
        Author[] authors = authorService.findAll();
        if (authors.length > 0) {
            System.out.println("Here's your authors list:");
            for (int i = 0; i < authors.length; i++) {
                System.out.println("#" + (i + 1) + " " + authors[i].toString());
            }
        } else {
            System.out.println("Your authors list is empty.");
        }
    }

    public void readById(BufferedReader reader) throws IOException {
        Author authorByID = getAuthorByID(reader);
        if (authorByID != null) {
            System.out.println("Here's your author:");
            System.out.println(authorByID);
        } else {
            System.out.println("Author was not found.");
        }
    }

    public void update(BufferedReader reader) throws IOException {
        Author authorByID = getAuthorByID(reader);
        if (authorByID != null) {
            System.out.println("And now enter new author's data:");
            Author newAuthor = authorFieldsFilling(reader);
            newAuthor.setId(authorByID.getId());
            if (authorService.update(newAuthor)) {
                System.out.println("The author has been updated successfully.");
                return;
            }
        }
        System.out.println("Please try again.");
    }

    public void delete(BufferedReader reader) throws IOException {
        Author authorByID = getAuthorByID(reader);
        if (authorByID != null) {
            authorService.delete(authorByID.getId());
            System.out.println("The author has been deleted successfully.");
        } else {
            System.out.println("Please try again.");
        }
    }

    private Author getAuthorByID(BufferedReader reader) throws IOException {
        System.out.println("Please enter id");
        String idString = reader.readLine();
        return authorService.findById(idString);
    }

    private Author authorFieldsFilling(BufferedReader reader) throws IOException {
        System.out.println("Please enter first name");
        String firstName = reader.readLine();
        System.out.println("Please enter last name");
        String lastName = reader.readLine();
        return new Author(firstName, lastName);
    }
}
