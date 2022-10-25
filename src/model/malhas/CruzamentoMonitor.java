package model.malhas;

import model.strategy.StrategyMovimentaCarroBaixo;
import model.strategy.StrategyMovimentaCarroCima;
import model.strategy.StrategyMovimentaCarroDireita;
import model.strategy.StrategyMovimentaCarroEsquerda;
import model.thread.Carro;
import singleton.RepositorioMalha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CruzamentoMonitor extends AbstractCruzamento {
    
    private Lock lock;
    private AbstractMalhaRodovia saida;
    
    public CruzamentoMonitor(List<AbstractMalhaRodoviaCruzamento> malhasCruzamento) {
        super(malhasCruzamento);
        lock = new ReentrantLock();
        saidas = getSaidas();
    }
    
    @Override
    public synchronized void movimentarCarro(Carro carro, AbstractMalhaRodovia malhaRodovia) {
        try {
            if(lock.tryLock(2, TimeUnit.SECONDS)) {
                int indiceSaida = new Random().nextInt(saidas.size());
                saida = saidas.get(indiceSaida);
                configurarMalhas(carro);
                do {
                    carro.getMalhaRodovia().movimentarCarroSimples(carro);
                } while(!carro.getMalhaRodovia().equals(saida) && !carro.isInterrupted() && !carro.getInterrupted());
                try {
                    lock.unlock();
                } catch(IllegalMonitorStateException e) {
                    //Trata erro do unlock
                }
            }
        } catch(InterruptedException e) {
            try {
                lock.unlock();
            } catch(IllegalMonitorStateException i) {
                //Trata erro do unlock
            }
            carro.setInterruptedTrue();
        }
    }
    
    //RETORNA TODAS AS SAIDAS DO CRUZAMENTO
    private List<AbstractMalhaRodovia> getSaidas() {
        AbstractMalhaRodovia[][] malhasRodovia = RepositorioMalha.getInstance().getMalhaRodovias();
        List<AbstractMalhaRodovia> saidas = new ArrayList<>();
        for (AbstractMalhaRodoviaCruzamento malhaRodoviaCruzamento : getMalhasCruzamento()) {
            if (malhaRodoviaCruzamento.getPodeMoverCima()) {
                AbstractMalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLINHA()-1][malhaRodoviaCruzamento.getCOLUNA()];
                if (!(proximaMalha instanceof AbstractMalhaRodoviaCruzamento)) {
                    saidas.add(proximaMalha);
                }
            }
            if (malhaRodoviaCruzamento.getPodeMoverDireita()) {
                AbstractMalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()+1];
                if (!(proximaMalha instanceof AbstractMalhaRodoviaCruzamento)) {
                    saidas.add(proximaMalha);
                }
            }
            if (malhaRodoviaCruzamento.getPodeMoverBaixo()) {
                AbstractMalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLINHA()+1][malhaRodoviaCruzamento.getCOLUNA()];
                if (!(proximaMalha instanceof AbstractMalhaRodoviaCruzamento)) {
                    saidas.add(proximaMalha);
                }
            }
            if (malhaRodoviaCruzamento.getPodeMoverEsquerda()) {
                AbstractMalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()-1];
                if (!(proximaMalha instanceof AbstractMalhaRodoviaCruzamento)) {
                    saidas.add(proximaMalha);
                }
            }
        }
        return saidas;
    }
    
    //CONFIGURA O CAMINHO ATE A SAIDA COM OS STRATEGIES CORRETOS
    private void configurarMalhas(Carro carro) {
        buscarSaida((AbstractMalhaRodoviaCruzamento) carro.getMalhaRodovia().getProximaMalhaRodovia(carro));
    }
    
    //BUSCA RECURSIVAMENTE A SAIDA
    private boolean buscarSaida(AbstractMalhaRodoviaCruzamento malhaRodoviaCruzamento) {
        AbstractMalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        
        //PROCURA A SAIDA A PARTIR DA MALHA ATUAL
        if (malhaRodoviaCruzamento.getPodeMoverCima()) {
            malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroCima(malhaRodoviaCruzamento));
            AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()-1][malhaRodoviaCruzamento.getCOLUNA()];
            if (proximaMalha.equals(saida)) {
                return true;
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverDireita()) {
            malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroDireita(malhaRodoviaCruzamento));
            AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()+1];
            if (proximaMalha.equals(saida)) {
                return true;
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverBaixo()) {
            malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroBaixo(malhaRodoviaCruzamento));
            AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()+1][malhaRodoviaCruzamento.getCOLUNA()];
            if (proximaMalha.equals(saida)) {
                return true;
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverEsquerda()) {
            malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroEsquerda(malhaRodoviaCruzamento));
            AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()-1];
            if (proximaMalha.equals(saida)) {
                return true;
            }
        }
        
        //CASO NÂO ENCONTRE A SAIDA, PROCURA NAS MALHAS AO LADO NAS QUAIS ESSA PODE IR
        if (malhaRodoviaCruzamento.getPodeMoverCima()) {
            malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroCima(malhaRodoviaCruzamento));
            AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()-1][malhaRodoviaCruzamento.getCOLUNA()];
            if (proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                if(buscarSaida((AbstractMalhaRodoviaCruzamento) proximaMalha)) {
                    return true;
                }
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverDireita()) {
            malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroDireita(malhaRodoviaCruzamento));
            AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()+1];
            if (proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                if(buscarSaida((AbstractMalhaRodoviaCruzamento) proximaMalha)) {
                    return true;
                }
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverBaixo()) {
            malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroBaixo(malhaRodoviaCruzamento));
            AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()+1][malhaRodoviaCruzamento.getCOLUNA()];
            if (proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                if(buscarSaida((AbstractMalhaRodoviaCruzamento) proximaMalha)) {
                    return true;
                }
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverEsquerda()) {
            malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroEsquerda(malhaRodoviaCruzamento));
            AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()-1];
            if (proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                if(buscarSaida((AbstractMalhaRodoviaCruzamento) proximaMalha)) {
                    return true;
                }
            }
        }
        return false;
    }
}
