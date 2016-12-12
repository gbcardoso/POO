package projetopoo;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Gabriel & Simões
 */

public class Viagem implements Serializable {
    private int codigo;
    private String origem;
    private String destino;
    private double preco;
    private Date data;
    private int duracaoMin;
    private int capacidade;
    private double lucro;

    private ArrayList <Autocarro> ala;
    private ArrayList <Comentario> alc;
    private ArrayList <Reserva> alreserva;

    public Viagem() {}

    public Viagem(int codigo, String origem, String destino, double preco, Date data, int duracaoMin, ArrayList<Comentario> comentarios, ArrayList<Autocarro> autocarros) {
        this.codigo = codigo;
        this.origem = origem;
        this.destino = destino;
        this.preco = preco;
        this.data = data;
        this.duracaoMin = duracaoMin;
        this.alc = comentarios;
        this.ala = autocarros;
    }

    public int getCapacidade() {
        return capacidade;
    }
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public ArrayList<Comentario> getAlc() {
        return alc;
    }
    public void setAlc(ArrayList<Comentario> alc) {
        this.alc = alc;
    }

    public ArrayList<Reserva> getAlreserva() {
        return alreserva;
    }
    public void setAlreserva(ArrayList<Reserva> alreserva) {
        this.alreserva = alreserva;
    }

    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getOrigem() {
        return origem;
    }
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public ArrayList<Autocarro> getAla() {
        return ala;
    }
    public void setAla(ArrayList<Autocarro> ala) {
        this.ala = ala;
    }

    public int getDuracaoMin() {
        return duracaoMin;
    }
    public void setDuracaoMin(int duracaoMin) {
        this.duracaoMin = duracaoMin;
    }

    public double getLucro() {
        return lucro;
    }
    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    public ArrayList<Comentario> getComentarios() {
        return alc;
    }
    public void setComentarios(ArrayList<Comentario> alc) {
        this.alc = alc;
    }

    public double mediaComentario() {
        ArrayList<Comentario> comentarios = this.getComentarios();
        double media = 0;
        int total = 0;

        for(Comentario comentario : comentarios) {
            media += comentario.getPont();
            total++;
        }

        return media/total;
    }

    public void addComentario(Comentario coment){
        /*adicionar comentario a lista*/
        alc.add(coment);
    }

    public void addReserva(Reserva v){
        alreserva.add(v);
    }

    public void soutComentarios(){
        /*todos os comentarios*/
        for(Comentario c: alc)
            System.out.println(c);
    }

    @Override
    public String toString(){
        return "-- Viagem --\nCodigo: " +codigo+ "\n" +
                "Origem: " +origem+ "\n" +
                "Destino: " +destino+ "\n" +
                "Data: " +data+ "\n" +
                "Preco: " +preco + "\n" +
                "Duracao: "+duracaoMin+ " minutos\n" +
                "Numero de autocarros: " +ala.size()+ "\n";
    }

    /**
     * Metodo que altera na lista de Reservas uma certa reserva
     * ---->Ignora a ordem
     * @param antiga reserva a remover
     * @param nova reserva a adicionar
     */
    public void alteraReserva(Reserva antiga, Reserva nova, ArrayList<Utilizador> users){
        /*recede 2 reservas */
        int check = 0;
        for(Reserva v :  alreserva ){
            if(v.equals(antiga)){
                alreserva.remove(antiga);
                check = 1;
                if(antiga.getClass().equals(Espera.class))
                    //enviaMensagem(users);
                break;
            }
        }
        if(check == 1)
            alreserva.add(nova);
    }

    /**
     * verifica se ainda ha reservas validas
     * @return se houver retorna lugar disponivel, senao 0
     */
    public int verificaEspera(ArrayList<Reserva> reserva, Viagem viagem){
        System.out.println("A verificar viagens em espera...");
        if(!reserva.isEmpty()) {
            System.out.println("Dentro");
            int pos_ocup = 0;
            for (Reserva v : reserva) {
                if (v.getClass().equals(Valida.class))
                    pos_ocup++;
                if (pos_ocup >= viagem.getCapacidade())
                    return 0;
            }
        }
        System.out.println("Fora");
        return 1;
    }


    /**
     * obter lugares disponiveis para escolha do user
     * evocar o metodo verifica espera antes desta com if(v.verificaEspera() == 1)
     * @return lugares disponiveis para reserva valida
     */
    public int retorna_lugar(){
        int x = 0;
        if(!alreserva.isEmpty()) {
            int lugares[] = new int[capacidade + 1];
            ArrayList<Integer> lugares_disponiveis = new ArrayList<>();
            for (int i = 0; i <= capacidade; i++)
                lugares[i] = i;

            for (Reserva v : alreserva)
                lugares[v.getLugar()] = 0;

            for (int i = 1; i <= capacidade; i++) {
                if (lugares[i] != 0)
                    lugares_disponiveis.add(i);
            }
            x = Agencia.escolhe_lugar(lugares_disponiveis);
        }
        return x;
    }


    /**
     *
     * @param lugar lugar a verificar se esta ocupado
     * @return false se lugar livre, true se ocupado
     */
    public boolean checkLugarOcupado(int lugar){
        if(!alreserva.isEmpty()) {
            int lugares[] = new int[capacidade + 1];
            ArrayList<Integer> lugares_disponiveis = new ArrayList<>();
            for (int i = 0; i <= capacidade; i++)
                lugares[i] = i;

            for (Reserva v : alreserva)
                lugares[v.getLugar()] = 0;

            for (int i = 1; i <= capacidade; i++) {
                if (lugares[i] != 0)
                    lugares_disponiveis.add(i);
            }
            if (lugares_disponiveis.contains(lugar))
                return false;
            else
                return true;
        }
        return true;
    }

    /**
     * Caso seja cancelada uma reserva valida evocar este metodo
     * envia mensagem aos clientes atraves "u instanceof Cliente"
     * a informar que a viagem tem lugares disponiveis
     * caso alguem reserve esse lugar as mensagens nao sao apagadas
     */
    /*public void enviaMensagem(ArrayList<Utilizador> users){
        Cliente c ;
        if(verificaEspera() == 1){
            ArrayList<Cliente> clist = new ArrayList<>();
            for(Reserva v : alreserva){
                if(v.getClass().equals(Espera.class)){
                    for(Utilizador u : users){
                        if(u instanceof Cliente){
                            c = (Cliente) u;
                            ArrayList<Reserva> rlist = c.getListReserv();
                            if(rlist.contains(v))
                                c.getListMensagem().add(v + "tem lugares disponiveis");
                        }

                    }
                }
            }
        }
    }*/
}
