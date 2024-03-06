package ua.com.alevel.entity;

public class Book extends BaseEntity {
    private String title;
    private Integer year;

    public Book() {
    }

    public Book(String title, Integer year) {
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book {" + "id: " + getId() +
                ", title: '" + title + '\'' +
                ", year: " + year +
                '}';
    }
}
