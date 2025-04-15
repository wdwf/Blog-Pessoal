package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

  @Autowired
  private PostagemRepository postagemRepository;

  @GetMapping
  public ResponseEntity<List<Postagem>> getAll() {
    return ResponseEntity.ok(postagemRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Postagem> getById(@PathVariable Long id) {
    return postagemRepository.findById(id)
        .map(resposta -> ResponseEntity.ok(resposta))
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping("/titulo")
  public ResponseEntity<List<Postagem>> getByTitulo(@RequestParam String titulo) {
    return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
  }

  @PostMapping()
  public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(postagemRepository.save(postagem));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Postagem> put(@PathVariable Long id, @Valid @RequestBody Postagem postagem) {
    return postagemRepository.findById(id)
        .map(resposta -> {
          postagem.setId(id);
          return ResponseEntity.ok().body(postagemRepository.save(postagem));
        })
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}