package com.generation.blogpessoal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
  @GetMapping
  public String helloWorld() {
    return """
        <h1>Lista</h1>
        <a href="/postagem">Postagens</a>
        """;
  }
}
