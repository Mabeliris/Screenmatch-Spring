package com.aluracursos.demo.controller;

import com.aluracursos.demo.dto.EpisodioDto;
import com.aluracursos.demo.dto.SerieDto;
import com.aluracursos.demo.model.Serie;
import com.aluracursos.demo.repository.SerieRepository;
import com.aluracursos.demo.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;
    @GetMapping()
    public List<SerieDto> obtenerTodasLasSeries (){
        return service.obtenerTodasLasSeries();
    }

    @GetMapping("/top5")
    public  List<SerieDto> obtenerTop5(){
       return service.obtenerTop5();

    }

    @GetMapping("/lanzamientos")
    public  List<SerieDto> obtenerLanzamientos(){
        return service.obtenerLanzamientosMasRecientes();
    }

    @GetMapping("/{id}")
    public SerieDto obtenerPorId(@PathVariable Long id){
        return service.otenerPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDto> obtenerTodasLasTemporadas(@PathVariable Long id){
        return service.obtenerTodasLasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDto> obtenerTemporadasPorNumero(@PathVariable Long id,
                                                        @PathVariable Long numeroTemporada){
        return service.obtenerTemporadasPorNumero(id,numeroTemporada);
    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDto> categoriaPorGenero(@PathVariable String nombreGenero){
        return service.categoriaPorGenero(nombreGenero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDto> top5Episodios(@PathVariable Long id){
        return service.top5Episodios(id);
    }


}
