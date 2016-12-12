/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetopoo;
import java.util.*;

/**
 *
 * @author Gabriel
 */
public abstract class Reserva {
    protected Viagem v;
    protected int lugar;
    protected double valor;
    protected Date data;

    /**
     * Cria reserva para uma dada viagem com lugar escolhido e valor da reserva
     * @param lugar lugar escolhido ou 0 se reserva cancelada ou espera
     * @param valor atualiza o lucro da viagem
     */
    Reserva(Viagem v, int lugar, double valor) {
        /*ao criar a reserva o cliente vai introduzir valor * % do valor a pagar*/
        /*exemplo (c,v,lugar,valor*0.5)*/
        /*adiciona automaticamente as reservas da viagem*/
        this.v = v;
        this.lugar = lugar;
        this.valor = valor;
        v.setLucro(v.getLucro() + valor);
    }

    public Viagem getV() {
        return v;
    }
    public void setV(Viagem v) {
        this.v = v;
    }

    public int getLugar() {
        return lugar;
    }
    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString(){
        return v +" lugar " +lugar+ " custo: " +valor;
    }
}
