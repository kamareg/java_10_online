package ua.com.alevel.final_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.alevel.final_project.dto.request.AuthRequest;
import ua.com.alevel.final_project.dto.request.RegRequest;
import ua.com.alevel.final_project.dto.response.AuthResponse;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.RoleUser;
import ua.com.alevel.final_project.exception.UserAlreadyExistsException;
import ua.com.alevel.final_project.service.AuthenticationService;
import ua.com.alevel.final_project.service.JwtService;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse register(RegRequest regRequest) {
        if (userDetailsService.emailExists(regRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(regRequest.getPassword()));
        user.setEmail(regRequest.getEmail());
        if (regRequest.getFirstName() != null) {
            user.setFirstName(regRequest.getFirstName());
        }
        if (regRequest.getLastName() != null) {
            user.setLastName(regRequest.getLastName());
        }
        if (regRequest.getAge() != null) {
            user.setAge(regRequest.getAge());
        }
        user.setRole(RoleUser.ROLE_USER);
        userDetailsService.create(user);
        return getAuthResponse(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        return getAuthResponse(userDetails);
    }

    private AuthResponse getAuthResponse(UserDetails userDetails) {
        final String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
