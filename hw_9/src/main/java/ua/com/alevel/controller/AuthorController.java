package ua.com.alevel.controller;

import ua.com.alevel.entity.Author;
import ua.com.alevel.service.AuthorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AuthorController {
    private final AuthorService authorService = new AuthorService();
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
        System.out.println("To add new author enter 1");
        System.out.println("To show all authors enter 2");
        System.out.println("To show author by id enter 3");
        System.out.println("To update author enter 4");
        System.out.println("To delete author enter 5");
        System.out.println("To add the book to the author enter 6");
        System.out.println("To delete a relational book-author pair enter 7");
        System.out.println("To show all books of the author enter 8");
        System.out.println("Exit to main menu please enter 0");
    }

    private void crud(String position) throws IOException {
        switch (position) {
            case "1" -> create();
            case "2" -> readAll();
            case "3" -> readById();
            case "4" -> update();
            case "5" -> delete();
            case "6" -> addBookToAuthor();
            case "7" -> deleteBookAuthor();
            case "8" -> readAllBooksByAuthor();
        }
    }

    private void create() throws IOException {
        authorService.create(authorFieldsFilling());
        System.out.println("The author has been created successfully.");
    }

    private void readAll() {
        ArrayList<Author> authors = authorService.findAll();
        if (authors.size() > 0) {
            System.out.println("Here's your authors list:");
            for (int i = 0; i < authors.size(); i++) {
                System.out.println("#" + (i + 1) + " " + authors.get(i));
            }
        } else {
            System.out.println("Your authors list is empty.");
        }
    }

    private void readById() throws IOException {
        Author authorByID = getAuthorByID();
        System.out.println("Here's your author:");
        System.out.println(authorByID);
    }

    private void update() throws IOException {
        Author authorByID = getAuthorByID();
        System.out.println("And now enter new author's data:");
        Author newAuthor = authorFieldsFilling();
        newAuthor.setId(authorByID.getId());
        authorService.update(newAuthor);
        System.out.println("The author has been updated successfully.");
    }

    private void delete() throws IOException {
        Author authorByID = getAuthorByID();
        authorService.delete(authorByID.getId());
        System.out.println("The author has been deleted successfully.");
    }

    private void addBookToAuthor() throws IOException {
        bookAuthorController.create();
    }

    private void deleteBookAuthor() throws IOException {
        bookAuthorController.delete();
    }

    private void readAllBooksByAuthor() throws IOException {
        Author authorByID = getAuthorByID();
        bookAuthorController.readAllBooksByAuthor(authorByID);
    }

    private Author authorFieldsFilling() throws IOException {
        System.out.println("Please enter first name");
        String firstName = reader.readLine();
        System.out.println("Please enter last name");
        String lastName = reader.readLine();
        return new Author(firstName, lastName);
    }

    private Author getAuthorByID() throws IOException {
        System.out.println("Please enter author ID");
        String idString = reader.readLine();
        return authorService.findById(idString);
    }
}
