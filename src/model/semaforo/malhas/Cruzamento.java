package model.semaforo.malhas;

import model.semaforo.observer.ObserverMalhaRodovia;
import model.semaforo.strategy.StrategyMovimentaCarroBaixo;
import model.semaforo.strategy.StrategyMovimentaCarroCima;
import model.semaforo.strategy.StrategyMovimentaCarroDireita;
import model.semaforo.strategy.StrategyMovimentaCarroEsquerda;
import model.thread.Carro;
import singleton.RepositorioMalha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cruzamento{
    
    private List<MalhaRodoviaCruzamento> malhasQueCompoe;
    private List<MalhaRodovia> saidas;
    private MalhaRodovia saida;
    
    public Cruzamento(List<MalhaRodoviaCruzamento> malhasQueCompoe) {
        this.malhasQueCompoe = malhasQueCompoe;
        saidas = getSaidas();
    }
    
    //MOVIMENTA CARRO COM MONITOR
    public synchronized void movimentarCarro(Carro carro) {
        //ESCOLHE UMA SAIDA ALEATORIA
        int indiceSaida = new Random().nextInt(saidas.size());
        saida = saidas.get(indiceSaida);
        
        //CONFIGURA O CAMINHO ATE A SAIDA COM OS STRATEGIES CORRETOS
        configurarMalhas(carro);
        
        //MOVIMENTA O CARRO
        do {
            MalhaRodovia proximaMalhaRodovia = carro.getMalhaRodovia().getProximaMalhaRodovia(carro);
            carro.getMalhaRodovia().movimentarCarro(carro);
            carro.setMalhaRodovia(proximaMalhaRodovia);
            carro.dormir();
        } while (!carro.getMalhaRodovia().equals(saida));
    }
    
    //RETORNA TODAS AS SAIDAS DO CRUZAMENTO
    private List<MalhaRodovia> getSaidas() {
        MalhaRodovia[][] malhasRodovia = RepositorioMalha.getInstance().getMalhaRodovias();
        List<MalhaRodovia> saidas = new ArrayList<>();
        for (MalhaRodoviaCruzamento malhaRodoviaCruzamento : malhasQueCompoe) {
            if (malhaRodoviaCruzamento.getPodeMoverCima()) {
                MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()-1][malhaRodoviaCruzamento.getColuna()];
                if (!proximaMalha.getClass().equals(MalhaRodoviaCruzamento.class)) {
                    saidas.add(proximaMalha);
                }
            }
            if (malhaRodoviaCruzamento.getPodeMoverDireita()) {
                MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()][malhaRodoviaCruzamento.getColuna()+1];
                if (!proximaMalha.getClass().equals(MalhaRodoviaCruzamento.class)) {
                    saidas.add(proximaMalha);
                }
            }
            if (malhaRodoviaCruzamento.getPodeMoverBaixo()) {
                MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()+1][malhaRodoviaCruzamento.getColuna()];
                if (!proximaMalha.getClass().equals(MalhaRodoviaCruzamento.class)) {
                    saidas.add(proximaMalha);
                }
            }
            if (malhaRodoviaCruzamento.getPodeMoverEsquerda()) {
                MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()][malhaRodoviaCruzamento.getColuna()-1];
                if (!proximaMalha.getClass().equals(MalhaRodoviaCruzamento.class)) {
                    saidas.add(proximaMalha);
                }
            }
        }
        return saidas;
    }
    
    //CONFIGURA O CAMINHO ATE A SAIDA COM OS STRATEGIES CORRETOS
    private void configurarMalhas(Carro carro) {
        buscarSaida((MalhaRodoviaCruzamento) carro.getMalhaRodovia().getProximaMalhaRodovia(carro));
    }
    
    //BUSCA RECURSIVAMENTE A SAIDA
    private boolean buscarSaida(MalhaRodoviaCruzamento malhaRodoviaCruzamento) {
        MalhaRodovia[][] malhasRodovia = RepositorioMalha.getInstance().getMalhaRodovias();
        
        //PROCURA A SAIDA A PARTIR DA MALHA ATUAL
        if (malhaRodoviaCruzamento.getPodeMoverCima()) {
            malhaRodoviaCruzamento.setStrategyMovimentaCarro(new StrategyMovimentaCarroCima(malhaRodoviaCruzamento));
            MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()-1][malhaRodoviaCruzamento.getColuna()];
            if (proximaMalha.equals(saida)) {
                return true;
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverDireita()) {
            malhaRodoviaCruzamento.setStrategyMovimentaCarro(new StrategyMovimentaCarroDireita(malhaRodoviaCruzamento));
            MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()][malhaRodoviaCruzamento.getColuna()+1];
            if (proximaMalha.equals(saida)) {
                return true;
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverBaixo()) {
            malhaRodoviaCruzamento.setStrategyMovimentaCarro(new StrategyMovimentaCarroBaixo(malhaRodoviaCruzamento));
            MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()+1][malhaRodoviaCruzamento.getColuna()];
            if (proximaMalha.equals(saida)) {
                return true;
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverEsquerda()) {
            malhaRodoviaCruzamento.setStrategyMovimentaCarro(new StrategyMovimentaCarroEsquerda(malhaRodoviaCruzamento));
            MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()][malhaRodoviaCruzamento.getColuna()-1];
            if (proximaMalha.equals(saida)) {
                return true;
            }
        }
        
        //CASO NÂO ENCONTRE A SAIDA, PROCURA NAS MALHAS AO LADO NAS QUAIS ESSA PODE IR
        if (malhaRodoviaCruzamento.getPodeMoverCima()) {
            malhaRodoviaCruzamento.setStrategyMovimentaCarro(new StrategyMovimentaCarroCima(malhaRodoviaCruzamento));
            MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()-1][malhaRodoviaCruzamento.getColuna()];
            if (proximaMalha.getClass().equals(MalhaRodoviaCruzamento.class)) {
                if(buscarSaida((MalhaRodoviaCruzamento) proximaMalha)) {
                    return true;
                }
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverDireita()) {
            malhaRodoviaCruzamento.setStrategyMovimentaCarro(new StrategyMovimentaCarroDireita(malhaRodoviaCruzamento));
            MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()][malhaRodoviaCruzamento.getColuna()+1];
            if (proximaMalha.getClass().equals(MalhaRodoviaCruzamento.class)) {
                if(buscarSaida((MalhaRodoviaCruzamento) proximaMalha)) {
                    return true;
                }
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverBaixo()) {
            malhaRodoviaCruzamento.setStrategyMovimentaCarro(new StrategyMovimentaCarroBaixo(malhaRodoviaCruzamento));
            MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()+1][malhaRodoviaCruzamento.getColuna()];
            if (proximaMalha.getClass().equals(MalhaRodoviaCruzamento.class)) {
                if(buscarSaida((MalhaRodoviaCruzamento) proximaMalha)) {
                    return true;
                }
            }
        }
        if (malhaRodoviaCruzamento.getPodeMoverEsquerda()) {
            malhaRodoviaCruzamento.setStrategyMovimentaCarro(new StrategyMovimentaCarroEsquerda(malhaRodoviaCruzamento));
            MalhaRodovia proximaMalha = malhasRodovia[malhaRodoviaCruzamento.getLinha()][malhaRodoviaCruzamento.getColuna()-1];
            if (proximaMalha.getClass().equals(MalhaRodoviaCruzamento.class)) {
                if(buscarSaida((MalhaRodoviaCruzamento) proximaMalha)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<MalhaRodoviaCruzamento> getMalhasQueCompoe() {
        return malhasQueCompoe;
    }
}
