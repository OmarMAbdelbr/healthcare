package com.kfh.healthcare.security;

import com.kfh.healthcare.dto.authentication.LoginRequestDTO;
import com.kfh.healthcare.dto.authentication.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        return new LoginResponseDTO(jwtUtils.generateToken(loginRequest.username()));
    }

    public void invalidateSession(String token) {
        jwtUtils.blacklistToken(token);
    }
}
