package ua.com.alevel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
