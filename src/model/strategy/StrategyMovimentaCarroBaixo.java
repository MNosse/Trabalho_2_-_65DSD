package model.strategy;

import model.malhas.AbstractCruzamento;
import model.malhas.AbstractMalhaRodovia;
import model.malhas.AbstractMalhaRodoviaCruzamento;
import model.thread.Carro;
import singleton.RepositorioMalha;

public class StrategyMovimentaCarroBaixo extends StrategyMovimentaCarro{
    public StrategyMovimentaCarroBaixo(AbstractMalhaRodovia malhaRodovia) {
        super(malhaRodovia);
    }
    
    @Override
    public void movimentarCarro(Carro carro) {
        AbstractMalhaRodovia proximaMalha = null;
        try {
            proximaMalha = getProximaMalhaRodovia(carro);
            if(proximaMalha != null) {
                if(proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                    for(AbstractCruzamento cruzamento : RepositorioMalha.getInstance().getCruzamentos()) {
                        if(cruzamento.getMalhasCruzamento().contains((AbstractMalhaRodoviaCruzamento) proximaMalha)) {
                            cruzamento.movimentarCarro(carro);
                            break;
                        }
                    }
                } else {
                    if(proximaMalha.tryBloquear()) {
                        carro.setMalhaRodovia(proximaMalha);
                        carro.setLinha(carro.getLinha() + 1);
                        malhaRodovia.getObserver().notificarMovimentoCarro(carro.getLinha() - 1, carro.getColuna(), carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
                        malhaRodovia.liberar();
                        carro.dormir();
                    } else {
                        carro.dormir();
                    }
                }
            } else {
                malhaRodovia.liberar();
                carro.setInterruptedTrue();
            }
        } catch(InterruptedException e) {
            malhaRodovia.liberar();
            if (proximaMalha != null) {
                proximaMalha.liberar();
            }
            carro.setInterruptedTrue();
        }
    }
    
    @Override
    public void movimentarCarroSimples(Carro carro) throws InterruptedException {
        AbstractMalhaRodovia proximaMalha = null;
        try {
            proximaMalha = getProximaMalhaRodovia(carro);
            if(proximaMalha.tryBloquear()) {
                carro.setMalhaRodovia(proximaMalha);
                carro.setLinha(carro.getLinha() + 1);
                malhaRodovia.getObserver().notificarMovimentoCarro(carro.getLinha() - 1, carro.getColuna(), carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
                malhaRodovia.liberar();
                carro.dormir();
            } else {
                carro.dormir();
            }
        } catch(InterruptedException e) {
            malhaRodovia.liberar();
            if (proximaMalha != null) {
                proximaMalha.liberar();
            }
            carro.setInterruptedTrue();
            throw  new InterruptedException();
        }
    }
    
    @Override
    public AbstractMalhaRodovia getProximaMalhaRodovia(Carro carro) {
        int novaLinha = carro.getLinha()+1;
        AbstractMalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        if(novaLinha < malhaRodovias.length && malhaRodovias[novaLinha][carro.getColuna()] != null)
            return malhaRodovias[novaLinha][carro.getColuna()];
        else{
            return null;
        }
    }
}
