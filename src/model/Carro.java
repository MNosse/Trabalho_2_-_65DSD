package model;

public class Carro {

    private int linha;
    private int coluna;

    private String nomeCarro;

    public Carro(int linha, int coluna, String nomeCarro) {
        this.linha = linha;
        this.coluna = coluna;
        this.nomeCarro= nomeCarro;
    }

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

    public String getNomeCarro() {
        return nomeCarro;
    }

    public void setNomeCarro(String nomeCarro) {
        this.nomeCarro = nomeCarro;
    }
}
