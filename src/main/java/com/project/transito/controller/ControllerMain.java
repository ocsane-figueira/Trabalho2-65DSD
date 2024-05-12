
package com.project.transito.controller;

import com.project.transito.model.ConversorMatrizMalhaViaria;
import com.project.transito.model.LeitorMalha;
import com.project.transito.model.Malha;
import com.project.transito.model.Nodo;
import com.project.transito.view.Tela;
import java.io.IOException;

public class ControllerMain {

    private Tela view;
    private Malha malhaViaria;
    private ControllerCarros adicionadorCarros;
    private ControllerMalha desenhadorMalha;

    public void setView(Tela view) {
        this.view = view;
    }

    public void CriaMalhaViaria(String caminho) {
        LeitorMalha leitor = new LeitorMalha(caminho);
        int[][] malhaBase = null;

        try {
            malhaBase = leitor.lerMalha();
        }
        catch (IOException ex) {
        }

        ConversorMatrizMalhaViaria conversor = new ConversorMatrizMalhaViaria(malhaBase);
        this.malhaViaria = conversor.getMatrizConvertidaParaMalha();
    }
        

    public void inciarSimulacaoMonitor(int tempoMiliseg, int qtdCarros) {
        ControllerCarros addCar = new ControllerCarros(this.malhaViaria, false);
        addCar.setTempoMiliseg(tempoMiliseg);
        addCar.setQtdTotalCarros(qtdCarros);
        this.adicionadorCarros = addCar;
        addCar.start();
        
        ControllerMalha desenhadorMalha = new ControllerMalha(this.view.getTextArea(), this.malhaViaria);
        this.desenhadorMalha = desenhadorMalha;
        desenhadorMalha.start();
    } 
    
    public void inciarSimulacaoSemaforo(int tempoMiliseg, int qtdCarros) {
        ControllerCarros addCar = new ControllerCarros(this.malhaViaria, true);
        addCar.setTempoMiliseg(tempoMiliseg);
        addCar.setQtdTotalCarros(qtdCarros);
        this.adicionadorCarros = addCar;
        addCar.start();
        
        ControllerMalha desenhadorMalha = new ControllerMalha(this.view.getTextArea(), this.malhaViaria);
        this.desenhadorMalha = desenhadorMalha;
        desenhadorMalha.start();
    }   
    
    public void pararSimulacao() {
        this.adicionadorCarros.paraExcucao();
    }
    
    public void pararSimulacaoAgora() {
        this.adicionadorCarros.paraExcucao();
        this.desenhadorMalha.paraExecucao();
        this.malhaViaria.paraExecucao();
    }
}
