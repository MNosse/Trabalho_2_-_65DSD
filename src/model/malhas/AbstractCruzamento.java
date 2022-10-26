package model.malhas;

import model.thread.Carro;

import java.util.List;

public abstract class AbstractCruzamento {
    private List<AbstractMalhaRodoviaCruzamento> malhasCruzamento;
    protected List<AbstractMalhaRodovia> saidas;
    protected AbstractMalhaRodovia saida;
    
    public AbstractCruzamento(List<AbstractMalhaRodoviaCruzamento> malhasCruzamento) {
        this.malhasCruzamento = malhasCruzamento;
        
    }
    
    public List<AbstractMalhaRodoviaCruzamento> getMalhasCruzamento() {
        return malhasCruzamento;
    }
    
    public abstract void movimentarCarro(Carro carro);
}
