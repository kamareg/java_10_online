package ua.com.alevel;

import java.time.LocalDateTime;

public class Book {
    private String title;
    private String author;
    private int year;

    public String getTitle() {
        return title;
    }

    public boolean setTitle(String title) {
        if (title.length() > 0) {
            this.title = title;
            return true;
        }
        return false;
    }

    public String getAuthor() {
        return author;
    }

    public boolean setAuthor(String author) {
        if (author.length() > 0 && author.matches("[a-zA-Z ]+")) {
            this.author = author;
            return true;
        }
        return false;
    }

    public int getYear() {
        return year;
    }

    public boolean setYear(String yearString) {
        int year;
        try {
            year = Integer.parseInt(yearString);
        } catch (Exception e) {
            return false;
        }

        if (year >= 0 && year <= LocalDateTime.now().getYear()) {
            this.year = year;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title: '" + title + '\'' +
                ", author: '" + author + '\'' +
                ", year: " + year +
                '}';
    }
}
