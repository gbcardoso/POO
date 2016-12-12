package projetopoo;


import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Gabriel & Simões
 */

public abstract class Utilizador implements Serializable {
    protected String nome;
    protected String morada;
    protected int telefone;
    protected String email;
    protected String pass;
    protected int tipo, nif;

    public Utilizador() {}

    public Utilizador(String nome, int nif, String morada, int telefone, String email, String pass, int tipo) {
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.telefone = telefone;
        this.email = email;
        this.pass = pass;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }
    public int getNif() {
        return nif;
    }
    public String getMorada() {
        return morada;
    }
    public int getTelefone() {
        return telefone;
    }
    public String getEmail() {
        return email;
    }
    public String getPass() {
        return pass;
    }
    public int getTipo() {
        return tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setNif(int nif) {
        this.nif = nif;
    }
    public void setMorada(String morada) {
        this.morada = morada;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public boolean optionsCheck(String str, int min, int max) {
        try {
            int input = Integer.parseInt(str);
            return !(input < min || input > max);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean intCheck(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch(NumberFormatException e) {
            // System.out.println("Invalido!");
            return false;
        }
        return true;
    }
    public boolean minCheck(String str, int min) {
        try {
            double d = Double.parseDouble(str);
            return !(d < 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean checkNif(int nif, ArrayList<Utilizador> clientes) {
        for(Utilizador cliente : clientes)
            if(cliente.getNif() == nif)
                return true;
        return false;
    }
    public boolean checkEmail(String email, ArrayList<Utilizador> clientes) {
        for(Utilizador cliente : clientes)
            if(cliente.getEmail().equalsIgnoreCase(email))
                return true;
        return false;
    }
    public boolean checkCodigoViagem(int codigo, ArrayList<Viagem> viagens) {
        for(Viagem viagem : viagens)
            if(viagem.getCodigo() == codigo)
                return true;
        return false;
    }
    public boolean checkMatricula(String matricula, ArrayList<Autocarro> autocarros) {
        for(Autocarro auto : autocarros)
            if(auto.getMatricula().equalsIgnoreCase(matricula))
                return true;
        return false;
    }



    @Override
    public String toString(){
        return "Informação do cliente:\nnome:" +nome+ "\n" +
                "NIF: " +nif+ "\n" +
                "Morada: " +morada+ "\n" +
                "Contacto: " +telefone+ "\n" +
                "Email: " +email+ "\n" +
                "Password: " +pass+ "\n" +
                "Tipo: " +tipo+ "\n\t";
    }
}
