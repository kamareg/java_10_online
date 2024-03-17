package ua.com.alevel.db.impl;

import com.google.gson.Gson;
import ua.com.alevel.db.BookAuthorDb;
import ua.com.alevel.entity.BookAuthor;
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.util.GeneratorId;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class JsonBookAuthorDbImpl implements BookAuthorDb {
    private final ArrayList<BookAuthor> bookAuthors;
    private final File file;

    public JsonBookAuthorDbImpl() {
        bookAuthors = new ArrayList<>();
        file = new File(ObjectFactory.getInstance().getFile(BookAuthor.class));
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
    public void create(BookAuthor bookAuthor) {
        readJson();
        bookAuthor.setId(GeneratorId.generateId());
        bookAuthors.add(bookAuthor);
        writeJson();
    }

    @Override
    public void update(BookAuthor bookAuthor) {
        //placeholder
    }

    @Override
    public void delete(String id) {
        readJson();
        bookAuthors.removeIf(bookAuthor -> bookAuthor.getId().equals(id)
                || bookAuthor.getAuthorId().equals(id) || bookAuthor.getBookId().equals(id));
        writeJson();
    }

    @Override
    public Optional<BookAuthor> findById(String id) {
        //placeholder
        return Optional.empty();
    }

    @Override
    public ArrayList<BookAuthor> findAll() {
        readJson();
        return bookAuthors;
    }

    private ArrayList<String> idSaving() {
        ArrayList<String> ids = new ArrayList<>();
        readJson();
        bookAuthors.forEach(bookAuthor -> ids.add(bookAuthor.getId()));
        return ids;
    }

    private void readJson() {
        Gson gson = new Gson();
        try {
            BookAuthor[] fromJson = gson.fromJson(new FileReader(file), BookAuthor[].class);
            if (fromJson != null) {
                this.bookAuthors.clear();
                this.bookAuthors.addAll(Arrays.asList(fromJson));
            }
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    private void writeJson() {
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(file)) {
            String json = gson.toJson(this.bookAuthors);
            fileWriter.write(json);
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
        }
    }
}
