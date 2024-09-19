package ua.com.alevel.final_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.alevel.final_project.dto.UserDto;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::new).toList();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
