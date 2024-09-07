package ua.com.alevel.final_project.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User> {
    User findByEmail(String email);
//    boolean existsByEmail(String email);
//    Optional<User> findByEmail(String email);
}
