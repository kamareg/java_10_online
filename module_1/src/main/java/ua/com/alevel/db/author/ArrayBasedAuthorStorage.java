package ua.com.alevel.db.author;

import ua.com.alevel.entity.Author;

import java.util.UUID;

public class ArrayBasedAuthorStorage implements AuthorStorage {
    private Author[] authors;
    private int count;

    public ArrayBasedAuthorStorage() {
        this.authors = new Author[10];
        this.count = 0;
    }

    @Override
    public void create(Author author) {
        if (authors.length <= count) {
            createNewArray();
        }
        author.setId(generateID());
        authors[count++] = author;
    }

    @Override
    public void update(Author author) {
        authors[findIndex(author.getId())] = author;
    }

    @Override
    public void delete(String id) {
        int index = findIndex(id);
        Author[] copyAuthors = new Author[authors.length - 1];
        System.arraycopy(authors, 0, copyAuthors, 0, index);
        System.arraycopy(authors, index + 1, copyAuthors, index, authors.length - 1 - index);
        authors = copyAuthors;
        count--;
    }

    @Override
    public Author findById(String id) {
        int index = findIndex(id);
        if (index != -1) {
            return authors[index];
        }
        return null;
    }

    @Override
    public Author[] findAll() {
        Author[] authorsArray = new Author[count];
        System.arraycopy(authors, 0, authorsArray, 0, count);
        return authorsArray;
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

    private void createNewArray() {
        Author[] copyAuthors = new Author[authors.length * 2];
        System.arraycopy(authors, 0, copyAuthors, 0, authors.length);
        authors = copyAuthors;
    }

    private int findIndex(String id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] != null && (authors[i].getId().equals(id))) {
                return i;
            }
        }
        return -1;
    }
}
