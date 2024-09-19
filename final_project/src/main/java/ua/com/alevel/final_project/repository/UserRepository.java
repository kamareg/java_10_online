package ua.com.alevel.final_project.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.final_project.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByEmail(String email);
}
