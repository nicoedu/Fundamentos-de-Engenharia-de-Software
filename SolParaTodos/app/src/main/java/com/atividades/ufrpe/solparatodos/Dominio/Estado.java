package com.atividades.ufrpe.solparatodos.Dominio;

/**
 * Created by Pichau on 21/08/2017.
 */

public enum Estado {
    LIGADO("1"), DELISGADO("0");

    private String valor;

    Estado(String i) {
        this.valor = i;
    }

    public String getValor() {
        return this.valor;
    }
}
