package ua.com.alevel.db.impl;

import com.google.gson.Gson;
import ua.com.alevel.db.BookDb;
import ua.com.alevel.entity.Book;
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.util.GeneratorId;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class JsonBookDbImpl implements BookDb {
    private final ArrayList<Book> books;
    private final File file;

    public JsonBookDbImpl() {
        books = new ArrayList<>();
        file = new File(ObjectFactory.getInstance().getFile(Book.class));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        GeneratorId.idSaving(idSaving());
    }

    @Override
    public void create(Book book) {
        readJson();
        book.setId(GeneratorId.generateId());
        books.add(book);
        writeJson();
    }

    @Override
    public void update(Book book) {
        readJson();
        books.stream()
                .filter(b -> b.getId().equals(book.getId()))
                .findFirst()
                .ifPresent(b -> books.set(books.indexOf(b), book));
        writeJson();
    }

    @Override
    public void delete(String id) {
        readJson();
        books.removeIf(book -> book.getId().equals(id));
        writeJson();
    }

    @Override
    public Optional<Book> findById(String id) {
        readJson();
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    @Override
    public ArrayList<Book> findAll() {
        readJson();
        return books;
    }

    private ArrayList<String> idSaving() {
        ArrayList<String> ids = new ArrayList<>();
        readJson();
        books.forEach(book -> ids.add(book.getId()));
        return ids;
    }

    private void readJson() {
        Gson gson = new Gson();
        try {
            Book[] booksFromJson = gson.fromJson(new FileReader(file), Book[].class);
            if (booksFromJson != null) {
                this.books.clear();
                this.books.addAll(Arrays.asList(booksFromJson));
            }
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    private void writeJson() {
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(file)) {
            String json = gson.toJson(this.books);
            fileWriter.write(json);
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
        }
    }
}
