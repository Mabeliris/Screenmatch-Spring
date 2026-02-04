package com.aluracursos.demo.dto;

import com.aluracursos.demo.model.Categoria;


public record SerieDto(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double evaluacion,
        Categoria genero,
        String actores,
        String poster,
        String sinopsis) {

}
