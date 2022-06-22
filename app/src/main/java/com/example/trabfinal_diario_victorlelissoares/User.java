package com.example.trabfinal_diario_victorlelissoares;

import java.io.Serializable;

public class User implements Serializable {
    private int idUser;
    private String nome;
    private String email;
    //private byte[] foto;
    private String senha;//substituir futuramente pelo hash da senha ao inves da própria
    //fora a foto do usuario

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Nome: " + getNome() + ", Email: " + getEmail() + "\n";
    }
}
