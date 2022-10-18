package model.semaforo.malhas;

import model.semaforo.observer.ObserverMalhaRodovia;

public class MalhaRodoviaCruzamento extends MalhaRodovia{
    private boolean podeMoverBaixo;
    private boolean podeMoverCima;
    private boolean podeMoverDireita;
    private boolean podeMoverEsquerda;
    
    public MalhaRodoviaCruzamento(ObserverMalhaRodovia observer, int linha, int coluna) {
        super(observer, linha, coluna);
        podeMoverBaixo = false;
        podeMoverCima = false;
        podeMoverDireita = false;
        podeMoverEsquerda = false;
    }
    
    public boolean getPodeMoverBaixo() {
        return podeMoverBaixo;
    }
    
    public void setPodeMoverBaixo(boolean podeMoverBaixo) {
        this.podeMoverBaixo = podeMoverBaixo;
    }
    
    public boolean getPodeMoverCima() {
        return podeMoverCima;
    }
    
    public void setPodeMoverCima(boolean podeMoverCima) {
        this.podeMoverCima = podeMoverCima;
    }
    
    public boolean getPodeMoverDireita() {
        return podeMoverDireita;
    }
    
    public void setPodeMoverDireita(boolean podeMoverDireita) {
        this.podeMoverDireita = podeMoverDireita;
    }
    
    public boolean getPodeMoverEsquerda() {
        return podeMoverEsquerda;
    }
    
    public void setPodeMoverEsquerda(boolean podeMoverEsquerda) {
        this.podeMoverEsquerda = podeMoverEsquerda;
    }
}
