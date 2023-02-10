package com.webmonitor.webmon.controllers;

import com.webmonitor.webmon.auth.AuthenticationRequest;
import com.webmonitor.webmon.auth.AuthenticationResponse;
import com.webmonitor.webmon.services.AuthenticationService;
import com.webmonitor.webmon.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    @GetMapping
    public String signupPage() {
        return "/auth";
    }

//    @PostMapping("/signup")
//    public String register(RegisterRequest request) {
//        ResponseEntity.ok(service.register(request));
//        return "redirect:/auth";
//    }
//
//    @PostMapping("/signing")
//    public String authenticate(AuthenticationRequest request) {
//        ResponseEntity.ok(service.authenticate(request));
//        return "redirect:/auth";
//    }

  @PostMapping("/signup")
  public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
    System.out.println("* * * * * * * * * * * * Response contain the data: " + request.getEmail() + " " + request.getFirstname() + " " + request.getLastname());
    return ResponseEntity.ok(service.register(request));
  }


  @PostMapping("/signing")
  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }


}
