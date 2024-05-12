package com.project.transito.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeitorMalha {

    private int malha[][];
    private String diretorio;

    public LeitorMalha(String diretorio) {
        this.diretorio = diretorio;
    }
    
    public int[][] lerMalha() throws IOException {
        BufferedReader buffRead = this.criaBuffer();
        if (buffRead == null) {
            return null;
        }
        
        this.iniciaMalha(buffRead);
        this.preencheMalha(buffRead);
     
        buffRead.close();
        return this.malha;
    }
   
    private void preencheMalha(BufferedReader buffRead) throws IOException {
        String linha = buffRead.readLine();
        int idxLinha = 0;
            
        while(linha != null) {
            String[] numerosMalha = linha.split("\t");
            
            this.preencheLinhaMalha(idxLinha, this.arrayLinhaStringToInt(numerosMalha));
            idxLinha++;
            
            linha = buffRead.readLine();      
        }
    }
    
    private int[] arrayLinhaStringToInt(String[] linha) {
        int[] linhaInt = new int[linha.length];
        for (int idx = 0; idx < linha.length; idx++) {
            linhaInt[idx] = Integer.parseInt(linha[idx]);
        }
        return linhaInt;
    }
    
    private void preencheLinhaMalha(int idxLinha, int[] linha) {
        for (int idxCol = 0; idxCol < this.malha[idxLinha].length; idxCol++) {
            this.malha[idxLinha][idxCol] = linha[idxCol];
        }
    }
    
    private void iniciaMalha(BufferedReader buffRead) throws IOException {
        String lnQtdLinhas = buffRead.readLine().trim();
        String lnQtdColunas = buffRead.readLine().trim();
        
        int qtdLinhas = Integer.parseInt(lnQtdLinhas);
        int qtdColunas = Integer.parseInt(lnQtdColunas);
        
        this.malha = new int[qtdLinhas][qtdColunas];
    }
    
    private BufferedReader criaBuffer() {
       BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(new FileReader(this.diretorio)); 
        }
        catch (FileNotFoundException ex) { }
        return buffRead;
    }

}
