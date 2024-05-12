
package com.project.transito.model;

public class Malha {
    
    private Nodo[][] nodos;
    private boolean emExecucao;

    public Malha() {
        this.emExecucao = true;
    }
    
    public void paraExecucao() {        
        this.emExecucao = false;
    }
    
    public boolean estaEmExecucao() {
        return this.emExecucao;
    }

    public Nodo getNodo(int idxLinha, int idxColuna) {
        return this.nodos[idxLinha][idxColuna];
    }
    
    public void setNodo(int idxLinha, int idxColuna, Nodo nodo) {
        this.nodos[idxLinha][idxColuna] = nodo;
    }

    public void setNodos(Nodo[][] nodos) {
        this.nodos = nodos;
    }

    public Nodo[][] getNodos() {
        return nodos;
    }

}
