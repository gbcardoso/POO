/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetopoo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import projetopoo.Agencia.*;

/**
 *
 * @author Gabriel & Simões
 */

public class Admin extends Utilizador implements Serializable {
    public Admin() {
    }

    public Admin(String nome, int nif, String morada, int telefone, String email, String password, int tipo) {
        super(nome, nif, morada, telefone, email, password, tipo);
    }

    public Utilizador criarCliente(ArrayList<Utilizador> users) throws IOException {
        String nome, morada, email, password, tipoInput;
        int tipo, nif, telefone;
        // Scanner input = new Scanner(System.in);

        nome = Agencia.getNome("Nome do cliente:");
        do {
            nif = Agencia.getInt("NIF do cliente:");
        } while (checkNif(nif, users));
        morada = Agencia.getString("Morada do cliente:");
        telefone = Agencia.getInt("Telefone do cliente:");
        email = Agencia.getEmail("Email do cliente:");
        password = Agencia.getString("Password do cliente:");
        do {
            tipoInput = Agencia.getString("Que tipo de utilizador quer criar (1 - Regular |" +
                    " 2 - Premium |" +
                    " 3 - Admin)");
        } while (!optionsCheck(tipoInput, 1, 3));
        tipo = Integer.parseInt(tipoInput);

        ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
        if (tipo == 3) {
            Admin admin = new Admin(nome, nif, morada, telefone, email, password, tipo);
            users.add(admin);
            Agencia.escreverObjecto(oS, users);
            System.out.println("Admin adicionado com sucesso!");
            oS.close();
            return admin;
        } else if (tipo == 1) {
            Regular regular = new Regular(nome, nif, morada, telefone, email, password);
            users.add(regular);
            Agencia.escreverObjecto(oS, users);
            System.out.println("Cliente Regular adicionado com sucesso!");
            oS.close();
            return regular;
        } else if (tipo == 2) {
            Premium premium = new Premium(nome, nif, morada, telefone, email, password);
            users.add(premium);
            Agencia.escreverObjecto(oS, users);
            System.out.println("Cliente Premium adicionado com sucesso!");
            oS.close();
            return premium;
        }
        oS.close();

        return null;
    }

    public Utilizador eliminarCliente(ArrayList<Utilizador> users) throws IOException {
        int nif;

        do {
            nif = Agencia.getInt("NIF do cliente:");
        } while (!checkNif(nif, users));

        ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
        Utilizador aux;
        aux = procurarCliente(nif, users);
        users.remove(users.indexOf(aux));

        Agencia.escreverObjecto(oS, users);
        oS.close();

        System.out.println("Cliente removido com sucesso!");

        return aux;
    }

    public void listarClientes(ArrayList<Utilizador> users) {
        if (users.isEmpty()) {
            System.out.println("Lista de clientes VAZIA.");
        } else {
            System.out.println("\n- LISTA CLIENTES -:");
            for (Utilizador aux : users) {
                if (!aux.getClass().equals(Admin.class))
                    System.out.println(aux);
            }
            System.out.println("- FIM DA LISTA -\n");
        }
    }

    public Utilizador procurarCliente(int nif, ArrayList<Utilizador> users) {
        for (Utilizador user : users) {
            if (user.getNif() == nif) {
                return user;
            }
        }
        return null;
    }

