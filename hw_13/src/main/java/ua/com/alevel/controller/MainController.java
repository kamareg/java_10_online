package ua.com.alevel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class MainController {
    @Autowired
    private BookController bookController;
    @Autowired
    private AuthorController authorController;

    public void start() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String position;
            do {
                menu();
                position = reader.readLine();
                response(position);
            } while (!position.equals("0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void menu() {
        System.out.println();
        System.out.println("If you want to perform transactions with books please enter 1");
        System.out.println("If you want to perform transactions with authors please enter 2");
        System.out.println("If you want to exit please enter 0");
    }

    public void response(String position) throws IOException {
        switch (position) {
            case "1" -> bookController.start();
            case "2" -> authorController.start();
        }
    }
}
