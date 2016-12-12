package projetopoo;


import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gabriel & Simões
 */

public class Agencia implements Serializable {
    private ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>();
    private ArrayList<Autocarro> autocarros = new ArrayList<Autocarro>();
    private ArrayList<Viagem> viagens = new ArrayList<Viagem>();
    Date inicio;

    public Agencia(ArrayList<Utilizador> utilizadores, ArrayList<Autocarro> autocarros, ArrayList<Viagem> viagens) {
        this.utilizadores = utilizadores;
        this.autocarros = autocarros;
        this.viagens = viagens;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public ArrayList<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public void setUtilizadores(ArrayList<Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public ArrayList<Autocarro> getAutocarros() {
        return autocarros;
    }

    public void setAutocarros(ArrayList<Autocarro> autocarros) {
        this.autocarros = autocarros;
    }

    public ArrayList<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(ArrayList<Viagem> viagens) {
        this.viagens = viagens;
    }

    public static boolean optionsCheck(String str, int min, int max) {
        try {
            int input = Integer.parseInt(str);
            return !(input < min || input > max);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Utilizador userCheck(String email, String password, ArrayList<Utilizador> users) {
        for (Utilizador aux : users) {
            if (aux.getEmail().equalsIgnoreCase(email) && aux.getPass().equalsIgnoreCase(password)) {
                return aux;
            }
        }
        return null;
    }

    public boolean checkNif(int nif) {
        for (Utilizador cliente : utilizadores)
            if (cliente.getNif() == nif)
                return true;
        return false;
    }

    public static int checkData(int dia, int mes, int ano) {
        if (dia > 31 || dia <= 0)
            return 0;
        if (mes > 12 || mes <= 0)
            return 0;
        if (ano < 1970 || ano > 3000)
            return 0;
        if ((mes == 11 || mes == 4 || mes == 6 || mes == 9) && (dia == 31))
            return 0;
        if ((ano % 4 == 0 && dia > 29 && mes == 2) || (mes == 2 && dia == 30))
            return 0;
        return 1;
    }

    /**
     * Verifica se hora é valida
     *
     * @return 0 se falso, 1 se hora for valida
     */
    public static int checkHora(int hora, int minuto, int segundo) {
        if (hora > 24 || hora < 0 || minuto > 60 || minuto < 0 || segundo > 60 || segundo < 0)
            return 0;
        return 1;
    }


    public static Date getDate() {
        /*comparar 2 viagens*/
        /*x.compareTo(y)*/
        String data;
        int dia = 0, mes = 0, ano = 0, check = 0;
        System.out.println("Introduza a data (dd/mm/aaaa):");
        Scanner input = new Scanner(System.in);

        while (check == 0) {
            data = input.nextLine();
            try {
                if (data.matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]") ||
                        data.matches("[0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]") ||
                        data.matches("[0-9][0-9]/[0-9]/[0-9][0-9][0-9][0-9]") ||
                        data.matches("[0-9]/[0-9]/[0-9][0-9][0-9][0-9]")) {
                    Scanner dayScanner = new Scanner(data);
                    dayScanner.useDelimiter("/");
                    dia = dayScanner.nextInt();
                    mes = dayScanner.nextInt() - 1;
                    ano = dayScanner.nextInt();
                    check = checkData(dia, mes, ano);
                }
                if (check == 0) {
                    System.out.println("A data introduzida nao é valida!");
                    System.out.println("Introduza data valida:");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Introduza data valida!");
                check = 0;
            }
        }
        /*******************/
        System.out.println("Introduza a hora (hh:mm:ss):");
        int hora = 0, min = 0, segundo = 0;
        check = 0;
        String horario;
        while (check == 0) {
            horario = input.nextLine();

            try {
                if (horario.matches("[0-9][0-9]:[0-9][0-9]:[0-9][0-9]") ||
                        (horario.matches("[0-9]:[0-9][0-9]:[0-9][0-9]")) ||
                        (horario.matches("[0-9][0-9]:[0-9]:[0-9][0-9]")) ||
                        (horario.matches("[0-9][0-9]:[0-9][0-9]:[0-9]")) ||
                        (horario.matches("[0-9]:[0-9]:[0-9][0-9]")) ||
                        (horario.matches("[0-9]:[0-9][0-9]:[0-9]")) ||
                        (horario.matches("[0-9][0-9]:[0-9]:[0-9]")) ||
                        (horario.matches("[0-9]:[0-9]:[0-9]"))) {

                    Scanner hourScanner = new Scanner(horario);
                    hourScanner.useDelimiter(":");
                    hora = hourScanner.nextInt();
                    min = hourScanner.nextInt();
                    segundo = hourScanner.nextInt();
                    check = checkHora(hora, min, segundo);
                }
                if (check == 0) {
                    System.out.println("A hora introduzida nao é valida !");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Introduza hora valida!");
                check = 0;
            }
        }

        Calendar c = Calendar.getInstance();
        c.set(ano, mes, dia, hora, min, segundo);
        Date x = c.getTime();
        System.out.println(x);
        return x;
    }

    /**
     * Get a diff between two dates
     * comparar com 24*60*(dias diferença)
     *
     * @param date1    data a tras 1/1/1
     * @param date2    a frente 2/2/2
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {

    /*se negativo quer dizer q a data 1 é no futuro*/
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static boolean YorN() {
        Scanner sc = new Scanner(System.in);
        String c;
        do {
            System.out.println("É esta a opçao que deseja (Y/N)?:");
            c = sc.nextLine();
            c = c.toLowerCase();
        } while (!c.equals("y") && !c.equals("n"));

        return c.equals("y");
    }

    public static int getInt(String mensagem) {
        Scanner sc = new Scanner(System.in);
        int inteiro = 0, flag = 1;
        try {
            do {
                System.out.println(mensagem);
                inteiro = sc.nextInt();
                sc.nextLine();
            } while (inteiro <= 0);
        } catch (InputMismatchException e) {
            System.out.println("Introduza apenas um inteiro");
        }

        return inteiro;
    }

    public static Double getDouble(String mensagem) {
        Scanner sc = new Scanner(System.in);
        Double d = 0.0;
        try {
            do {
                System.out.println(mensagem);
                d = sc.nextDouble();
                sc.nextLine();
            } while (d < 0);
        } catch (InputMismatchException e) {
            System.out.println("introduza apenas um double");
        }

        return d;
        /*
        do{
            x = getDouble("Introduza o Double");
        }while(x== 0);
        */
    }

    public static String getString(String mensagem) {
        Scanner sc = new Scanner(System.in);
        String x;
        System.out.println(mensagem);
        x = sc.nextLine();
        return x;
    }

    public static String getNome(String mensagem) {
        int flag;
        String x;

        do {
            flag = 0;
            Scanner sc = new Scanner(System.in);
            System.out.println(mensagem);
            x = sc.nextLine();
            try {
                double d = Double.parseDouble(x);
                System.out.println("Erro. Nome nao pode ter algarismos.");
                flag = 1;
            } catch (NumberFormatException e) {
                flag = 0;
            }
        } while (flag == 1);

        return x;
    }

    public static String getEmail(String mensagem) {
        String email;
        Scanner input = new Scanner(System.in);
        System.out.println(mensagem);
        while (!input.hasNext("[a-zA-Z]+[@]+[a-zA-Z]+[.]+[a-zA-Z]+")) {
            System.out.println("Email Inválido. Insira de novo:");
            input.nextLine();
        }
        email = input.nextLine();

        return email;

    }

    public static void sout(Object mensagem) {
        System.out.println(mensagem);
    }

    public static int escolhe_lugar(ArrayList<Integer> lugares) {
        sout("lugares disponiveis: ");
        for (int i : lugares) {
            System.out.print(i + " ");
        }
        int lugar;
        System.out.println();
        do {

            lugar = getInt("Qual o lugar q pretende?");
            if (!lugares.contains(lugar))
                System.out.println("O lugar ja se encontra ocupado ou nao existe");
        } while (!lugares.contains(lugar) && lugar != 0);
        return lugar;
    }

    public void escreverLinha(File f, String linha) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(f));
            writer.println(linha);
            System.out.println(linha);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao existente!");
        } catch (IOException e) {
            System.out.println("Excepcao I/O Geral: " + e.getMessage());
        }
    }

    public String lerLinha(File f) {
        String linha = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            if ((linha = reader.readLine()) != null)
                return linha;
            reader.close();
        } catch (IOException e) {
            System.out.println("Ficheiro nao existente!" + e.getMessage());
        }

        return linha;
    }

    /**
     *
     * @param fn Nome do ficheiro a verificar
     * @return true se ficheiro vazio, falso se ficheiro existente
     * @throws IOException
     */
    public boolean ficheiroVazio(String fn) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fn));
        return br.readLine() == null;
    }

    public Object lerObjecto(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    public static void escreverObjecto(ObjectOutputStream oS, Object obj) throws IOException {
        oS.writeObject(obj);
    }

    private static void listarViagem(ArrayList<Viagem> listViagens, Date date) {
        /**
         * Listar viagens que a Data seja depois da data de entrade no sistema
         */
        if (!listViagens.isEmpty()) {
            for (Viagem v : listViagens) {
                if (v.getData().compareTo(date) >= 0) {
                    System.out.println(v);
                }
            }
        }
    }

    public static void menuAdmin(ArrayList<Utilizador> users, ArrayList<Viagem> viagens, ArrayList<Autocarro> buses, Admin admin, Date atual) throws IOException {
        Scanner sc = new Scanner(System.in);
        String input;
        int escolha;

        for (;;) {
            System.out.println("-- MENU ADMIN --\n" +
                    "[0] - Logout\n" +
                    "[1] - Criar Cliente\n" +
                    "[2] - Listar clientes\n" +
                    "[3] - Alterar cliente\n" +
                    "[4] - Eliminar cliente\n" +
                    "[5] - Criar Viagem\n" +
                    "[6] - Listar viagens\n" +
                    "[7] - Alterar viagem\n" +
                    "[8] - Eliminar viagem\n" +
                    "[9] - Criar Autocarro\n" +
                    "[10] - Listar autocarros\n" +
                    "[11] - Alterar autocarro\n" +
                    "[12] - Eliminar autocarro\n" +
                    "[13] - Extras");
            System.out.println("Operacao: ");
            input = sc.nextLine();
            while (!optionsCheck(input, 0, 13)) {
                System.out.println("Escolha invalida. Tente outra vez:");
                input = sc.nextLine();
            }
            escolha = Integer.parseInt(input);

            switch (escolha) {
                case 0:
                    ObjectOutputStream oSU = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                    ObjectOutputStream oSV = new ObjectOutputStream(new FileOutputStream("viagens"));
                    ObjectOutputStream oSA = new ObjectOutputStream(new FileOutputStream("autocarros"));
                    escreverObjecto(oSU, users);
                    oSU.close();
                    escreverObjecto(oSV, viagens);
                    oSV.close();
                    escreverObjecto(oSA, buses);
                    oSA.close();
                    return;
                case 1:
                    admin.criarCliente(users);
                    admin.listarClientes(users);
                    break;
                case 2:
                    admin.listarClientes(users);
                    break;
                case 3:
                    admin.alterarCliente(users);
                    admin.listarClientes(users);
                    break;
                case 4:
                    admin.eliminarCliente(users);
                    admin.listarClientes(users);
                    break;
                case 5:
                    Viagem aux = admin.criarViagem(viagens, buses, atual);
                    System.out.println(aux);
                    admin.listarViagens(viagens);
                    break;
                case 6:
                    admin.listarViagens(viagens);
                    break;
                case 7:
                    admin.alterarViagem(viagens);
                    admin.listarViagens(viagens);
                    break;
                case 8:
                    admin.eliminarViagem(viagens);
                    admin.listarViagens(viagens);
                    break;
                case 9:
                    admin.criarAutocarros(buses);
                    admin.listarAutocarros(buses);
                    break;
                case 10:
                    admin.listarAutocarros(buses);
                    break;
                case 11:
                    admin.alterarAutocarros(buses);
                    admin.listarAutocarros(buses);
                    break;
                case 12:
                    admin.eliminarAutocarro(buses);
                    admin.listarAutocarros(buses);
                    break;
                case 13:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
            if (escolha == 13)
                break;
        }
    }

    public static void menuClient(ArrayList<Utilizador> users, ArrayList<Viagem> viagens, ArrayList<Autocarro> autocarros, Cliente c, Date data)
            throws IOException, ClassNotFoundException {
        String input;
        Scanner sc = new Scanner(System.in);
        c.leMensagens();
        while (true) {
            sout("-- MENU CLIENTE --\n" +
                    "[0] - Logout\n" +
                    "[1] - Listar Viagens Disponiveis\n" +
                    "[2] - Fazer Reserva\n" +
                    "[3] - Cancelar Reserva\n" +
                    "[4] - Listar Reservas\n" +
                    "[5] - Comentar Viagem Realizada\n" +
                    "[6] - Ler comentarios de Viagem\n" +
                    "[7] - Ler comentarios Realizados\n" +
                    "Operacao desejada:");
            input = sc.nextLine();
            while (!optionsCheck(input, 0, 7)) {
                sout("Opcao invalida. Tente outra vez:");
                input = sc.nextLine();
            }
            int escolha = Integer.parseInt(input);


            switch (escolha) {
                case 1:
                    sout("Lista de Viagens disponiveis");
                    listarViagem(viagens, data);
                    break;
                case 2:
                    Viagem v = c.escolherViagem(viagens, data);
                    System.out.println(v);
                    if (v != null) {
                        try {
                            Reserva r = c.criarReserva(v);
                            c.addReserva(r);
                            v.addReserva(r);
                        } catch (NullPointerException e) {
                            System.out.println("!!! Excepcao apanhada. !!!" + e.getMessage());
                        }
                    } else
                        sout("Nao foi criada a Reserva");
                    break;
                case 3:
                    Reserva r1 = c.escolherReserva();
                    if (r1 != null)
                        c.cancelarReserva(r1, data, users);
                    else
                        sout("Nenhuma reserva selecionada");
                    break;
                case 4:
                    sout("Lista de Reservas : ");
                    c.consultaReserva();
                    break;
                case 5:
                    Viagem v_coment = c.escolherViagemRealizada(data);
                    if (v_coment != null)
                        c.realizarComentario(v_coment);
                    break;
                case 6:
                    Viagem le_coment = c.escolherViagem(viagens, data);
                    if (le_coment != null)
                        c.leComentario(le_coment);
                    break;
                case 7:
                    c.leComentarioRealizados(viagens);
                    break;
                case 0:
                    System.out.println("Logout");
                    ObjectOutputStream oSU = new ObjectOutputStream(new FileOutputStream("utilizadores"));
                    ObjectOutputStream oSV = new ObjectOutputStream(new FileOutputStream("viagens"));
                    ObjectOutputStream oSA = new ObjectOutputStream(new FileOutputStream("autocarros"));
                    escreverObjecto(oSU, users);
                    oSU.close();
                    escreverObjecto(oSV, viagens);
                    oSV.close();
                    escreverObjecto(oSA, autocarros);
                    oSA.close();
                    return;
                default:
                    System.out.println("Opçao invalida");
                    break;
            }
        }
    }

    private static Utilizador login(ArrayList<Utilizador> users) {
        System.out.println("** LOGIN **");
        int escolha;

        Scanner sc = new Scanner(System.in);

        System.out.println("- Welcome -");
        System.out.println("[1] - LOGIN\n[0] - EXIT\nOpcao:");
        String input = sc.nextLine();
        while (!optionsCheck(input, 0, 1)) {
            System.out.println("Escolha invalida.");
            input = sc.nextLine();
        }
        escolha = Integer.parseInt(input);

        if (escolha == 0) {
            return null;
        } else {
            System.out.println("Insira o Email:");
            String email = sc.nextLine();
            System.out.println("Insira a Password:");
            String pass = sc.nextLine();

            while(userCheck(email, pass, users) == null) {  // login falhado
                System.out.println("Login falhado. Utilizador nao existente ou password errada.");
                System.out.println("Insira o Email:");
                email = sc.nextLine();
                System.out.println("Insira a Password:");
                pass = sc.nextLine();
            }

            return userCheck(email, pass, users);   // retorna o user
        }
    }

    private void menu(ArrayList<Utilizador> users, ArrayList<Viagem> viagens, ArrayList<Autocarro> autocarros) {
        Date inicio = getDate();
        while(true) {
            Utilizador user = login(users);
            if (user == null)   // login falhado ou fechar programa?
                return;

            try {
                if (user.getTipo() == 1 || user.getTipo() == 2) {  // Regular
                    Cliente cliente = (Cliente) user;
                    menuClient(users, viagens, autocarros, cliente, inicio);
                } else {
                    Admin admin = (Admin) user;
                    menuAdmin(users, viagens, autocarros, admin, inicio);
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<Autocarro> autocarros = new ArrayList<Autocarro>();
        ArrayList<Viagem> viagens = new ArrayList<Viagem>();
        ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>();

        Agencia agencia = new Agencia(utilizadores, autocarros, viagens);

        File f_users = new File("utilizadores");
        boolean teste = f_users.createNewFile();
        File f_viagens = new File("viagens");
        f_viagens.createNewFile();
        File f_bus = new File("autocarros");
        f_bus.createNewFile();

        if (!agencia.ficheiroVazio("utilizadores")) {
            ObjectInputStream iS = new ObjectInputStream(new FileInputStream("utilizadores"));
            try {
                utilizadores = (ArrayList<Utilizador>) agencia.lerObjecto(iS);
                iS.close();
            } catch (EOFException e) {
                System.out.println("haha");
                e.printStackTrace();
            }
        }

        if(!agencia.ficheiroVazio("viagens")) {
            ObjectInputStream iS = new ObjectInputStream(new FileInputStream("viagens"));
            try {
                viagens = (ArrayList<Viagem>) agencia.lerObjecto(iS);
                iS.close();
            } catch (EOFException e) {
                System.out.println("haha");
                e.printStackTrace();
            }
        }
        if(!agencia.ficheiroVazio("autocarros")) {
            ObjectInputStream iS = new ObjectInputStream(new FileInputStream("autocarros"));
            try {
                autocarros = (ArrayList<Autocarro>) agencia.lerObjecto(iS);
                iS.close();
            } catch (EOFException e) {
                System.out.println("haha");
                e.printStackTrace();
            }
        }

        if(teste){
            Admin admin = new Admin("Admin", 123, "rua", 112, "admin", "admin", 3);
            utilizadores.add(admin);
        }

        agencia.menu(utilizadores, viagens, autocarros);

    }
}
