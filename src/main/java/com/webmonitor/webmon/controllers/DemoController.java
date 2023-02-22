package com.webmonitor.webmon.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

//  @GetMapping
//  public ResponseEntity<String> sayHello() {
//    return ResponseEntity.ok("Hello from secured endpoint");
//  }


  @GetMapping("/v1/demo-controller")
  public String sayHello2() {

    return "/demo";
  }
}
