package edu.am.pratique;

import edu.am.pratique.objetos.CategoriaEvento;
import edu.am.pratique.objetos.Evento;
import edu.am.pratique.objetos.Status;
import edu.am.pratique.objetos.Usuario;


import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Main {

    private static final List<Usuario> usuarios = new ArrayList<Usuario>();
    private static final List<Evento> eventos = new ArrayList<Evento>();

    public static void main(String[] args) throws ParseException {

        File eventsFile = new File("events.data");
        if(eventsFile.exists()) {
            eventos.addAll(lerEventos());
        }
        File usersFile = new File("users.data");
        if(usersFile.exists()) {
            usuarios.addAll(lerUsuarios());
        }

        selecionaOpcao();
    }

    private static void selecionaOpcao() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Qual função deseja executar?");
            System.out.println("1 - Registrar usuário");
            System.out.println("2 - Registrar evento");
            System.out.println("3 - Consultar usuários");
            System.out.println("4 - Consultar eventos");
            System.out.println("5 - Adicionar participante ao evento:");
            System.out.println("6 - Remover participante do evento:");
            System.out.println("7 - Sair");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Registrar usuário selecionado:");
                    registerUser();
                    break;
                case 2:
                    System.out.println("Registrar evento selecionado:");
                    registraEvento();
                    break;
                case 3:
                    System.out.println("Consultar usuários selecionado:");
                    printUsuarios();
                    break;
                case 4:
                    System.out.println("Consultar eventos selecionado:");
                    printEventos();
                    break;
                case 5:
                    System.out.println("Adicionar participante ao evento:");
                    addUserToEvent();
                    break;
                case 6:
                    System.out.println("Remover participante do evento:");
                    removeUserFromEvent();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente");
            }

        } while (opcao != 0);

    }

    private static LocalDateTime formatStringToDate(String dateTimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida:"+ e.getParsedString());
        }
        return null;
    }

    private static void printEventos() {
        eventos.sort(Comparator.comparing(Evento::getDateTime).reversed());
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-30s | %-20s | %-10s | %-30s | %-15s | %-40s | %-30s |\n", "Nome", "Data", "Status", "Endereço", "Categoria", "Descrição", "Participantes");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Evento event : eventos) {
            System.out.printf("| %-30s | %-20s | %-10s | %-30s | %-15s | %-40s | %-30s |\n",
                    event.getName(),
                    event.getDateTime(),
                    event.getStatusName(),
                    event.getAddress(),
                    event.getCategoryName(),
                    event.getDescription(),
                    event.getUsersEmails()
            );
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private  static void printUsuarios() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-40s | %-20s | %-20s |\n", "Nome", "Email", "Idade", "Localização");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");

        for (Usuario user : usuarios) {
            System.out.printf("| %-20s | %-40s | %-20s | %-20s |\n", user.getNome(), user.getEmail(), user.getIdade(), user.getLocal());
        }

        // Print table footer
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }

    private static CategoriaEvento selecionaCategoria() {
        int opcao;
        Scanner scanner = new Scanner(System.in);

        do {

            System.out.println("Categoria de enventos:");
            System.out.println("1. Festas");
            System.out.println("2. Eventos esportivos");
            System.out.println("3. Shows");
            System.out.println("0. Encerrar");
            System.out.println("Selecione uma categoria:");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Festas");
                    return CategoriaEvento.PARTY;
                case 2:
                    System.out.println("Eventos esportivos");
                    return CategoriaEvento.SPORT;
                case 3:
                    System.out.println("Shows");
                    return CategoriaEvento.SHOW;
                case 0:
                    System.out.println("Exiting the program...");
                    return  CategoriaEvento.PARTY;
                default:
                    return CategoriaEvento.PARTY;
            }
        }while (opcao != 0);
    }

    private static void registraEvento() {
        UUID uuid = UUID.randomUUID();
        Scanner scanner = new Scanner(System.in);
        Evento evento = new Evento();
        Status status;

        evento.setId(uuid);

        System.out.println("Informe o nome do evento:");
        evento.setNome(scanner.nextLine());

        System.out.println("Informe o endereço do evento:");
        evento.setEndereco(scanner.nextLine());

        System.out.println("Informe a categoria do evento:");
        evento.setCategoria(selecionaCategoria());

        System.out.println("Informe a data e horário do evento (YYYY-MM-DD HH:MM):");
        String dateTimeString = scanner.nextLine();
        LocalDateTime eventoDateTime = formatStringToDate(dateTimeString);
        evento.setDataEvento(eventoDateTime);

        LocalDateTime currentDateTime = LocalDateTime.now();

        assert eventoDateTime != null;
        if(eventoDateTime.isBefore(currentDateTime)) {
            evento.setStatus(Status.FINSISHED);
        }else if(eventoDateTime.isEqual(currentDateTime)) {
            evento.setStatus(Status.ONGOING);
        }else {
            evento.setStatus(Status.SCHEDULED);
        }

        System.out.println("Informe a descrição do evento:");
        evento.setDescricao(scanner.nextLine());
        eventos.add(evento);
        salvaEventsData(eventos);

        evento.showEvent();
        printEventos();
    }

    private static void registerUser() {
        Scanner scanner = new Scanner(System.in);
        UUID uuid = UUID.randomUUID();
        Usuario usuario = new Usuario();

        usuario.setId(uuid);

        System.out.println("Informe o nome do usuário:");
        usuario.setNome(scanner.nextLine());

        System.out.println("Informe o e-mail do usuário:");
        usuario.setEmail(scanner.nextLine());

        System.out.println("Informe a idade do usuário:");
        usuario.setIdade(scanner.nextInt());
        scanner.nextLine();

        System.out.println("Informe a cidade do usuário:");
        usuario.setCidade(scanner.nextLine());

        System.out.println("Informe a estado do usuário:");
        usuario.setUf(scanner.nextLine());

        usuarios.add(usuario);
        salvaUsersData(usuarios);
        printUsuarios();
    }

    private static Usuario findUserByEmail(String email) {
        for(Usuario usuario :usuarios) {
            if(usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    private  static Evento findEventByName(String name) {
        for(Evento evento: eventos) {
            if(evento.getName().equals(name))
                return evento;
        }
        return null;
    }

    private static void addUserToEvent() {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario;
        Evento evento;

        System.out.println("Informe um usuário pelo e-mail:");
        usuario = findUserByEmail(scanner.nextLine());

        System.out.println("Informe um evento pelo seu nome:");
        evento = findEventByName(scanner.nextLine());

        if(evento != null && usuario != null) {
            evento.setUsers(usuario);
            System.out.println("Participante atribuído ao evento com sucesso!");
        }else {
            System.out.println("Não foi possível atribuir um usuário a um evento existente");
        }

    }

    private static void removeUserFromEvent() {
        var scanner = new Scanner(System.in);
        Usuario usuario;
        Evento evento;

        System.out.println("Informe um usuário pelo e-mail:");
        usuario = findUserByEmail(scanner.nextLine());

        System.out.println("Informe um evento pelo seu nome:");
        evento = findEventByName(scanner.nextLine());

        if(usuario != null && evento != null) {
            evento.removeUser(usuario);
            System.out.println("Usuário removido com sucesso");
        } else {
            System.out.println("Não foi possível remover o participante do evento");
        }

    }

    public static void salvaEventsData(List<Evento> eventos) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("events.data"))) {
            outputStream.writeObject(eventos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  void salvaUsersData(List<Usuario> usuarios) {
        try(ObjectOutputStream outputStream  = new ObjectOutputStream(new FileOutputStream("users.data"))){
            outputStream.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Evento> lerEventos() {
        List<Evento> eventos = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("events.data"))) {
            eventos = (List<Evento>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public static  List<Usuario> lerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (ObjectInputStream inputStream =  new ObjectInputStream(new FileInputStream("users.data"))) {
            usuarios = (List<Usuario>) inputStream.readObject();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}