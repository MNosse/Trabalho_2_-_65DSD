package model;

import javax.swing.*;

public class Carro {

    private int linha;
    private int coluna;

    //Se aplicável
    private ImageIcon carro;

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public ImageIcon getCarro() {
        return carro;
    }

    public void setCarro(ImageIcon carro) {
        this.carro = carro;
    }
}
