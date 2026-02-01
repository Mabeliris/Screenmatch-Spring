package com.aluracursos.demo.repository;

import com.aluracursos.demo.model.Categoria;
import com.aluracursos.demo.model.Episodio;
import com.aluracursos.demo.model.Serie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    //Buscar serie por un numero de temporada y evaluacion(desafio)
    //series que tengan un número máximo de temporadas
    // evaluación mínima

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPortemporadaYEvaluacion (int totalTemporadas, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Episodio e WHERE e.serie = :s ORDER BY e.evaluacion DESC " )
    List<Episodio> top5Episodios( @Param("s") Serie serie,
                                  Pageable pageable);
}
