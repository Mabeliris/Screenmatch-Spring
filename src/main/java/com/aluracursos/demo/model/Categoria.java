package com.aluracursos.demo.model;

import org.springframework.resilience.retry.SimpleRetryInterceptor;

public enum Categoria {
    ACCION ("ACTION"),
    ROMANCE("ROMANCE"),
    COMEDIA("COMEDY"),
    DRAMA("DRAMA"),
    CRIMEN("CRIME");


    private String categoriaOmdb;
    Categoria(String categoriaOmdb){
        this.categoriaOmdb =categoriaOmdb;
    }

    public static Categoria fromString(String text){
        for(Categoria categoria: Categoria.values()){
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }

        throw new IllegalArgumentException("Ninguna Categoría encontrada: " + text);
    }
}
