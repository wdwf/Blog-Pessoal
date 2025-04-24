package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O Atributo Nome é obrigatório")
    private String nome;

    @NotBlank(message = "O Atributo Usuario é obrigatório")
    private String usuario;

    @NotNull(message = "O Atributo Senha é obrigatório")
    private String senha;

    @NotBlank(message = "O Atributo Foto é obrigatório")
    private String foto;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = { CascadeType.REMOVE })
    @JsonIgnoreProperties("usuario")
    private List<Postagem> postagem;

}
