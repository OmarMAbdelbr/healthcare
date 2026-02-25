package com.kfh.healthcare.controller;

import com.kfh.healthcare.dto.authentication.LoginRequestDTO;
import com.kfh.healthcare.dto.authentication.LoginResponseDTO;
import com.kfh.healthcare.security.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/${app.api.version}/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {

        LoginResponseDTO responseDTO = authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(responseDTO);

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("No active session found.");
        }
        authenticationService.invalidateSession(authHeader.substring(7));
        return ResponseEntity.ok("Logged out successfully. Session invalidated.");
    }
}
