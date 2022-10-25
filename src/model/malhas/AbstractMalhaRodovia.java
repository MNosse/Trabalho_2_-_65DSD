package model.malhas;

import model.observer.ObserverMalhaRodovia;
import model.strategy.StrategyMovimentaCarro;
import model.thread.Carro;

public abstract class AbstractMalhaRodovia {
    private ObserverMalhaRodovia observer;
    protected StrategyMovimentaCarro strategy;
    private final int LINHA;
    private final int COLUNA;
    
    public AbstractMalhaRodovia(ObserverMalhaRodovia observer, int linha, int coluna) {
        this.observer = observer;
        LINHA = linha;
        COLUNA = coluna;
    }
    
    public ObserverMalhaRodovia getObserver() {
        return observer;
    }
    
    public void setStrategy(StrategyMovimentaCarro strategy) {
        this.strategy = strategy;
    }
    
    public int getLINHA() {
        return LINHA;
    }
    
    public int getCOLUNA() {
        return COLUNA;
    }
    
    public abstract void movimentarCarro(Carro carro);
    public abstract void movimentarCarroSimples(Carro carro) throws InterruptedException;
    public abstract AbstractMalhaRodovia getProximaMalhaRodovia(Carro carro);
    public abstract boolean tryBloquear() throws InterruptedException;
    public abstract void bloquear() throws InterruptedException;
    public abstract void liberar();
}
