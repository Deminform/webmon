package com.webmonitor.webmon.services;

import com.webmonitor.webmon.auth.AuthenticationRequest;
import com.webmonitor.webmon.auth.AuthenticationResponse;
import com.webmonitor.webmon.auth.RegisterRequest;
import com.webmonitor.webmon.user.Role;
import com.webmonitor.webmon.models.User;
import com.webmonitor.webmon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;


  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
    repository.save(user);
    var jwtToken = jwtService.generateToken(user);
//    log.info("Register - A new token has been generated: " + jwtToken); // log ********************************
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
  }


  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
//    log.info("Authenticate - A new token has been generated: " + jwtToken); // log ********************************
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

}
