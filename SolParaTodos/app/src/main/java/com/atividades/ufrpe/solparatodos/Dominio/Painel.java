package com.atividades.ufrpe.solparatodos.Dominio;

/**
 * Created by Pichau on 20/08/2017.
 */

public class Painel {

    private String nome;
    private long tamanho;
    private float aproveitamento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }

    public float getAproveitamento() {
        return aproveitamento;
    }

    public void setAproveitamento(float aproveitamento) {
        this.aproveitamento = aproveitamento;
    }

    @Override
    public String toString() {
        return "Painel:" +
                "\nNome = '" + nome + '\'' +
                "\nTamanho = " + tamanho + " m²" +
                "\nAproveitamento = " + aproveitamento + " %";
    }
}