    public Utilizador alterarCliente(ArrayList<Utilizador> users) throws IOException {
        Scanner sc = new Scanner(System.in);
        String nome, morada, email, password, input;
        int tipo, tp = 0, nif, telefone;

        do {
            nif = Agencia.getInt("NIF do cliente que pretendes alterar:");
        } while (!checkNif(nif, users));

        Utilizador aux = procurarCliente(nif, users);

        System.out.println("Alteracoes de Cliente:");
        System.out.println("1 - Alterar Tudo\n" +
                "2 - Alterar Nome\n" +
                "3 - Alterar NIF\n" +
                "4 - Alterar Morada\n" +
                "5 - Alterar Contacto\n" +
                "6 - Alterar Email\n" +
                "7 - Alterar Password\n" +
                "8 - Alterar Tipo\n" +
                "9 - Fechar");
        System.out.println("Operacao:");
        int res = Agencia.getInt("Operacao: ");
        while (res < 1 || res > 9) {
            System.out.println("Escolha invalida. Tente outra vez.");
            res = Agencia.getInt("Operacao: ");
        }

        ObjectOutputStream oS;
        switch (res) {
            case 1:
                nome = Agencia.getNome("Novo nome:");
                do {
                    nif = Agencia.getInt("Novo NIF:");
                } while (checkNif(nif, users));
                morada = Agencia.getString("Nova morada:");
                telefone = Agencia.getInt("Novo telefone:");
                while (telefone == 0) {
                    telefone = Agencia.getInt("Novo telefone:");
                }
                do {
                    email = Agencia.getEmail("Novo email:");
                } while (checkEmail(email, users));
                password = Agencia.getString("Nova password:");
                if (aux.getTipo() != 3) {
                    tp = 2; // nao admin
                } else {
                    tp = 3; // admin
                }

                System.out.println("Novo tipo (1 - Regular | 2 - Premium | 3 - Admin):");
                tipo = Agencia.getInt("Tipo: ");
                while (tipo < tp || tipo > tp) {
                    System.out.println("Escolha invalida.");
                    tipo = Agencia.getInt("Tipo: ");
                }

                aux.setNome(nome);
                aux.setNif(nif);
                aux.setMorada(morada);
                aux.setTelefone(telefone);
                aux.setEmail(email);
                aux.setPass(password);
                aux.setTipo(tipo);

                oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                Agencia.escreverObjecto(oS, users);
                oS.close();
                break;
            case 2:
                nome = Agencia.getNome("Novo nome:");
                aux.setNome(nome);
                oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                Agencia.escreverObjecto(oS, users);
                oS.close();
                break;
            case 3:
                do {
                    nif = Agencia.getInt("Novo NIF:");
                } while (checkNif(nif, users));
                oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                Agencia.escreverObjecto(oS, users);
                oS.close();
                aux.setNif(nif);
                break;
            case 4:
                morada = Agencia.getString("Nova morada:");
                oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                Agencia.escreverObjecto(oS, users);
                oS.close();
                aux.setMorada(morada);
                break;
            case 5:
                telefone = Agencia.getInt("Novo telefone:");
                while (telefone == 0) {
                    telefone = Agencia.getInt("Novo telefone:");
                }
                oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                Agencia.escreverObjecto(oS, users);
                oS.close();
                aux.setTelefone(telefone);
                break;
            case 6:
                do {
                    email = Agencia.getEmail("Novo email:");
                } while (checkEmail(email, users));
                oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                Agencia.escreverObjecto(oS, users);
                oS.close();
                aux.setEmail(email);
                break;
            case 7:
                password = Agencia.getString("Nova password:");
                oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                Agencia.escreverObjecto(oS, users);
                oS.close();
                aux.setPass(password);
                break;
            case 8:
                System.out.println("Novo tipo (1 - Regular | 2 - Premium | 3 - Admin):");
                tipo = Agencia.getInt("Tipo: ");
                while (tipo < 1 || tipo > 3) {
                    System.out.println("Escolha invalida.");
                    tipo = Agencia.getInt("Tipo: ");
                }
                oS = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                Agencia.escreverObjecto(oS, users);
                oS.close();
                aux.setTipo(tipo);
                break;
            case 9:
                break;
            default:
                System.out.println("Operacao Invalida!");
        }

        return aux;
    }

    public Viagem criarViagem(ArrayList<Viagem> viagens, ArrayList<Autocarro> autocarros, Date inicio) throws IOException {
        String origem, destino;
        double preco, lucro;
        int duracao;
        int code;
        String input;

        System.out.println("-- Criacao Viagem --");

        code = Agencia.getInt("Codigo da Viagem:");
        while (code == 0)
            code = Agencia.getInt("Codigo da viagem:");
        while (checkCodigoViagem(code, viagens)) {
            System.out.println("Codigo de viagem ja existente!");
            code = Agencia.getInt("Insira outra vez:");
        }
        origem = Agencia.getNome("Origem da Viagem:");
        destino = Agencia.getNome("Destino da Viagem:");
        preco = Agencia.getInt("Preco da Viagem:");
        while (preco < 1) {
            System.out.println("Preco invalido. Tem de ser maior que 0.");
            preco = Agencia.getInt("Preco da Viagem:");
        }

        duracao = Agencia.getInt("Duracao da viagem:");
        while (duracao < 1) {
            System.out.println("Duracao invalida.");
            duracao = Agencia.getInt("Duracao da viagem:");
        }

        System.out.println("Data atual = " + inicio);
        Date date = Agencia.getDate();
        if (inicio.compareTo(date) >= 0) {
            System.out.println("Data de viagem invalida. Apenas permitido marcar viagens para o futuro.");
            System.out.println("Data atual: " + date);
            date = Agencia.getDate();
        }

        System.out.println("- AUTOCARROS -");
        int num_bus = Agencia.getInt("Numero de Autocarros a usar:");
        while (num_bus < 1) {
            System.out.println("Numero de autocarros invalido. Introduza pelo menos um.");
            num_bus = Agencia.getInt("Numero de Autocarros a usar:");
        }

        ArrayList<Autocarro> novosAutocarros = new ArrayList<Autocarro>(num_bus);

        int res;
        if (autocarros.isEmpty()) {
            System.out.println("Nenhum autocarro existente.");
        }
        for (int i = 0; i < num_bus; i++) {
            if (autocarros.size() < num_bus) {
                System.out.println("Numero de autocarros disponiveis menor que numero de autocarros da viagem.");
                System.out.println("- Criar novo autocarro -");
                res = 1;
            } else {
                System.out.println("1 - Criar novo autocarro.\n" +
                        "2 - Designar autocarro ja existente para esta viagem.");
                res = Agencia.getInt("Escolha: ");
                while (res < 1 || res > 2) {
                    System.out.println("Opcao invalida.");
                    res = Agencia.getInt("Escolha:");
                }
            }

            switch (res) {
                case 1:
                    novosAutocarros.add(criarAutocarros(autocarros));
                    break;
                case 2:
                    listarAutocarros(autocarros);
                    do {
                        input = Agencia.getString("Matricula do autocarro que pretende:");
                    } while (!checkMatricula(input, autocarros));
                    Autocarro aux = procurarAutocarros(input, autocarros);
                    novosAutocarros.add(aux);
                    break;
            }
        }

        ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
        Viagem viagem = new Viagem(code, origem, destino, preco, date, duracao, comentarios, novosAutocarros);
        viagens.add(viagem);
        ObjectOutputStream oSV = new ObjectOutputStream(new FileOutputStream("viagens"));
        Agencia.escreverObjecto(oSV, viagens);
        oSV.close();
        ObjectOutputStream oSA = new ObjectOutputStream(new FileOutputStream("autocarros"));
        Agencia.escreverObjecto(oSA, autocarros);
        oSA.close();
        return viagem;
    }

