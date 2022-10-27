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

public class CruzamentoMonitor extends AbstractCruzamento {
    
    public CruzamentoMonitor(List<AbstractMalhaRodoviaCruzamento> malhasCruzamento) {
        super(malhasCruzamento);
        saidas = getSaidas();
    }
    
    @Override
    public void movimentarCarro(Carro carro) {
        try {
            int indiceSaida = new Random().nextInt(saidas.size());
            carro.setSaida(saidas.get(indiceSaida));
            if (carro.getSaida().tryBloquear()) {
                if(configurarMalhas(carro)) {
                    do {
                        carro.getMalhaRodovia().movimentarCarroSimples(carro);
                    } while (!carro.getMalhaRodovia().equals(carro.getSaida()) && !carro.isInterrupted() && !carro.getInterrupted());
                } else {
                    carro.getSaida().liberar();
                }
            } else {
                carro.dormir();
            }
        } catch(InterruptedException e) {
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
    private boolean configurarMalhas(Carro carro) throws InterruptedException {
        return buscarSaida((AbstractMalhaRodoviaCruzamento) carro.getMalhaRodovia().getProximaMalhaRodovia(carro), carro);
    }
    
    //BUSCA RECURSIVAMENTE A SAIDA
    private boolean buscarSaida(AbstractMalhaRodoviaCruzamento malhaRodoviaCruzamento, Carro carro) throws InterruptedException {
        AbstractMalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        try {
            if (malhaRodoviaCruzamento.tryBloquear()) {
                //PROCURA A SAIDA A PARTIR DA MALHA ATUAL
                if (malhaRodoviaCruzamento.getPodeMoverCima()) {
                    malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroCima(malhaRodoviaCruzamento));
                    AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()-1][malhaRodoviaCruzamento.getCOLUNA()];
                    if (proximaMalha.equals(carro.getSaida())) {
                        return true;
                    }
                }
                if (malhaRodoviaCruzamento.getPodeMoverDireita()) {
                    malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroDireita(malhaRodoviaCruzamento));
                    AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()+1];
                    if (proximaMalha.equals(carro.getSaida())) {
                        return true;
                    }
                }
                if (malhaRodoviaCruzamento.getPodeMoverBaixo()) {
                    malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroBaixo(malhaRodoviaCruzamento));
                    AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()+1][malhaRodoviaCruzamento.getCOLUNA()];
                    if (proximaMalha.equals(carro.getSaida())) {
                        return true;
                    }
                }
                if (malhaRodoviaCruzamento.getPodeMoverEsquerda()) {
                    malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroEsquerda(malhaRodoviaCruzamento));
                    AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()-1];
                    if (proximaMalha.equals(carro.getSaida())) {
                        return true;
                    }
                }

                //CASO NÂO ENCONTRE A SAIDA, PROCURA NAS MALHAS AO LADO NAS QUAIS ESSA PODE IR
                if (malhaRodoviaCruzamento.getPodeMoverCima()) {
                    malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroCima(malhaRodoviaCruzamento));
                    AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()-1][malhaRodoviaCruzamento.getCOLUNA()];
                    if (proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                        if(buscarSaida((AbstractMalhaRodoviaCruzamento) proximaMalha, carro)) {
                            return true;
                        }
                    }
                }
                if (malhaRodoviaCruzamento.getPodeMoverDireita()) {
                    malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroDireita(malhaRodoviaCruzamento));
                    AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()+1];
                    if (proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                        if(buscarSaida((AbstractMalhaRodoviaCruzamento) proximaMalha, carro)) {
                            return true;
                        }
                    }
                }
                if (malhaRodoviaCruzamento.getPodeMoverBaixo()) {
                    malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroBaixo(malhaRodoviaCruzamento));
                    AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()+1][malhaRodoviaCruzamento.getCOLUNA()];
                    if (proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                        if(buscarSaida((AbstractMalhaRodoviaCruzamento) proximaMalha, carro)) {
                            return true;
                        }
                    }
                }
                if (malhaRodoviaCruzamento.getPodeMoverEsquerda()) {
                    malhaRodoviaCruzamento.setStrategy(new StrategyMovimentaCarroEsquerda(malhaRodoviaCruzamento));
                    AbstractMalhaRodovia proximaMalha = malhaRodovias[malhaRodoviaCruzamento.getLINHA()][malhaRodoviaCruzamento.getCOLUNA()-1];
                    if (proximaMalha instanceof AbstractMalhaRodoviaCruzamento) {
                        if(buscarSaida((AbstractMalhaRodoviaCruzamento) proximaMalha, carro)) {
                            return true;
                        }
                    }
                }
                malhaRodoviaCruzamento.liberar();
                return false;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            malhaRodoviaCruzamento.liberar();
            throw new InterruptedException();
        }
    }
}
