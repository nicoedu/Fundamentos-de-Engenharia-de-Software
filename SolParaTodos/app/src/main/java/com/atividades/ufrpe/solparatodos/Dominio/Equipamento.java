package com.atividades.ufrpe.solparatodos.Dominio;

import java.util.Date;

/**
 * Created by Pichau on 21/08/2017.
 */

public class Equipamento {

    private String estado;
    private String identificador;
    private float consumo;
    private float consumoPadrao;
    private float consumoGeral;
    private String nome;
    private Date data;

    public Equipamento() {
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getEstado() {
        if (this.estado.equals("1")) {
            return Estado.LIGADO.getValor();
        } else {
            return Estado.DELISGADO.getValor();
        }
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }

    public float getConsumoPadrao() {
        return consumoPadrao;
    }

    public void setConsumoPadrao(float consumoPadrao) {
        this.consumoPadrao = consumoPadrao;
    }

    public float getConsumoGeral() {
        return consumoGeral;
    }

    public void setConsumoGeral(float consumoGeral) {
        this.consumoGeral = consumoGeral;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
