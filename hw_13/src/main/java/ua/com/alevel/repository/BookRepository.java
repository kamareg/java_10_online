package ua.com.alevel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
