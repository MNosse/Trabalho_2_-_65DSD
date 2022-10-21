package model.semaforo.strategy;

import model.semaforo.malhas.MalhaRodovia;
import model.thread.Carro;

public abstract class StrategyMovimentaCarro {
    protected MalhaRodovia malhaRodovia;
    
    public StrategyMovimentaCarro(MalhaRodovia malhaRodovia) {
        this.malhaRodovia = malhaRodovia;
    }
    
    public abstract void movimentarCarro(Carro carro);
    
    public abstract MalhaRodovia getProximaMalhaRodovia(Carro carro);
}
