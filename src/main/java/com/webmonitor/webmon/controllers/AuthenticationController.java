package com.webmonitor.webmon.controllers;

import com.webmonitor.webmon.auth.AuthenticationRequest;
import com.webmonitor.webmon.auth.AuthenticationResponse;
import com.webmonitor.webmon.repositories.UserRepository;
import com.webmonitor.webmon.services.AuthenticationService;
import com.webmonitor.webmon.auth.RegisterRequest;
import com.webmonitor.webmon.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        service.register(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/auth/signin");
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }

    @GetMapping("/signup")
    public String signup() {
        return "/signup";
    }


    @GetMapping("/signin")
    public String showProfile(Model model, HttpServletRequest request) {
        String email = service.getEmailFromCookie(request);
        return userRepository.findByEmail(email)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "/profile";
                })
                .orElse("/signin");
    }


//    private String getEmailFromCookie(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("jwt")) {
//                    String token = cookie.getValue();
//                    return jwtService.extractUsername(token);
//                }
//            }
//        }
//        return null;
//    }


    @PostMapping("/signing")
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request, HttpServletResponse response) {

        String token = service.authenticate(request).getToken();
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        cookie.setSecure(true);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/website");

        String cookieValue = String.format("%s=%s; HttpOnly; Max-Age=%d; Path=/; Secure; SameSite=Strict",
                cookie.getName(), cookie.getValue(), cookie.getMaxAge());
        response.addHeader("Set-Cookie", cookieValue);

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }

}
