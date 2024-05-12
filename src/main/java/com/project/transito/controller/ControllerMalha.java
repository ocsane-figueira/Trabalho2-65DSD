package com.project.transito.controller;

import com.project.transito.model.Malha;
import com.project.transito.model.Nodo;
import javax.swing.JTextArea;

public class ControllerMalha extends Thread {

    private boolean emExecucao;
    private JTextArea textArea;
    private Malha malhaViaria;

    public ControllerMalha(JTextArea textArea, Malha malhaViaria) {
        this.emExecucao = true;
        this.textArea = textArea;
        this.malhaViaria = malhaViaria;
    }

    public void paraExecucao() {
        this.emExecucao = false;
    }

    @Override
    public void run() {
        String malhaPrint = "";
        Nodo[][] malhaVi = this.malhaViaria.getNodos();
        while (this.emExecucao) {
            malhaPrint = "";
            for (int i = 0; i < malhaVi.length; i++) {
                for (int j = 0; j < malhaVi[i].length; j++) {
                    Nodo nodo = malhaVi[i][j];
                    if (nodo == null) {
                        malhaPrint += "  ";
                    }
                    else {
                        malhaPrint += (nodo.getCarro() != null ? nodo.getCarro().getNome() : nodo.getDirecao().getCaracterExibicao());
                        malhaPrint += " ";
                    }

                }
                malhaPrint += "\n";
            }

            this.textArea.setText(malhaPrint);
            try {
                this.sleep(50);
            }
            catch (InterruptedException ex) {
            }
        }
    }

}
