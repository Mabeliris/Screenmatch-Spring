package com.aluracursos.demo;

import com.aluracursos.demo.model.DatosEpisodios;
import com.aluracursos.demo.model.DatosSerie;
import com.aluracursos.demo.model.DatosTemporadas;
import com.aluracursos.demo.principal.Principal;
import com.aluracursos.demo.repository.SerieRepository;
import com.aluracursos.demo.service.ConsumoApi;
import com.aluracursos.demo.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {


	private final Principal principal;

	public ScreenmatchApplication(Principal principal) {
		this.principal = principal;
	}



	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) {

		principal.muestraElMenu();
	}
}


