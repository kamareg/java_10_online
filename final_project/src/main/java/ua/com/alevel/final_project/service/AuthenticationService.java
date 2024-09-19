package ua.com.alevel.final_project.service;


import ua.com.alevel.final_project.dto.request.AuthRequest;
import ua.com.alevel.final_project.dto.request.RegRequest;
import ua.com.alevel.final_project.dto.response.AuthResponse;

public interface AuthenticationService {

    AuthResponse register(RegRequest regRequest);

    AuthResponse authenticate(AuthRequest authRequest);
}
