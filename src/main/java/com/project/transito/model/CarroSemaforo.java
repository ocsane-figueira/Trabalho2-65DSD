package com.project.transito.model;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarroSemaforo extends Carro {

    private String nome;
    private Nodo nodoAtual;
    private boolean emCruzamento;
    private Nodo[] caminhoCruzamento;
    private Semaphore semaforo;

    public CarroSemaforo(String nome, Semaphore semaforo) {
        this.nome = nome;
        this.emCruzamento = false;
        this.caminhoCruzamento = new Nodo[4];
        this.semaforo = semaforo;
    }

    public Nodo getNodoAtual() {
        return nodoAtual;
    }

    public void setNodoAtual(Nodo nodoAtual) {
        this.nodoAtual = nodoAtual;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private void andar(Nodo nodoDestino) {
        try {
            this.semaforo.acquire();
        }
        catch (InterruptedException ex) {}
        if (nodoDestino.getCarro() != null) {
            this.semaforo.release();
            return;
        }
        this.nodoAtual.setCarro(null);
        this.nodoAtual = nodoDestino;
        this.nodoAtual.setCarro(this);
        
        this.semaforo.release();
    }

    private Nodo getProxNodoCima() {
        Malha malhaViaria = this.nodoAtual.getMalhaViaria();
        Nodo proximoNodo = malhaViaria.getNodo(this.nodoAtual.getIdxLinha() - 1, this.nodoAtual.getIdxColuna());
        return proximoNodo;

    }

    private Nodo getProxNodoDireita() {
        Malha malhaViaria = this.nodoAtual.getMalhaViaria();
        Nodo proximoNodo = malhaViaria.getNodo(this.nodoAtual.getIdxLinha(), this.nodoAtual.getIdxColuna() + 1);
        return proximoNodo;
    }

    private Nodo getProxNodoBaixo() {
        Malha malhaViaria = this.nodoAtual.getMalhaViaria();
        Nodo proximoNodo = malhaViaria.getNodo(this.nodoAtual.getIdxLinha() + 1, this.nodoAtual.getIdxColuna());
        return proximoNodo;
    }

    private Nodo getProxNodoEsquerda() {
        Malha malhaViaria = this.nodoAtual.getMalhaViaria();
        Nodo proximoNodo = malhaViaria.getNodo(this.nodoAtual.getIdxLinha(), this.nodoAtual.getIdxColuna() - 1);
        return proximoNodo;
    }

    private Nodo getProxNodoCima(Nodo nodoReferencia) {
        Malha malhaViaria = nodoReferencia.getMalhaViaria();
        Nodo proximoNodo = malhaViaria.getNodo(nodoReferencia.getIdxLinha() - 1, nodoReferencia.getIdxColuna());
        return proximoNodo;

    }

    private Nodo getProxNodoDireita(Nodo nodoReferencia) {
        Malha malhaViaria = nodoReferencia.getMalhaViaria();
        Nodo proximoNodo = malhaViaria.getNodo(nodoReferencia.getIdxLinha(), nodoReferencia.getIdxColuna() + 1);
        return proximoNodo;
    }

    private Nodo getProxNodoBaixo(Nodo nodoReferencia) {
        Malha malhaViaria = nodoReferencia.getMalhaViaria();
        Nodo proximoNodo = malhaViaria.getNodo(nodoReferencia.getIdxLinha() + 1, nodoReferencia.getIdxColuna());
        return proximoNodo;
    }

    private Nodo getProxNodoEsquerda(Nodo nodoReferencia) {
        Malha malhaViaria = nodoReferencia.getMalhaViaria();
        Nodo proximoNodo = malhaViaria.getNodo(nodoReferencia.getIdxLinha(), nodoReferencia.getIdxColuna() - 1);
        return proximoNodo;
    }

    private void SairMalhaViaria() {
        this.nodoAtual.setCarro(null);
    }

    private Nodo getProxNodoNormal() {
        Nodo proximoNodo = null;
        Nodo a = null;
        Nodo b = null;
        switch (this.nodoAtual.getDirecao()) {
            case CIMA:
                proximoNodo = this.getProxNodoCima();
                break;
            case DIREITA:
                proximoNodo = this.getProxNodoDireita();
                break;
            case BAIXO:
                proximoNodo = this.getProxNodoBaixo();
                break;
            case ESQUERDA:
                proximoNodo = this.getProxNodoEsquerda();
                break;
            case CRUZAMENTO_CIMA:
                proximoNodo = this.getProxNodoCima();
                break;
            case CRUZAMENTO_DIREITA:
                proximoNodo = this.getProxNodoDireita();
                break;
            case CRUZAMENTO_BAIXO:
                proximoNodo = this.getProxNodoBaixo();
                break;
            case CRUZAMENTO_ESQUERDA:
                proximoNodo = this.getProxNodoEsquerda();
                break;
            case CRUZAMENTO_BAIXO_DIREITA:
                a = this.getProxNodoBaixo();
                b = this.getProxNodoDireita();

                proximoNodo = a.isCruzamento() ? b : a;

                break;
            case CRUZAMENTO_BAIXO_ESQUERDA:
                a = this.getProxNodoBaixo();
                b = this.getProxNodoEsquerda();

                proximoNodo = a.isCruzamento() ? b : a;

                break;
            case CRUZAMENTO_CIMA_DIRETIA:
                a = this.getProxNodoCima();
                b = this.getProxNodoDireita();

                proximoNodo = a.isCruzamento() ? b : a;

                break;
            case CRUZAMENTO_CIMA_ESQUERDA:
                a = this.getProxNodoCima();
                b = this.getProxNodoEsquerda();

                proximoNodo = a.isCruzamento() ? b : a;

                break;
        }

        return proximoNodo;
    }

    private Nodo getProxNodoCruzamento(Nodo nodoReferencia, boolean forcaSairCruzamento, Nodo ViagemTemporal, Nodo[] listaInteria, int idxNodoAtual) {
        Nodo proximoNodo = null;
        Nodo a = null;
        Nodo b = null;
        Random rand = new Random();
        boolean par = rand.nextInt(500) % 2 == 0;

        switch (nodoReferencia.getDirecao()) {
            case CRUZAMENTO_CIMA:
                if (forcaSairCruzamento) {
                    proximoNodo = this.getProxNodoCruzamento(ViagemTemporal, true, null, null, 0);
                    listaInteria[idxNodoAtual] = null;
                }
                else {
                    proximoNodo = this.getProxNodoCima(nodoReferencia);
                }
                break;
            case CRUZAMENTO_DIREITA:
                if (forcaSairCruzamento) {
                    proximoNodo = this.getProxNodoCruzamento(ViagemTemporal, true, null, null, 0);
                    listaInteria[idxNodoAtual] = null;
                }
                else {
                    proximoNodo = this.getProxNodoDireita(nodoReferencia);
                }
                break;
            case CRUZAMENTO_BAIXO:
                if (forcaSairCruzamento) {
                    proximoNodo = this.getProxNodoCruzamento(ViagemTemporal, true, null, null, 0);
                    listaInteria[idxNodoAtual] = null;
                }
                else {
                    proximoNodo = this.getProxNodoBaixo(nodoReferencia);
                }
                break;
            case CRUZAMENTO_ESQUERDA:
                if (forcaSairCruzamento) {
                    proximoNodo = this.getProxNodoCruzamento(ViagemTemporal, true, null, null, 0);
                    listaInteria[idxNodoAtual] = null;
                }
                else {
                    proximoNodo = this.getProxNodoEsquerda(nodoReferencia);
                }
                break;
            case CRUZAMENTO_BAIXO_DIREITA:
                a = this.getProxNodoBaixo(nodoReferencia);
                b = this.getProxNodoDireita(nodoReferencia);
                if (forcaSairCruzamento) {
                    proximoNodo = a.isCruzamento() ? b : a;
                }
                else {
                    proximoNodo = par ? a : b;
                }
                break;
            case CRUZAMENTO_BAIXO_ESQUERDA:
                a = this.getProxNodoBaixo(nodoReferencia);
                b = this.getProxNodoEsquerda(nodoReferencia);
                if (forcaSairCruzamento) {
                    proximoNodo = a.isCruzamento() ? b : a;
                }
                else {
                    proximoNodo = par ? a : b;
                }
                break;
            case CRUZAMENTO_CIMA_DIRETIA:
                a = this.getProxNodoCima(nodoReferencia);
                b = this.getProxNodoDireita(nodoReferencia);
                if (forcaSairCruzamento) {
                    proximoNodo = a.isCruzamento() ? b : a;
                }
                else {
                    proximoNodo = par ? a : b;
                }
                break;
            case CRUZAMENTO_CIMA_ESQUERDA:
                a = this.getProxNodoCima(nodoReferencia);
                b = this.getProxNodoEsquerda(nodoReferencia);
                if (forcaSairCruzamento) {
                    proximoNodo = a.isCruzamento() ? b : a;
                }
                else {
                    proximoNodo = par ? a : b;
                }
                break;
        }

        return proximoNodo;
    }

    private boolean temCaminhoDefinido() {
        boolean encontrouCaminho = false;
        for (Nodo nodo : caminhoCruzamento) {
            if (nodo != null) {
                encontrouCaminho = true;
            }
        }
        return encontrouCaminho;
    }

    private boolean pararExecucao() {
        if (!this.nodoAtual.getMalhaViaria().estaEmExecucao()) {
            return true;
        }

        if (this.nodoAtual.isNodoDeSaida()) {
            this.SairMalhaViaria();
            return true;
        }
        return false;
    }

    private void trataAndarNormal(Nodo nodoDestino) {
        if (nodoDestino.isCruzamento()) {
            this.emCruzamento = true;
        }
        else {
            if (nodoDestino.getCarro() == null) {
                this.andar(nodoDestino);
            }
        }
    }

    private void escolherCaminhoCruzamento(Nodo nodoIniCruzamento) {
        this.caminhoCruzamento[0] = nodoIniCruzamento;
        Nodo viagemNoTempo = null;
        for (int i = 1; i <= 3; i++) {
            Nodo prox = this.getProxNodoCruzamento(this.caminhoCruzamento[(i - 1)], (i == 3), viagemNoTempo, this.caminhoCruzamento, (i - 1));
            this.caminhoCruzamento[i] = prox;
            viagemNoTempo = this.caminhoCruzamento[(i - 1)];
            if (!prox.isCruzamento()) {
                break;
            }
        }

        Nodo[] caminhoCruzamentoAux = new Nodo[4];
        int contIdxAtual = 0;
        for (Nodo nodo : caminhoCruzamento) {
            if (nodo != null) {
                caminhoCruzamentoAux[contIdxAtual] = nodo;
                contIdxAtual++;
            }
        }
        this.caminhoCruzamento = caminhoCruzamentoAux;
    }

    private void tentaReservarCaminhoCruzamento() {
        try {
            this.semaforo.acquire();
        }
        catch (InterruptedException ex) {}
        
        boolean livre = true;
        for (Nodo nodo : caminhoCruzamento) {
            if (nodo == null) {
                break;
            }
            if (nodo.getReserva() != null) {
                livre = false;
                break;
            }
        }

        if (!livre) {
            this.semaforo.release();
            return;
        }

        for (Nodo nodo : caminhoCruzamento) {
            if (nodo == null) {
                break;
            }
            nodo.setReserva(this);
        }
        this.semaforo.release();
    }

    private boolean temCaminhoReservado() {
        for (Nodo nodo : caminhoCruzamento) {
            if (nodo != null) {
                if (nodo.getReserva() == this) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    private void trataAndarCruzamento() {
        for (int i = 0; i < this.caminhoCruzamento.length; i++) {
            Nodo nodo = this.caminhoCruzamento[i];
            if (nodo != null) {
                this.andar(nodo);
                nodo.setReserva(null);
                this.caminhoCruzamento[i] = null;
                break;
            }
        }
        boolean andouTudo = true;
        for (int i = 0; i < this.caminhoCruzamento.length; i++) {
            if (this.caminhoCruzamento[i] != null) {
                andouTudo = false;
                break;
            }
        }
        if (andouTudo) {
            this.emCruzamento = false;
        }
    }

    private void esperar() {
        try {
            this.sleep(500);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void run() {
        Nodo proximoNodo = null;
        while (true) {
            if (this.pararExecucao()) {
                break;
            }
            if (this.emCruzamento) {
                if (this.temCaminhoDefinido()) {
                    if (this.temCaminhoReservado()) {
                        this.trataAndarCruzamento();
                    }
                    else {
                        this.tentaReservarCaminhoCruzamento();
                        if (this.temCaminhoReservado()) {
                            this.trataAndarCruzamento();
                        }
                    }
                }
                else {
                    this.escolherCaminhoCruzamento(proximoNodo);
                    this.tentaReservarCaminhoCruzamento();
                    if (this.temCaminhoReservado()) {
                        this.trataAndarCruzamento();
                    }
                }
            }
            else {
                proximoNodo = this.getProxNodoNormal();
                this.trataAndarNormal(proximoNodo);
            }
            this.esperar();
        }
    }

}
