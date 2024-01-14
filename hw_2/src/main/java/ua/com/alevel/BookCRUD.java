package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookCRUD {
    private Book[] books = new Book[10];
    private int count = 0;

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        menu();
        String position = "";
        while ((position = reader.readLine()) != null) {
            crud(position, reader);
            menu();
        }
    }

    public void menu() {
        System.out.println();
        System.out.println("If you want to add new book please enter 1");
        System.out.println("If you want to show all books please enter 2");
        System.out.println("If you want to update book please enter 3");
        System.out.println("If you want to delete book please enter 4");
        System.out.println("If you want exit please enter 0");
    }

    public void crud(String position, BufferedReader reader) throws IOException {
        switch (position) {
            case "1" -> create(reader);
            case "2" -> readAll();
            case "3" -> update(reader);
            case "4" -> delete(reader);
            case "0" -> System.exit(0);
        }
    }

    public void create(BufferedReader reader) throws IOException {
        Book book = bookValidation(reader);
        if (book != null) {
            if (books.length >= count) {
                createNewArray();
            }
            books[count++] = book;
            System.out.println("The book has been created successfully.");
        } else {
            System.out.println("Please try again.");
        }
    }

    public Book bookValidation(BufferedReader reader) throws IOException {
        System.out.println("Please enter title");
        String title = reader.readLine();
        System.out.println("Please enter author");
        String author = reader.readLine();
        System.out.println("Please enter year");
        String year = reader.readLine();
        Book book = new Book();
        if (book.setTitle(title) && book.setAuthor(author) && book.setYear(year)) {
            return book;
        }
        return null;
    }

    public void createNewArray() {
        Book[] copyBooks = new Book[books.length * 2];
        for (int i = 0; i < books.length; i++) {
            copyBooks[i] = books[i];
        }
        books = copyBooks;
    }

    public void readAll() {
        if (count > 0) {
            System.out.println("Here's your book list:");
            for (int i = 0; i < books.length; i++) {
                if (books[i] != null) {
                    System.out.println("#" + (i + 1) + " " + books[i].toString());
                }
            }
        } else {
            System.out.println("Your book list is empty.");
        }
    }

    public void update(BufferedReader reader) throws IOException {
        int id = getId(reader) - 1;
        if (id >= 0 && id < books.length && books[id] != null) {
            Book book = bookValidation(reader);
            if (book != null) {
                books[id] = book;
                System.out.println("The book has been updated successfully.");
            } else {
                System.out.println("Please try again.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    public int getId(BufferedReader reader) throws IOException {
        if (count > 0) {
            readAll();
            System.out.println("Please enter id");
            String idString = reader.readLine();
            int id;
            try {
                id = Integer.parseInt(idString);
            } catch (Exception e) {
                System.out.println("Please try again.");
                id = getId(reader);
            }
            return id;
        }
        return 0;
    }

    public void delete(BufferedReader reader) throws IOException {
        int id = getId(reader) - 1;
        if (id >= 0 && id < books.length && books[id] != null) {
            Book[] copyBooks = new Book[books.length - 1];
            for (int i = 0; i < id; i++) {
                copyBooks[i] = books[i];
            }
            for (int i = id; i < books.length - 1; i++) {
                copyBooks[i] = books[i + 1];
            }
            books = copyBooks;
            count--;
            System.out.println("The book has been deleted successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }
}
