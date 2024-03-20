package edu.am.pratique.objetos;

import lombok.Getter;
import lombok.Setter;

@Getter


public enum Status {
    FINSISHED(1),
    SCHEDULED(2),
    ONGOING(3);

    private final int valor;

    Status(int valor) {
        this.valor = valor;
    }
}
