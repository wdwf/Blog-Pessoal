package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

        @Autowired
        private TestRestTemplate testRestTemplate;

        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @BeforeAll
        void start() {
                usuarioRepository.deleteAll();

                usuarioService.cadastrarUsuario(new Usuario(null,
                                "Root", "root@root.com", "rootroot", "-"));
        }

        @Test
        @DisplayName("Cadastrar um Usuário")
        public void deveCriarUmUsuario() {
                HttpEntity<Usuario> bodyRequest = new HttpEntity<Usuario>(
                                new Usuario(null, "Bob Bobistico", "bob@email.com", "123456789", "-"));
                ResponseEntity<Usuario> bodyResponse = testRestTemplate
                                .exchange("/usuarios/cadastrar", HttpMethod.POST, bodyRequest, Usuario.class);

                assertEquals(HttpStatus.CREATED, bodyResponse.getStatusCode());
        }

        @Test
        @DisplayName("Não deve permitir duplicação de Usuário")
        public void naoDeveDuplicarUsuario() {
                usuarioService.cadastrarUsuario(
                                new Usuario(null, "Maria da Silva", "maria_silva@email.com", "123456789", "-"));
                HttpEntity<Usuario> bodyRequest = new HttpEntity<Usuario>(
                                new Usuario(null, "Maria da Silva", "maria_silva@email.com", "123456789", "-"));

                ResponseEntity<Usuario> bodyResponse = testRestTemplate
                                .exchange("/usuarios/cadastrar", HttpMethod.POST, bodyRequest, Usuario.class);

                assertEquals(HttpStatus.BAD_REQUEST, bodyResponse.getStatusCode());
        }

        @Test
        @DisplayName("Atualizar um Usuário")
        public void deveAtualizarUmUsuario() {
                Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(
                                new Usuario(null, "Bob Bobistico", "bob_teste@email.com", "123456789", "-"));
                Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), "Bob", "bob_bobistico@email.com",
                                "123456789",
                                "-");

                HttpEntity<Usuario> bodyRequest = new HttpEntity<Usuario>(usuarioUpdate);
                ResponseEntity<Usuario> bodyResponse = testRestTemplate
                                .withBasicAuth("root@root.com", "rootroot")
                                .exchange("/usuarios/atualizar", HttpMethod.PUT, bodyRequest, Usuario.class);

                assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
        }

        @Test
        @DisplayName("Listar todos os Usuários")
        public void deveMostrarTodosUsuarios() {
                usuarioService.cadastrarUsuario(
                                new Usuario(null, "Jom Sanches", "jom@email.com", "123456789", "-"));
                usuarioService.cadastrarUsuario(
                                new Usuario(null, "Rom Tankes", "Rom@email.com", "123456789", "-"));

                ResponseEntity<String> response = testRestTemplate
                                .withBasicAuth("root@root.com", "rootroot")
                                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        }
}
