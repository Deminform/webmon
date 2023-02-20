package com.webmonitor.webmon.controllers;

import com.webmonitor.webmon.auth.AuthenticationRequest;
import com.webmonitor.webmon.auth.AuthenticationResponse;
import com.webmonitor.webmon.services.AuthenticationService;
import com.webmonitor.webmon.auth.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    /* ------------------- signup ------------------- */

//    @PostMapping("/signup")
//    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
//        System.out.println("* * * * * * * * * * * * Response contain the data: " + request.getEmail() + " " + request.getFirstname() + " " + request.getLastname());
//        return ResponseEntity.ok(service.register(request));
//    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {  /* RedirectAttributes redirectAttributes */

        service.register(request);

//        redirectAttributes.addFlashAttribute("email", request.getEmail());
//        redirectAttributes.addFlashAttribute("firstname", request.getFirstname());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/");

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
//        return ResponseEntity.ok().headers(headers).build();
    }

    /* ------------------- signing ------------------- */
    @PostMapping("/signing")
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request, HttpServletResponse response) {

        String token = service.authenticate(request).getToken();
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        cookie.setSecure(true);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/");

        String cookieValue = String.format("%s=%s; HttpOnly; Max-Age=%d; Path=/; Secure; SameSite=Strict",
                cookie.getName(), cookie.getValue(), cookie.getMaxAge());
        response.addHeader("Set-Cookie", cookieValue);

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
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


//    @PostMapping("/signup")
//    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
//        log.info("SignUp form request: - " + request.toString());
//        HttpHeaders headerToken = new HttpHeaders();
//        headerToken.add("Authorization", "Bearer " + service.register(request).getToken());
//        return ResponseEntity.ok().headers(headerToken).build();
//    }






//    @RequestMapping("/signing")
//    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("MyResponseHeader", "MyValue");
//        return new ResponseEntity<>("Hello World", ResponseEntity.ok(service.authenticate(request)), HttpStatus.OK);
//    }


}
