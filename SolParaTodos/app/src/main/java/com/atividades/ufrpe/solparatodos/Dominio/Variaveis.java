package com.atividades.ufrpe.solparatodos.Dominio;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pichau on 20/08/2017.
 */

public class Variaveis {

    private String cidade;
    private float temperatura;
    private String velocidadeVento;
    private String humidade;
    private String pressao;
    private long radiacao;
    private Date data;

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public String getVelocidadeVento() {
        return velocidadeVento;
    }

    public void setVelocidadeVento(String velocidadeVento) {
        this.velocidadeVento = velocidadeVento;
    }

    public String getHumidade() {
        return humidade;
    }

    public void setHumidade(String humidade) {
        this.humidade = humidade;
    }

    public String getPressao() {
        return pressao;
    }

    public void setPressao(String pressao) {
        this.pressao = pressao;
    }

    public long getRadiacao() {
        return radiacao;
    }

    public void setRadiacao(long radiacao) {
        this.radiacao = radiacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(this.data);

        return "\nVariaveis obtidas em: " + dataFormatada + "\n\n" +
                "Local: " + cidade + '\n' +
                "Temperatura: " + temperatura + '\n' +
                "Velocidade do Vento: " + velocidadeVento + '\n' +
                "Humidade: " + humidade + '\n' +
                "Pressao: " + pressao + '\n';
    }
}
