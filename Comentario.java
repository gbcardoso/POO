package projetopoo;
import projetopoo.Agencia.*;

import java.util.*;
import java.io.Serializable;

/**
 * Created by ams on 25-11-2015.
 */
public class Comentario implements Serializable {
    private String comment;
    private int pont;

    public Comentario(){
        do{
            pont = Agencia.getInt("Introduza a pontuaçao: ");
            if(pont < 1 || pont>5)
                System.out.println("A pontuaçao so vai de 1 ate 5");
        }while(pont < 1 || pont>5);
        this.comment = Agencia.getString("Introduza o comentario:");
    }

    /*adicionar tb em viagem*/
    public String toString(){
        return pont +": "+ comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPont() {
        return pont;
    }

    public void setPont(int pont) {
        this.pont = pont;
    }

    /**
     * @return Viagem à qual pertence o Comentario
     * nao deve devolver null, mas pode vir a acontecer se viagem for eliminada
     */
    public Viagem getViagemdoComentario(ArrayList <Viagem> vlist){

        if(!vlist.isEmpty()){
            for(Viagem v : vlist){
                if(v.getComentarios().contains(this))
                    return v;
            }
        }
        return null;
    }
}