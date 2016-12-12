package projetopoo;

import java.io.Serializable;

/**
 * Created by ams on 25-11-2015.
 */
public class Autocarro implements Serializable {
    private String matricula;
    private int capacidade;

    public Autocarro(String matricula, int capacidade) {
        this.matricula = matricula;
        this.capacidade = capacidade;
    }

    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getCapacidade() {
        return capacidade;
    }
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }


    @Override
    public String toString(){
        return "Matricula: " +matricula+ "\n" +
                "Capacidade: " + capacidade + "\n----";
    }
}