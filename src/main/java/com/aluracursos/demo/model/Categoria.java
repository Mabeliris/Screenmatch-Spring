package com.aluracursos.demo.model;

import org.springframework.resilience.retry.SimpleRetryInterceptor;

public enum Categoria {
    ACCION ("ACTION", "ACCION"),
    ROMANCE("ROMANCE", "ROMANCE"),
    COMEDIA("COMEDY", "COMEDIA"),
    DRAMA("DRAMA", "DRAMA"),
    CRIMEN("CRIME", "CRIMEN");


    private String categoriaOmdb;
    private String categoriaEspanol;
    Categoria(String categoriaOmdb, String categoriaEspanol){
        this.categoriaOmdb =categoriaOmdb;
        this.categoriaEspanol= categoriaEspanol;
    }

    public static Categoria fromString(String text){
        for(Categoria categoria: Categoria.values()){
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }

        throw new IllegalArgumentException("Ninguna Categoría encontrada: " + text);
    }

    public static Categoria fromEspanol(String text){
        for(Categoria categoria: Categoria.values()){
            if(categoria.categoriaEspanol.equalsIgnoreCase(text)){
                return categoria;
            }
        }

        throw new IllegalArgumentException("Ninguna Categoría encontrada: " + text);
    }
}
