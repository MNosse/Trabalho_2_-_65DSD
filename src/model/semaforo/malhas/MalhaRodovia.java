package model.semaforo.malhas;

import model.semaforo.observer.ObserverMalhaRodovia;
import model.semaforo.strategy.StrategyMovimentaCarro;
import model.thread.Carro;

import java.util.concurrent.Semaphore;

public class MalhaRodovia{

    protected Semaphore mutex = new Semaphore(1);
    private ObserverMalhaRodovia observer;
    private StrategyMovimentaCarro strategyMovimentaCarro;
    private int linha;
    private int coluna;
    
    public MalhaRodovia(ObserverMalhaRodovia observer, int linha, int coluna) {
        this.observer = observer;
        this.linha = linha;
        this.coluna = coluna;
    }

    public void movimentarCarro(Carro carro) {
        strategyMovimentaCarro.movimentarCarro(carro);
    }
    
    public MalhaRodovia getProximaMalhaRodovia(Carro carro) {
        return strategyMovimentaCarro.getProximaMalhaRodovia(carro);
    }

    public Semaphore getMutex() {
        return mutex;
    }
    
    public ObserverMalhaRodovia getObserver() {
        return observer;
    }
    
    public int getLinha() {
        return linha;
    }
    
    public int getColuna() {
        return coluna;
    }
    
    public StrategyMovimentaCarro getStrategyMovimentaCarro() {
        return strategyMovimentaCarro;
    }
    
    public void setStrategyMovimentaCarro(StrategyMovimentaCarro strategyMovimentaCarro) {
        this.strategyMovimentaCarro = strategyMovimentaCarro;
    }
}