    public Viagem eliminarViagem(ArrayList<Viagem> viagens) throws IOException {
        int codigo = Agencia.getInt("Codigo da viagem a remover = ");


        if (!checkCodigoViagem(codigo, viagens)) {
            System.out.println("Viagem nao existente na lista.");
            return null;
        }

        Viagem aux;
        aux = procurarViagem(codigo, viagens);
        viagens.remove(viagens.indexOf(aux));
        ObjectOutputStream oSV = new ObjectOutputStream(new FileOutputStream("viagens"));
        Agencia.escreverObjecto(oSV, viagens);
        oSV.close();

        return aux;
    }

    public Viagem procurarViagem(int codigo, ArrayList<Viagem> viagens) {
        for (Viagem elem : viagens) {
            if (elem.getCodigo() == codigo) {
                return elem;
            }
        }
        return null;
    }

    public Viagem alterarViagem(ArrayList<Viagem> viagens) throws IOException {
        int codigo;

        do {
            codigo = Agencia.getInt("Codigo da viagem que pretende alterar:");
        } while (!checkCodigoViagem(codigo, viagens));

        String origem, destino, lucro;
        Viagem aux;
        String input;
        int res, duracao, code, preco;
        Date date;

        aux = procurarViagem(codigo, viagens);

        System.out.println("Operacao:");
        System.out.println("1 -- Alterar Tudo" +
                "2 -- Alterar Origem" +
                "3 -- Alterar Destino" +
                "4 -- Alterar Preco" +
                "5 -- Alterar Data" +
                "6 -- Alterar Duracao");
        int op = Agencia.getInt("Opcao: ");
        while (op < 1 || op > 8) {
            op = Agencia.getInt("Opcao: ");
        }

        ObjectOutputStream oS;
        switch (op) {
            case 1:
                origem = Agencia.getNome("Nova Origem:");
                destino = Agencia.getNome("Novo Destino:");
                preco = Agencia.getInt("Novo preco:");
                while (preco < 1) {
                    System.out.println("Preco invalido. Tem de ser maior que 0.");
                    preco = Agencia.getInt("Novo preco:");
                }
                duracao = Agencia.getInt("Nova duracao:");
                while (duracao < 1) {
                    System.out.println("Duracao invalida. Tem de ser maior que 0.");
                }

                date = Agencia.getDate();

                aux.setOrigem(origem);
                aux.setDestino(destino);
                aux.setDuracaoMin(duracao);
                aux.setPreco(preco);
                aux.setData(date);

                oS = new ObjectOutputStream(new FileOutputStream("viagens"));
                Agencia.escreverObjecto(oS, viagens);
                oS.close();
                break;
            case 2:
                origem = Agencia.getNome("Nova Origem:");
                aux.setOrigem(origem);
                oS = new ObjectOutputStream(new FileOutputStream("viagens"));
                Agencia.escreverObjecto(oS, viagens);
                oS.close();
                break;
            case 3:
                destino = Agencia.getNome("Novo Destino:");
                aux.setDestino(destino);
                oS = new ObjectOutputStream(new FileOutputStream("viagens"));
                Agencia.escreverObjecto(oS, viagens);
                oS.close();
                break;
            case 4:
                preco = Agencia.getInt("Novo preco:");
                while (preco < 1) {
                    System.out.println("Preco invalido. Tem de ser maior que 0.");
                    preco = Agencia.getInt("Novo preco:");
                }
                oS = new ObjectOutputStream(new FileOutputStream("viagens"));
                Agencia.escreverObjecto(oS, viagens);
                oS.close();
                aux.setPreco(preco);
            case 5:
                date = Agencia.getDate();
                aux.setData(date);
                oS = new ObjectOutputStream(new FileOutputStream("viagens"));
                Agencia.escreverObjecto(oS, viagens);
                oS.close();
                break;
            case 6:
                duracao = Agencia.getInt("Nova duracao:");
                while (duracao < 1) {
                    System.out.println("Duracao invalida. Tem de ser maior que 0.");
                }
                oS = new ObjectOutputStream(new FileOutputStream("viagens"));
                Agencia.escreverObjecto(oS, viagens);
                oS.close();
                aux.setDuracaoMin(duracao);
                break;
            default:
                System.out.println("Operacao invalida.");
        }

        return aux;
    }

