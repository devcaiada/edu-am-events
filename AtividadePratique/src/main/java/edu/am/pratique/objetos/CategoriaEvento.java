package edu.am.pratique.objetos;

import lombok.Getter;
import lombok.Setter;

@Getter

public enum CategoriaEvento{
    PARTY("PARTY"),
    SPORT("SPORT"),
    SHOW("SHOW");


    private final String valor;

    CategoriaEvento(String valor) {
        this.valor = valor;
    }

    public String getValue() {
        return valor;
    }
}
