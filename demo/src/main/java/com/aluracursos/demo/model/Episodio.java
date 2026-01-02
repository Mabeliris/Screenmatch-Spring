package com.aluracursos.demo.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double evluacion;
    private LocalDate fechaLanzamiento;

    public Episodio(Integer temporada, DatosEpisodios d) {
        this.temporada=temporada;
        this.titulo=d.titulo();
        this.numeroEpisodio=d.numeroEpisodio();

        try{
            this.evluacion= Double.valueOf(d.evaluacion());
        } catch (NumberFormatException e) {
            this.evluacion=0.0;
        }
        try{
            this.fechaLanzamiento= LocalDate.parse(d.fechaDeLanzamiento());
        } catch (DateTimeParseException e) {
            this.fechaLanzamiento=null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvluacion() {
        return evluacion;
    }

    public void setEvluacion(Double evluacion) {
        this.evluacion = evluacion;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evluacion=" + evluacion +
                ", fechaLanzamiento=" + fechaLanzamiento ;
    }
}
