package ua.com.alevel.final_project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.exception.EntityNotFoundException;
import ua.com.alevel.final_project.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));
    }
}
