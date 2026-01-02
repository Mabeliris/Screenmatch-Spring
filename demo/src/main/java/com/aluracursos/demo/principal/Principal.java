package com.aluracursos.demo.principal;

import com.aluracursos.demo.model.DatosEpisodios;
import com.aluracursos.demo.model.DatosSerie;
import com.aluracursos.demo.model.DatosTemporadas;
import com.aluracursos.demo.model.Episodio;
import com.aluracursos.demo.service.ConsumoApi;
import com.aluracursos.demo.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;



@Component
public class Principal {

    private final Scanner lectura = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConvierteDatos conversor = new ConvierteDatos();

    private final String URL_BASE = "https://www.omdbapi.com/?t=";

    @Value("${api.key}")
    private String API_KEY ;



    public void muestraElMenu() {

        System.out.println("Ingresa el nombre de la serie");
        var nombreSerie = lectura.nextLine();

        String urlSerie = URL_BASE
                + nombreSerie.replace(" ", "+")
                + "&type=series"
                + "&apikey=" + API_KEY;

        var json = consumoApi.obtenerDatos(urlSerie);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);

        System.out.println(datos);

        if (datos.totalTemporadas() == null) {
            System.out.println("No se pudo obtener el número de temporadas");
            return;
        }

        int totalTemporadas = datos.totalTemporadas();

        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= totalTemporadas; i++) {

            String urlTemporada = URL_BASE
                    + nombreSerie.replace(" ", "+")
                    + "&type=series"
                    + "&season=" + i
                    + "&apikey=" + API_KEY;

            json = consumoApi.obtenerDatos(urlTemporada);
            var datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
        }

        //temporadas.forEach(System.out::println);

        //mostrr solo el titulo de los episodios para las temporadas

        /*for (int i = 0; i < datos.totalTemporadas(); i++) {

            List<DatosEpisodios> episodiosTemporadas= temporadas.get(i).episodios();

            for (int j = 0; j < episodiosTemporadas.size() ; j++) {
                System.out.println(episodiosTemporadas.get(j).titulo());

            }

        }*/

        temporadas.forEach(t->t.episodios().forEach(e-> System.out.println(e.titulo())));

        //convertir todas las infirmaciones a una lista de datos episodios

        List<DatosEpisodios> datosEpisodios=temporadas.stream()
                .flatMap(t->t.episodios().stream())
                .collect(Collectors.toList());

        //top 5 episdios


       /* System.out.println("top 5 episodios");
        datosEpisodios.stream()
                .filter(e->!e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodios::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);*/



        //cinvirtiendo los datos a una lista de episodios
        List<Episodio> episodios =temporadas.stream()
                .flatMap(t->t.episodios().stream()
                        .map(d-> new Episodio(t.numeroDeTemporada(),d)))
                .collect(Collectors.toList());

       // episodios.forEach(System.out::println);

        //busqueda de episodios a partir de x años

        /*System.out.println("Desde que año deseas ver los episodios?:");
        var fecha=lectura.nextInt();
        lectura.hasNextLine();

        LocalDate fechaBusqueda=LocalDate.of(fecha,1,1);

        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e->e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
                .forEach(e-> System.out.println(

                        "Temporada " + e.getTemporada() +
                                " Titulo " + e.getTitulo() +
                                " Fecha de lanzamiento " + e.getFechaLanzamiento().format(dtf)
                ));*/

        //buscar episodios por algunas partes del titulo

       /* System.out.println("que episodio deseas ver?: ");
        var parteTitulo= lectura.nextLine();
       Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(parteTitulo.toUpperCase()))
                .findFirst();

       if(episodioBuscado.isPresent()){
           System.out.println("Episodio encontrado");

           System.out.println(" los datos son " + episodioBuscado.get());
       }else {
           System.out.println(" Episodio no encontrado ");
       }*/

        Map<Integer,Double> evaluacionesPorTemporadas= episodios.stream()
                .filter(e->e.getEvluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                         Collectors.averagingDouble(Episodio::getEvluacion)));

        System.out.println("Evaluacion por temporada " + evaluacionesPorTemporadas);

        DoubleSummaryStatistics est= episodios.stream()
                .filter(e->e.getEvluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvluacion));

        System.out.println("estadisticas completas " + est);

        //est devuelve varias cosas que pueden ser llamadas como llamas a un objeto
        System.out.println("Episodiomejor peor evaluado: " + est.getMin());


    }
}
