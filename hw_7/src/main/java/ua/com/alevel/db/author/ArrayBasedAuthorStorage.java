package ua.com.alevel.db.author;

import ua.com.alevel.entity.Author;

import java.util.ArrayList;
import java.util.UUID;

public class ArrayBasedAuthorStorage implements AuthorStorage {
    private final ArrayList<Author> authors;

    public ArrayBasedAuthorStorage() {
        this.authors = new ArrayList<>();
    }

    @Override
    public void create(Author author) {
        author.setId(generateID());
        authors.add(author);
    }

    @Override
    public void update(Author author) {
        authors.set(findIndex(author.getId()), author);
    }

    @Override
    public void delete(String id) {
        authors.remove(findIndex(id));
    }

    @Override
    public Author findById(String id) {
        int index = findIndex(id);
        if (index != -1) {
            return authors.get(index);
        }
        return null;
    }

    @Override
    public Author[] findAll() {
        return authors.toArray(new Author[0]);
    }

    @Override
    public String generateID() {
        String id = UUID.randomUUID().toString();
        for (Author author : authors) {
            if (author != null && author.getId().equals(id)) {
                return generateID();
            }
        }
        return id;
    }

    private int findIndex(String id) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i) != null && (authors.get(i).getId().equals(id))) {
                return i;
            }
        }
        return -1;
    }
}
