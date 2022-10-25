package model.strategy;

import model.malhas.AbstractMalhaRodovia;
import model.thread.Carro;

public abstract class StrategyMovimentaCarro {
    protected AbstractMalhaRodovia malhaRodovia;
    
    public StrategyMovimentaCarro(AbstractMalhaRodovia malhaRodovia) {
        this.malhaRodovia = malhaRodovia;
    }
    
    public abstract void movimentarCarro(Carro carro);
    
    public abstract void movimentarCarroSimples(Carro carro) throws InterruptedException;
    
    public abstract AbstractMalhaRodovia getProximaMalhaRodovia(Carro carro);
}
