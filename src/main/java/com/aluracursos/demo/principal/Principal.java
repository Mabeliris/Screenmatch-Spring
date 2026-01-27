package com.aluracursos.demo.principal;

import com.aluracursos.demo.model.DatosSerie;
import com.aluracursos.demo.model.DatosTemporadas;
import com.aluracursos.demo.model.Episodio;
import com.aluracursos.demo.model.Serie;
import com.aluracursos.demo.repository.SerieRepository;
import com.aluracursos.demo.service.ConsumoApi;
import com.aluracursos.demo.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private final SerieRepository repositorio;
    private final ConsumoApi consumoApi;
    private final ConvierteDatos conversor;
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final List <DatosSerie> datosSerie = new ArrayList<>();
    private  List<Serie> series;


    @Value("${api.key}")
    private String API_KEY ;

    public Principal(
            SerieRepository repositorio,
            ConsumoApi consumoApi,
            ConvierteDatos conversor
    ) {
        this.repositorio = repositorio;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarPorTitulo();

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }




    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&type=series" + "&apikey=" + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }
    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("¿De que serie deseas ver los episodios?");
        var nombreSerie=teclado.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s->s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            var serieEncontrada=serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i +  "&apikey=" + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios=temporadas.stream()
                    .flatMap(d->d.episodios().stream()
                            .map(e->new Episodio(d.numeroDeTemporada(),e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }

    }
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie=new Serie(datos);
        repositorio.save(serie);
        //datosSerie.add(datos);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {

        //metodo son jpa repository
       /*List<Serie> series= new ArrayList<>();
       series= datosSerie.stream()
               .map(d->new Serie(d))
               .collect(Collectors.toList());*/

        //metoddo con jpa

        series=repositorio.findAll();
       series.stream()
               .sorted(Comparator.comparing(Serie::getGenero))
               .forEach(System.out::println);
    }

    private void buscarPorTitulo() {
    }



}
