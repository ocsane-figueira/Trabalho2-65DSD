package com.project.transito.controller;

import com.project.transito.model.Carro;
import com.project.transito.model.CarroMonitor;
import com.project.transito.model.CarroSemaforo;
import com.project.transito.model.Direcao;
import com.project.transito.model.Malha;
import com.project.transito.model.Nodo;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class ControllerCarros extends Thread {

    private boolean emExecucao;
    private Malha malha;
    private int tempoMiliseg;
    private ArrayList<Nodo> listNodosEntrada;
    private int qtdTotalCarros;
    private Semaphore semaforoMaster;
    private boolean usaSemaforo;

    public ControllerCarros(Malha malha, boolean usaSemaforo) {
        this.emExecucao = true;
        this.malha = malha;
        this.tempoMiliseg = 500;
        this.listNodosEntrada = new ArrayList<Nodo>();
        this.initListNodosEntrada();
        this.semaforoMaster = new Semaphore(1);
        this.usaSemaforo = usaSemaforo;
    }

    public void setTempoMiliseg(int tempoMiliseg) {
        this.tempoMiliseg = tempoMiliseg;
    }

    private void initListNodosEntrada() {
        this.setNodosEntradaDireitaEsquerda();
        this.setNodosEntradaTopoBaixo();
    }

    public void paraExcucao() {
        this.emExecucao = false;
    }

    private void setNodosEntradaTopoBaixo() {
        Nodo[][] matrizNodos = this.malha.getNodos();
        for (int idxColuna = 0; idxColuna < matrizNodos[0].length; idxColuna++) {
            Nodo nodoAtualTopo = matrizNodos[0][idxColuna];
            Nodo nodoAtualBaixo = matrizNodos[matrizNodos.length - 1][idxColuna];
            this.identificaNodoEntrada(nodoAtualTopo, Direcao.BAIXO);
            this.identificaNodoEntrada(nodoAtualBaixo, Direcao.CIMA);
        }
    }

    private void setNodosEntradaDireitaEsquerda() {
        Nodo[][] matrizNodos = this.malha.getNodos();
        int colunaBordaDireita = (matrizNodos[0].length - 1);
        for (int idxLinha = 0; idxLinha < matrizNodos.length; idxLinha++) {
            Nodo nodoAtualDireita = matrizNodos[idxLinha][colunaBordaDireita];
            Nodo nodoAtualEsquerda = matrizNodos[idxLinha][0];
            this.identificaNodoEntrada(nodoAtualDireita, Direcao.ESQUERDA);
            this.identificaNodoEntrada(nodoAtualEsquerda, Direcao.DIREITA);
        }
    }

    private void identificaNodoEntrada(Nodo nodo, Direcao direcao) {
        if (nodo == null) {
            return;
        }
        if (nodo.getDirecao() == direcao) {
            this.listNodosEntrada.add(nodo);
        }
    }

    private String getIdCarro(int numChar) {
        char id = (char) (65 + numChar);
        return id + "";
    }

    public void setQtdTotalCarros(int qtdTotalCarros) {
        this.qtdTotalCarros = qtdTotalCarros;
    }
    
    @Override
    public void run() {
        Random ran = new Random();
        boolean validaQtdCarros = this.qtdTotalCarros > 0;
        int contCarros = 0;
        int idxListNodosEntrada = 0;
        Carro carro;
        Nodo nodo;

        while (this.emExecucao) {
            
            if (idxListNodosEntrada > this.listNodosEntrada.size() -1) {
                idxListNodosEntrada = 0;
            }
            
            nodo = this.listNodosEntrada.get(idxListNodosEntrada);

            if (nodo.getCarro() != null) {
                idxListNodosEntrada++;
                if (idxListNodosEntrada == this.listNodosEntrada.size() - 1) {
                    idxListNodosEntrada = 0;
                }
                continue;
            }
            if (usaSemaforo) {
                carro = new CarroSemaforo(this.getIdCarro(ran.nextInt(26)), this.semaforoMaster);                
            }
            else {
                carro = new CarroMonitor(this.getIdCarro(ran.nextInt(26)));                
            }
            nodo.setCarro(carro);
            carro.setNodoAtual(nodo);
            carro.start();

            if (validaQtdCarros) {
                contCarros++;
                if (contCarros >= this.qtdTotalCarros) {
                    this.emExecucao = false;
                }
            }
            if (idxListNodosEntrada == this.listNodosEntrada.size() - 1) {
                idxListNodosEntrada = 0;
            }
            else {
                idxListNodosEntrada++;
            }

            try {
                this.sleep(this.tempoMiliseg);
            }
            catch (InterruptedException ex) {}
        }

    }

}
