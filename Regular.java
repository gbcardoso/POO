/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetopoo;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static projetopoo.Agencia.*;
/**
 *
 * @author Gabriel
 */
public class Regular extends Cliente{
    Regular(){
        super();
    }

    Regular(String nome, int nif, String morada, int telefone, String email, String password){
        super(nome,nif,morada,telefone,email,password,1);
    }


    @Override
    public void cancelarReserva(Reserva r,Date dia,ArrayList<Utilizador> users) throws IOException {
        /*devolver 50% do valor*/
        Date d_viagem = r.getV().getData();
        Reserva cancel = null;
        if(r.getClass().equals(Cancelada.class))
            sout("Nao é possivel cancelar uma viajem cancelada");
        else{
            if(getDateDiff(dia,d_viagem,TimeUnit.MINUTES)<1440*5 || getDateDiff(dia,d_viagem,TimeUnit.MINUTES)>=0 )
                /*se depois do prazo de 5 dias*/
                cancel = new Cancelada(r.getV());
            else if(getDateDiff(dia,d_viagem,TimeUnit.MINUTES)>1440*5){
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

    @Override
    public Reserva criarReserva(Viagem v) {
        if(v.verificaEspera(v.getAlreserva(), v) == 1){
            Agencia.sout("Ainda existem lugares disponiveis");
            int lugar = v.retorna_lugar();
            Reserva r = new Valida(v,lugar,v.getPreco());
            v.addReserva(r);
            return r;
        } else {
            Agencia.sout("Ja nao existem lugares disponiveis, deseja ficar em espera?");
            if(YorN()){
                Reserva r = new Espera(v);
                v.addReserva(r);
                return r;
            }
        }
        return null;
    }
}
