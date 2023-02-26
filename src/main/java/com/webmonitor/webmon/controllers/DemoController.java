package com.webmonitor.webmon.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

  @GetMapping
  public String sayHello2() {

    return "/demo";
  }
}
