package com.atividades.ufrpe.solparatodos.Dominio;

/**
 * Created by Pichau on 05/08/2017.
 */

public class Usuario {

    private String nome;
    private String sobrenome;
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario() {

    }

}
