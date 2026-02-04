package com.aluracursos.demo.service;

import com.aluracursos.demo.dto.EpisodioDto;
import com.aluracursos.demo.dto.SerieDto;
import com.aluracursos.demo.model.Categoria;
import com.aluracursos.demo.model.Episodio;
import com.aluracursos.demo.model.Serie;
import com.aluracursos.demo.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;
    public List<SerieDto> obtenerTodasLasSeries (){
        return convierteDatos((repository.findAll()));
    }

    public List<SerieDto> obtenerTop5() {
        return convierteDatos((repository.findTop5ByOrderByEvaluacionDesc()));
    }

    public List<SerieDto> obtenerLanzamientosMasRecientes() {
        return convierteDatos((repository.lanzamientosMasRecientes()));
    }

    public  List<SerieDto> convierteDatos(List<Serie> serie){
       return serie.stream()
                .map(s->new SerieDto(s.getId(),s.getTitulo(),s.getTotalTemporadas(),s.getEvaluacion(),s.getGenero(),
                        s.getActores(),s.getPoster(),s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public SerieDto otenerPorId(Long id) {
        Optional<Serie> serie=  repository.findById(id);
        if(serie.isPresent()){
            Serie s= serie.get();
            return new SerieDto(s.getId(),s.getTitulo(),s.getTotalTemporadas(),s.getEvaluacion(),s.getGenero(),
                    s.getActores(),s.getPoster(),s.getSinopsis());
        }
        return null;
    }

    public List<EpisodioDto> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie=  repository.findById(id);
        if (serie.isPresent()){
            Serie s= serie.get();
            return s.getEpisodios().stream().map(e->new EpisodioDto(e.getTemporada(),e.getTitulo(),
                    e.getNumeroEpisodio())).collect(Collectors.toList());
        }
        return null;
    }



    public List<EpisodioDto> obtenerTemporadasPorNumero(Long id, Long temporada) {
         return repository.obtenerTemporadasPorNumero(id, temporada).stream()
                 .map(e->new EpisodioDto(e.getTemporada(),e.getTitulo(),
                         e.getNumeroEpisodio())).collect(Collectors.toList());
    }

    public List<SerieDto> categoriaPorGenero(String nombreGenero) {
        Categoria categoria=Categoria.fromEspanol(nombreGenero);
        return convierteDatos(repository.findByGenero(categoria));
    }

    public List<EpisodioDto> top5Episodios(Long id) {

        var serie = repository.findById(id).get();
        return repository.top5Episodios(serie)
                .stream()
                .map(e -> new EpisodioDto(e.getTemporada(),e.getTitulo(),
                        e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

}
