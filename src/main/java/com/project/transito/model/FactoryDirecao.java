
package com.project.transito.model;

public class FactoryDirecao {

    
    public static Direcao getDirecao(int numDir) {
        Direcao retorno = null;
        switch(numDir) {
            case 1:
               retorno = Direcao.CIMA;
               break;
            case 2:
                retorno = Direcao.DIREITA;
               break;
            case 3:
                retorno = Direcao.BAIXO;
               break;
            case 4:
                retorno = Direcao.ESQUERDA;
               break;
            case 5:
                retorno = Direcao.CRUZAMENTO_CIMA;
               break;
            case 6:
                retorno = Direcao.CRUZAMENTO_DIREITA;
               break;
            case 7:
                retorno = Direcao.CRUZAMENTO_BAIXO;
               break;
            case 8:
                retorno = Direcao.CRUZAMENTO_ESQUERDA;
               break;
            case 9:
                retorno = Direcao.CRUZAMENTO_CIMA_DIRETIA;
               break;
            case 10:
                retorno = Direcao.CRUZAMENTO_CIMA_ESQUERDA;
               break;
            case 11:
                retorno = Direcao.CRUZAMENTO_BAIXO_DIREITA;
               break;
            case 12:
                retorno = Direcao.CRUZAMENTO_BAIXO_ESQUERDA;
               break;
        }
        return retorno;
    }
}
