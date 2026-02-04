package com.aluracursos.demo.repository;

import com.aluracursos.demo.dto.SerieDto;
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

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    //Buscar serie por un numero de temporada y evaluacion(desafio)
    //series que tengan un número máximo de temporadas
    // evaluación mínima

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPortemporadaYEvaluacion (int totalTemporadas, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    @Query("SELECT s FROM Serie s" + " JOIN s.episodios e" + " GROUP BY s " + "ORDER BY MAX(e.fechaLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :temporada")
    List<Episodio> obtenerTemporadasPorNumero(Long id, Long temporada);

}
