package model.strategy;

import model.malhas.AbstractCruzamento;
import model.malhas.AbstractMalhaRodovia;
import model.malhas.AbstractMalhaRodoviaCruzamento;
import model.thread.Carro;
import singleton.RepositorioMalha;

public class StrategyMovimentaCarroDireita extends StrategyMovimentaCarro{
    public StrategyMovimentaCarroDireita(AbstractMalhaRodovia malhaRodovia) {
        super(malhaRodovia);
    }
    
    @Override
    public void movimentarCarro(Carro carro) {
        AbstractMalhaRodovia proximaMalha = null;
        try {
            if(carro.getPrimeiraIteracao()) {
                malhaRodovia.bloquear();
                malhaRodovia.getObserver().notificarInicioCarro(carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
                carro.desativarPrimeiraIteracao();
                carro.dormir();
            }
            proximaMalha = getProximaMalhaRodovia(carro);
            if(proximaMalha != null) {
                if(proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                    for(AbstractCruzamento cruzamento : RepositorioMalha.getInstance().getCruzamentos()) {
                        if(cruzamento.getMalhasCruzamento().contains((AbstractMalhaRodoviaCruzamento) proximaMalha)) {
                            cruzamento.movimentarCarro(carro, malhaRodovia);
                            break;
                        }
                    }
                } else {
                    if(proximaMalha.tryBloquear()) {
                        carro.setMalhaRodovia(proximaMalha);
                        carro.setColuna(carro.getColuna() + 1);
                        malhaRodovia.getObserver().notificarMovimentoCarro(carro.getLinha(), carro.getColuna() - 1, carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
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
                carro.setColuna(carro.getColuna() + 1);
                malhaRodovia.getObserver().notificarMovimentoCarro(carro.getLinha(), carro.getColuna() - 1, carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
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
            throw new InterruptedException();
        }
    }
    
    @Override
    public AbstractMalhaRodovia getProximaMalhaRodovia(Carro carro) {
        int novaColuna = carro.getColuna()+1;
        AbstractMalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        if(novaColuna < malhaRodovias[0].length && malhaRodovias[carro.getLinha()][novaColuna] != null)
            return malhaRodovias[carro.getLinha()][novaColuna];
        else{
            return null;
        }
    }
}
