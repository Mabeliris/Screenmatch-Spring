package com.aluracursos.demo.repository;

import com.aluracursos.demo.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie,Long> {
}
