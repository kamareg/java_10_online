package ua.com.alevel.db.impl;

import com.google.gson.Gson;
import ua.com.alevel.db.AuthorDb;
import ua.com.alevel.entity.Author;
import ua.com.alevel.factory.ObjectFactory;
import ua.com.alevel.util.GeneratorId;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class JsonAuthorDbImpl implements AuthorDb {
    private final ArrayList<Author> authors;
    private final File file;

    public JsonAuthorDbImpl() {
        authors = new ArrayList<>();
        file = new File(ObjectFactory.getInstance().getFile(Author.class));
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
    public void create(Author author) {
        readJson();
        author.setId(GeneratorId.generateId());
        authors.add(author);
        writeJson();
    }

    @Override
    public void update(Author author) {
        readJson();
        authors.stream()
                .filter(a -> a.getId().equals(author.getId()))
                .findFirst()
                .ifPresent(a -> authors.set(authors.indexOf(a), author));
        writeJson();
    }

    @Override
    public void delete(String id) {
        readJson();
        authors.removeIf(author -> author.getId().equals(id));
        writeJson();
    }

    @Override
    public Optional<Author> findById(String id) {
        readJson();
        return authors.stream().filter(author -> author.getId().equals(id)).findFirst();
    }

    @Override
    public ArrayList<Author> findAll() {
        readJson();
        return authors;
    }

    private ArrayList<String> idSaving() {
        ArrayList<String> ids = new ArrayList<>();
        readJson();
        authors.forEach(author -> ids.add(author.getId()));
        return ids;
    }

    private void readJson() {
        Gson gson = new Gson();
        try {
            Author[] authorsFromJson = gson.fromJson(new FileReader(file), Author[].class);
            if (authorsFromJson != null) {
                this.authors.clear();
                this.authors.addAll(Arrays.asList(authorsFromJson));
            }
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    private void writeJson() {
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(file)) {
            String json = gson.toJson(this.authors);
            fileWriter.write(json);
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
        }
    }
}
