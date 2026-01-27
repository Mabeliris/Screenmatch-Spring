package com.aluracursos.demo.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
@Entity
@Table(name="series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(unique = true)
    private String titulo;

    private Integer totalTemporadas;

    private Double evaluacion;

    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private String actores;

    private String poster;

    private String sinopsis;

    @OneToMany (mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;


    public Serie(){};
    public Serie(DatosSerie datosSerie){
          this.titulo= datosSerie.titulo();
          this.totalTemporadas= datosSerie.totalTemporadas();
          this.evaluacion= Optional.ofNullable(datosSerie.evaluacion())
                  .map(Double::parseDouble)
                  .orElse(0.0);
          this.genero = datosSerie.genero() != null
                ? Categoria.fromString(datosSerie.genero().split(",")[0].trim())
                : null;


        this.actores= datosSerie.actores();
          this.poster= datosSerie.poster();
          this.sinopsis= datosSerie.sinopsis();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e->e.setSerie(this));
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return
                " genero=" + genero +
                "titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", evaluacion=" + evaluacion +
                ", actores='" + actores + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", episodios='" + episodios + '\''  ;
    }
}
