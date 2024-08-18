package ua.com.alevel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    private String title;

    private Integer year;

    @ManyToMany(mappedBy = "books")
    @ToString.Exclude
    private Set<Author> authors;

    public Book() {
        this.authors = new HashSet<>();
    }

    public Book(String title, Integer year) {
        this.title = title;
        this.year = year;
        this.authors = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(year, book.year) && Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year);
    }
}
