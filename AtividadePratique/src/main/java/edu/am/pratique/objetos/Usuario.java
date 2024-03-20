package edu.am.pratique.objetos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class Usuario implements Serializable {

    @Setter
    private UUID id;
    private String nome;
    @Setter
    private int idade;
    @Setter
    private String cidade;
    @Setter
    private String uf;

    @Setter
    private String email;

    public Usuario() {

    }
    public Usuario(UUID id, String nome, String email, int idade, String cidade, String uf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.cidade = cidade;
        this.uf = uf;
    }


    public String getLocal() {
        return this.getCidade()+","+this.getUf();
    }


}
