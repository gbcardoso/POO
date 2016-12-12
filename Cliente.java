/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetopoo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import static projetopoo.Agencia.*;
/**
 *
 * @author Gabriel
 */
public abstract class Cliente extends Utilizador {
    /**
     * Class com informaçao dos Clientes relativamente as suas Reservas, mensagens recebidas e  Comentarios realizados
     */
    protected ArrayList<Reserva> listReserv;
    protected ArrayList <Comentario> listComentario;
    protected ArrayList <String> listMensagem;

    Cliente(String nome, int nif, String morada, int telefone, String email, String pass, int tipo){
        super(nome,nif,morada,telefone,email,pass,tipo);
        this.listReserv = new ArrayList<>();
        this.listComentario = new ArrayList<>();
        this.listMensagem = new ArrayList<>();
    }

    Cliente(){
        super();
        this.listReserv = new ArrayList<>();
        this.listComentario = new ArrayList<>();
        this.listMensagem = new ArrayList<>();
    }

    /**
     * Percorre a lista de Reservas 1 a 1 e pergunta ao Cliente se é aquela q pretende
     * @return viagem escolhida, null se nenhuma
     */
    public Reserva escolherReserva(){
        Reserva atual;
        for(Reserva aux : listReserv){
            sout(aux);
            if(YorN()){
                atual = aux;
                return atual;
            }
        }
        return null; /*caso nao haja uma escolha*/
    }
    public ArrayList<String> getListMensagem() {
        return listMensagem;
    }


    /**
     * Metodo para criar reserva cancelada e remover Reserva escolhida
     * está declarado abstract pois varia a diferença de dias e o dinheiro devolvido
     * @param dia recebe o dia para comparar com a data da reserva e ter em conta a diferença entre eles
     * @param users recebe este parametro para conseguir viagem conseguir enviar mensagem aos cliente em espera caso reserva cancelada for valida
     */
    public abstract void cancelarReserva(Reserva r,Date dia, ArrayList<Utilizador> users) throws IOException;

    public Viagem escolherViagem(ArrayList<Viagem> listViagens,Date date){
        /*invoca metodo da main para escolher viagens */
        if(!listViagens.isEmpty()){
            for(Viagem v: listViagens){
                if(v.getData().compareTo(date) >= 0)
                    Agencia.sout(v);
                if(YorN()){
                    return v;
                }
            }
        }
        return null; /*caso nao haja uma opçao escolhida*/

    }

    public void addMensagem(String x){
        this.listMensagem.add(x);
    }

    public Viagem escolherViagemRealizada(Date date){
        /*invoca metodo da main para escolher viagens */

        if(!this.listReserv.isEmpty()){
            sout("Escolha uma viagem realizada");
            for(Reserva r: listReserv){
                if(r.getV().getData().compareTo(date) <= 0 && r.getClass().equals(Valida.class)){
                    sout(r.getV());
                    if(YorN())
                        return r.getV();
                }
            }
        }
        return null; /*caso nao haja uma opçao escolhida*/
    }

    /**
     * Cria reserva para uma dada viagem
     * verifica se consegue valida
     * senao pergunta se que ficar em espera
     * @param v viagem escolhida
     * @return Reserva criada senao null
     */
    public abstract Reserva criarReserva(Viagem v) throws IOException;


    public void addReserva(Reserva v) throws IOException {
        listReserv.add(v);
    }

    public void consultaReserva(){
        if(!listReserv.isEmpty())
            for(Reserva v: this.listReserv)
                System.out.println(v);
    }

    public void consultaComentariosRealizados(){
        if(!listReserv.isEmpty())
            for(Comentario c: this.listComentario)
                System.out.println(c);
    }

    public void realizarComentario(Viagem v){
        /*cria comentario*/

        Comentario newCom = new Comentario();
        listComentario.add(newCom);
        /*adicionar comentario na viagem */
        v.addComentario(newCom);
    }

    /**
     * Metodo que altera na lista de Reservas uma certa reserva
     * ---->Ignora a ordem
     * @param antiga reserva a remover
     * @param nova reserva a adicionar
     */
    public void alteraReserva(Reserva antiga, Reserva nova){

        int check = 0;
        for(Reserva v :  listReserv ){
            if(v.equals(antiga)){
                listReserv.remove(antiga);
                check = 1;
                break;
            }
        }
        if(check ==1)
            listReserv.add(nova);
    }

    /**
     * Metodo que aquando do login le as mensagem referentes as reservas espera que podem ser passadas a Validas
     * remove mensagem apos serem impressas
     */
    public void leMensagens(){
        if(!listMensagem.isEmpty()){
            sout("Nova(s) Mensagem(ns)");
            for(String mens: listMensagem){
                System.out.println(mens);
            }
            listMensagem.removeAll(listReserv);
            sout("Nota: cancele a sua reserva em espera primeiro");
        }
    }

    public ArrayList<Reserva> getListReserv() {
        return listReserv;
    }

    public void leComentario(Viagem v){
        v.soutComentarios();
    }
    public void leComentarioRealizados(ArrayList <Viagem> vlist){
        if(!vlist.isEmpty()){
            for(Comentario c: this.listComentario){
                Viagem v_comentario = c.getViagemdoComentario(vlist);
                if(v_comentario != null )
                    sout(v_comentario +"\n\t" + c);

                else
                    sout("Viagem foi eliminda :" + c);
            }
        }
        else{
            sout("Ainda nao realizou comentarios");
        }
    }
}
