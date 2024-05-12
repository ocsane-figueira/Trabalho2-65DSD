
package com.project.transito.model;

public abstract class Carro extends Thread {
    public abstract String getNome();
    public abstract void setNodoAtual(Nodo nodo);
}
