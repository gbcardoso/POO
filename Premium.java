/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetopoo;

import static projetopoo.Agencia.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gabriel
 */
public class Premium extends Cliente {
    Premium(){
        super();
    }

    Premium(String nome, int nif, String morada, int telefone, String email, String pass){
        super(nome,nif,morada,telefone,email,pass,2);
    }


    @Override
    public void cancelarReserva(Reserva r, Date dia, ArrayList<Utilizador> users) throws IOException {
        /*devolver 50% do valor*/
        Date d_viagem = r.getV().getData();
        Reserva cancel = null;
        if(r.getClass().equals(Cancelada.class))
            sout("Nao é possivel cancelar uma viajem cancelada");
        else{
            if(getDateDiff(dia,d_viagem,TimeUnit.MINUTES)<1440*2 || getDateDiff(dia,d_viagem,TimeUnit.MINUTES)>=0 )
            /*se depois do prazo de 5 dias*/
                cancel = new Cancelada(r.getV());

            else if(getDateDiff(dia,d_viagem,TimeUnit.MINUTES)>1440*2){
                /*com mais de 5 dias de diferença*/
                if(r.getClass().equals(Valida.class)){
                    r.getV().setLucro(r.getV().getLucro() -(r.getValor()*0.5));
                }
                cancel = new Cancelada(r.getV());
            }
            else
                sout("A viagem ja ocorreu");
        }
        if(cancel != null){
            this.alteraReserva(r,cancel);
            r.getV().alteraReserva(r,cancel,users);
        }

        ObjectOutputStream oSU = new ObjectOutputStream(new FileOutputStream("utilizadores"));
        Agencia.escreverObjecto(oSU, users);
        oSU.close();
    }

    /**
     * Cria Reserva para uma dada viagem
     */
    @Override
    public Reserva criarReserva(Viagem v) {
        if(v.verificaEspera(v.getAlreserva(), v) == 1 ){
            sout("Ainda existem lugares disponiveis");
            int lugar = v.retorna_lugar();
            Reserva r = new Valida(v,lugar,v.getPreco()*0.9);
            return r;
        }
        else
            sout("Ja nao existem lugares disponiveis, deseja ficar em espera?");
        if(YorN()){
            Reserva r = new Espera(v);
            return r;
        }
        return null;
    }

}
