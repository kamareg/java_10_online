package ua.com.alevel.final_project.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.final_project.dto.request.AuthRequest;
import ua.com.alevel.final_project.dto.request.RegRequest;
import ua.com.alevel.final_project.dto.response.AuthResponse;
import ua.com.alevel.final_project.dto.response.ResponseContainer;
import ua.com.alevel.final_project.service.AuthenticationService;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseContainer<AuthResponse>> register(
            @Valid
            @RequestBody
            RegRequest regRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContainer<>(authenticationService.register(regRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseContainer<AuthResponse>> login(
            @RequestBody
            AuthRequest authRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseContainer<>(authenticationService.authenticate(authRequest)));
    }
}
