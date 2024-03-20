package edu.am.pratique.objetos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Evento implements Serializable {

    @Getter
    private UUID id;
    private String nome;
    private String  endereco;
    private LocalDateTime dataEvento;
    private String descricao;

    private CategoriaEvento categoria;

    @Getter
    private Status status;

    @Getter
    private List<Usuario> usuarios = new ArrayList<Usuario>();

    public LocalDateTime getDateTime() {
        return dataEvento;
    }

    public Evento() {
    }

    public Evento(
            UUID id,
            String nome,
            String endereco,
            LocalDateTime dataEvento,
            String descricao,
            CategoriaEvento categoria,
            List<Usuario> usuarios
    ) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.dataEvento = dataEvento;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuarios = usuarios;
    }

    public void setUsers(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void removeUser(Usuario usuario) {
        if(this.usuarios.contains(usuario)) {
            this.usuarios.remove(usuario);
        }else {
            System.out.println("Participante não cadastrado ao evento.");
        };
    }



    public String getName() {
        return nome;
    }

    public String getAddress() {
        return endereco;
    }

    public String getDescription() {
        return descricao;
    }

    public void showEvent(){
        System.out.println(this.nome+" "+this.endereco+" "+this.dataEvento.toString()+" "+this.descricao);
    }

    public CategoriaEvento getCategory() {
        return categoria;
    }

    public String getCategoryName() {
        return switch (this.categoria) {
            case CategoriaEvento.PARTY -> "Festa";
            case CategoriaEvento.SPORT -> "Eventos Esportivos";
            case CategoriaEvento.SHOW -> "Show";
        };
    }

    public String getUsersEmails() {
        StringBuilder sb = new StringBuilder();
        for(Usuario usuario : usuarios) {
            sb.append(usuario.getEmail()).append(", ");
        }
        if(!sb.isEmpty()) {
            sb.delete(sb.length() -2, sb.length());
        }
        return sb.toString();
    }

    public String getStatusName() {
        return switch (this.status) {
            case Status.FINSISHED -> "FINALIZADO";
            case Status.SCHEDULED -> "AGENDADO";
            case Status.ONGOING -> "EM ANDAMENTO";
        };
    }




}