    public void listarViagens(ArrayList<Viagem> viagens) {
        if (viagens.isEmpty()) {
            System.out.println("Lista de viagens VAZIA.");
        } else {
            System.out.println("Lista de todas as Viagens:");
            for (Viagem aux : viagens) {
                System.out.println(aux);
            }
        }
    }

    public Autocarro criarAutocarros(ArrayList<Autocarro> autocarros) throws IOException {
        String matricula;
        int capacidade;
        Autocarro autocarro;

        matricula = Agencia.getString("Matricula do autocarro:");
        while (checkMatricula(matricula, autocarros)) {
            System.out.println("Autocarro ja existente!!!");
            matricula = Agencia.getString("Matricula do autocarro:");
        }
        capacidade = Agencia.getInt("Capacidade do autocarro:");

        autocarro = new Autocarro(matricula, capacidade);
        autocarros.add(autocarro);
        System.out.println("Autocarro com a matricula " + matricula + " criado com sucesso!");

        ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("autocarros"));
        Agencia.escreverObjecto(oS, autocarros);
        oS.close();
        return autocarro;
    }

    public Autocarro eliminarAutocarro(ArrayList<Autocarro> autocarros) throws IOException {
        Autocarro aux;

        String matricula = Agencia.getString("Matricula do Autocarro a remover");

        if (!checkMatricula(matricula, autocarros)) {
            System.out.println("Autocarro nao existente na lista.");
            return null;
        }

        aux = procurarAutocarros(matricula, autocarros);
        autocarros.remove(autocarros.indexOf(aux));
        ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("autocarros"));
        Agencia.escreverObjecto(oS, autocarros);
        oS.close();

        return aux;
    }

    public Autocarro procurarAutocarros(String matricula, ArrayList<Autocarro> autocarros) {
        for (Autocarro aux : autocarros) {
            if (aux.getMatricula().equalsIgnoreCase(matricula))
                return aux;
        }
        return null;
    }

    public Autocarro alterarAutocarros(ArrayList<Autocarro> autocarros) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Matricula do autocarro que pretende alterar:");
        String matricula = sc.nextLine();
        Autocarro aux;

        aux = procurarAutocarros(matricula, autocarros);

        if (aux != null) {
            System.out.println("Operacao:");
            System.out.println("1 -- Alterar Matricula\n2 -- Alterar Capacidade");
            int res = Agencia.getInt("Escolha: ");
            if (res == 1) {
                System.out.println("Matricula atual = " + aux.getMatricula());
                matricula = Agencia.getString("Nova matricula:");
                aux.setMatricula(matricula);
            } else {
                System.out.println("Capacidade atual = " + aux.getCapacidade());
                int capacidade = Agencia.getInt("Nova capacidade:");
                aux.setCapacidade(capacidade);
            }
            ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("autocarros"));
            Agencia.escreverObjecto(oS, autocarros);
            oS.close();
            return aux;
        } else {
            System.out.println("Autocarro a alterar nao encontrado.");
            return null;
        }
    }

    public void listarAutocarros(ArrayList<Autocarro> autocarros) {
        if (autocarros.isEmpty()) {
            System.out.println("Lista de autocarros VAZIA!");
        } else {
            System.out.println("Lista de autocarros:");
            for (int i = 0; i < autocarros.size(); i++) {
                System.out.println("- Autocarro " + (i + 1));
                System.out.println(autocarros.get(i));
            }
        }
    }
}


