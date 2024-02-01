package ua.com.alevel.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainController {
    private static final BookController bookController = new BookController();
    private static final AuthorController authorController = new AuthorController();
    private static final BookAuthorController bookAuthorController = new BookAuthorController();

    public static void menu() {
        System.out.println();
        System.out.println("If you want to perform transactions with books please enter 1");
        System.out.println("If you want to perform transactions with authors please enter 2");
        System.out.println("If you want to manipulate the book-author relation please enter 3");
        System.out.println("If you want to exit please enter 0");
    }

    public static void response(String position) throws IOException {
        switch (position) {
            case "1" -> bookController.start();
            case "2" -> authorController.start();
            case "3" -> bookAuthorController.start();
        }
    }

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome!");
        String position;
        do {
            menu();
            position = reader.readLine();
            response(position);
        } while (!position.equals("0"));
    }
}
