package com.generation.blogpessoal.model;

import java.time.LocalDate;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_postagens")
public class Postagem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O atributo é necessario") // não permite espaços em branco
  @Size(min = 3, max = 255)
  private String titulo;

  @NotBlank(message = "O atributo é necessario") // não permite espaços em branco
  @Size(min = 3, max = 1000, message = "O atributo deve conter no minimo 10 e no maximo 1000 caracteres")
  private String texto;

  @UpdateTimestamp
  private LocalDate data;

  @ManyToOne
  @JsonIgnoreProperties("postagem")
  private Tema tema;

  @ManyToOne
  @JsonIgnoreProperties("postagem")
  private Usuario usuario;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public Tema getTema() {
    return tema;
  }

  public void setTema(Tema tema) {
    this.tema = tema;
  }

}
