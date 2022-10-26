package model.thread;

import model.malhas.AbstractMalhaRodovia;

import java.util.Random;

public class Carro extends Thread {
    
    private boolean interrupted;
    private boolean primeiraIteracao;
    private int linha;
    private int coluna;
    private String nomeCarro;
    private int r;
    private int g;
    private int b;
    private int tempoSleep;
    private AbstractMalhaRodovia malhaRodovia;

    public Carro(int linha, int coluna, String nomeCarro, AbstractMalhaRodovia malhaRodovia) {
        interrupted = false;
        primeiraIteracao = true;
        this.linha = linha;
        this.coluna = coluna;
        this.nomeCarro= nomeCarro;
        tempoSleep = new Random().nextInt(2001 - 500) + 500;
//        tempoSleep = 500;
        r = new Random().nextInt(256);
        g = new Random().nextInt(256);
        b = new Random().nextInt(256);
        this.malhaRodovia = malhaRodovia;
    }
    
    @Override
    public void run() {
        try {
            while(!isInterrupted() && !interrupted) {
                if(primeiraIteracao) {
                    if(malhaRodovia.tryBloquear()) {
                        malhaRodovia.getObserver().notificarInicioCarro(linha, coluna, r, g, b);
                        desativarPrimeiraIteracao();
                        dormir();
                    }
                } else {
                    malhaRodovia.movimentarCarro(this);
                }
            }
            malhaRodovia.getObserver().notificarFimCarro(getLinha(), getColuna(), this);
            Thread.currentThread().interrupt();
        } catch(InterruptedException e) {
            malhaRodovia.getObserver().notificarFimCarro(getLinha(), getColuna(), this);
            Thread.currentThread().interrupt();
        }
    }
    
    public void setInterruptedTrue() {
        interrupted = true;
    }
    
    public void dormir() throws InterruptedException {
        sleep(tempoSleep);
    }
    
    public void desativarPrimeiraIteracao() {
        primeiraIteracao = false;
    }
    
    public boolean getPrimeiraIteracao() {
        return primeiraIteracao;
    }
    
    public boolean getInterrupted() {
        return this.interrupted;
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
    
    public int getR() {
        return r;
    }
    
    public int getG() {
        return g;
    }
    
    public int getB() {
        return b;
    }
    
    public AbstractMalhaRodovia getMalhaRodovia() {
        return malhaRodovia;
    }
    
    public void setMalhaRodovia(AbstractMalhaRodovia malhaRodovia) {
        this.malhaRodovia = malhaRodovia;
    }
}
