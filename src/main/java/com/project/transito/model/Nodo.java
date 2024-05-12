
package com.project.transito.model;

public class Nodo {
    
    private Carro carro;
    private Carro reserva;
    private Malha malhaViaria;
    private Direcao direcao;
    private boolean nodoDeSaida;
    private int idxLinha;
    private int idxColuna;

    public Nodo(Direcao direcao, int idxLinha, int idxColuna, Malha malhaViaria) {
        this.direcao = direcao;
        this.nodoDeSaida = false;
        this.idxLinha = idxLinha;
        this.idxColuna = idxColuna;
        this.malhaViaria = malhaViaria;
    }
    
    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    public void setDirecao(Direcao direcao) {
        this.direcao = direcao;
    }

    public boolean isNodoDeSaida() {
        return nodoDeSaida;
    }

    public void setNodoDeSaida(boolean nodoDeSaida) {
        this.nodoDeSaida = nodoDeSaida;
    }

    public int getIdxLinha() {
        return idxLinha;
    }

    public void setIdxLinha(int idxLinha) {
        this.idxLinha = idxLinha;
    }

    public int getIdxColuna() {
        return idxColuna;
    }

    public void setIdxColuna(int idxColuna) {
        this.idxColuna = idxColuna;
    }

    public Malha getMalhaViaria() {
        return malhaViaria;
    }

    public Carro getReserva() {
        return reserva;
    }

    public void setReserva(Carro reserva) {
        this.reserva = reserva;
    }

    public boolean isCruzamento() {
        return this.direcao == Direcao.CRUZAMENTO_CIMA           ||
               this.direcao == Direcao.CRUZAMENTO_BAIXO          ||
               this.direcao == Direcao.CRUZAMENTO_DIREITA        ||
               this.direcao == Direcao.CRUZAMENTO_ESQUERDA       ||
               this.direcao == Direcao.CRUZAMENTO_BAIXO_DIREITA  ||
               this.direcao == Direcao.CRUZAMENTO_BAIXO_ESQUERDA ||
               this.direcao == Direcao.CRUZAMENTO_CIMA_DIRETIA   ||
               this.direcao == Direcao.CRUZAMENTO_CIMA_ESQUERDA;
    }
}
