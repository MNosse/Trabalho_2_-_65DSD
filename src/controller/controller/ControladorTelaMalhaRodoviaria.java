package controller.controller;

import controller.observer.ObserverTelaMalhaRodoviaria;
import model.semaforo.malhas.Cruzamento;
import model.semaforo.malhas.MalhaRodovia;
import model.semaforo.malhas.MalhaRodoviaCruzamento;
import model.semaforo.observer.ObserverMalhaRodovia;
import model.semaforo.strategy.StrategyMovimentaCarroBaixo;
import model.semaforo.strategy.StrategyMovimentaCarroCima;
import model.semaforo.strategy.StrategyMovimentaCarroDireita;
import model.semaforo.strategy.StrategyMovimentaCarroEsquerda;
import model.thread.Carro;
import singleton.RepositorioMalha;

import java.util.ArrayList;
import java.util.List;

public class ControladorTelaMalhaRodoviaria implements ObserverMalhaRodovia {
    private ObserverTelaMalhaRodoviaria observer;
    private int[][] malhaRodoviariaNumeros;

    public ControladorTelaMalhaRodoviaria(ObserverTelaMalhaRodoviaria observer) {
        this.observer = observer;
        malhaRodoviariaNumeros = RepositorioMalha.getInstance().getMalhaRodoviariaNumeros();
        MalhaRodovia[][] malhaRodovias = new MalhaRodovia[malhaRodoviariaNumeros.length][malhaRodoviariaNumeros[0].length];
        for (int linha = 0; linha < malhaRodoviariaNumeros.length; linha++) {
            for (int coluna = 0; coluna < malhaRodoviariaNumeros[0].length; coluna++) {
                MalhaRodovia malhaRodovia = new MalhaRodovia(this, linha, coluna);
                MalhaRodoviaCruzamento malhaRodoviaCruzamento = new MalhaRodoviaCruzamento(this, linha, coluna);
                switch (malhaRodoviariaNumeros[linha][coluna]){
                    case 1:
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroCima(malhaRodovia));
                        break;
                    case 2:
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroDireita(malhaRodovia));
                        break;
                    case 3:
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroBaixo(malhaRodovia));
                        break;
                    case 4:
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroEsquerda(malhaRodovia));
                        break;
                    case 5:
                        malhaRodoviaCruzamento.setPodeMoverCima(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroCima(malhaRodovia));
                        break;
                    case 6:
                        malhaRodoviaCruzamento.setPodeMoverDireita(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroDireita(malhaRodovia));
                        break;
                    case 7:
                        malhaRodoviaCruzamento.setPodeMoverBaixo(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroBaixo(malhaRodovia));
                        break;
                    case 8:
                        malhaRodoviaCruzamento.setPodeMoverEsquerda(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroEsquerda(malhaRodovia));
                        break;
                    case 9:
                        malhaRodoviaCruzamento.setPodeMoverCima(true);
                        malhaRodoviaCruzamento.setPodeMoverDireita(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroCima(malhaRodovia));
                        break;
                    case 10:
                        malhaRodoviaCruzamento.setPodeMoverCima(true);
                        malhaRodoviaCruzamento.setPodeMoverEsquerda(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroCima(malhaRodovia));
                        break;
                    case 11:
                        malhaRodoviaCruzamento.setPodeMoverBaixo(true);
                        malhaRodoviaCruzamento.setPodeMoverDireita(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroBaixo(malhaRodovia));
                        break;
                    case 12:
                        malhaRodoviaCruzamento.setPodeMoverBaixo(true);
                        malhaRodoviaCruzamento.setPodeMoverEsquerda(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategyMovimentaCarro(new StrategyMovimentaCarroBaixo(malhaRodovia));
                        break;
                    default:
                }
                malhaRodovias[linha][coluna] = malhaRodovia;
            }
        }
        RepositorioMalha.getInstance().setMalhaRodovias(malhaRodovias);
        for (int linha = 0; linha < malhaRodovias.length; linha++) {
            for(int coluna = 0; coluna < malhaRodovias[0].length; coluna++) {
                if (malhaRodovias[linha][coluna].getClass().equals(MalhaRodoviaCruzamento.class)) {
                    if (malhaRodovias[linha-1][coluna].getClass().equals(MalhaRodovia.class)
                        && malhaRodovias[linha][coluna-1].getClass().equals(MalhaRodovia.class)) {
                        List<MalhaRodoviaCruzamento> malhasRodoviaCruzamento = new ArrayList<>();
                        malhasRodoviaCruzamento.add((MalhaRodoviaCruzamento) malhaRodovias[linha][coluna]);
                        malhasRodoviaCruzamento.add((MalhaRodoviaCruzamento) malhaRodovias[linha][coluna+1]);
                        malhasRodoviaCruzamento.add((MalhaRodoviaCruzamento) malhaRodovias[linha+1][coluna]);
                        malhasRodoviaCruzamento.add((MalhaRodoviaCruzamento) malhaRodovias[linha+1][coluna+1]);
                        Cruzamento cruzamento = new Cruzamento(malhasRodoviaCruzamento);
                        RepositorioMalha.getInstance().getCruzamentos().add(cruzamento);
                    }
                }
            }
        }
    }

    public void geraCarro(){
//        TESTE MALHA 1
        Carro carro1 = new Carro(0, 7, "Carro 1", RepositorioMalha.getInstance().getMalhaRodovias()[0][7]);
        carro1.start();
        Carro carro2 = new Carro(0, 7, "Carro 2", RepositorioMalha.getInstance().getMalhaRodovias()[0][7]);
        carro2.start();
        //TESTE MALHA 2
//        Carro carro1 = new Carro(0, 7, "Carro 1", RepositorioMalha.getInstance().getMalhaRodovias()[0][7]);
//        carro1.start();
//        Carro carro2 = new Carro(24, 8, "Carro 2", RepositorioMalha.getInstance().getMalhaRodovias()[24][8]);
//        carro2.start();
//        Carro carro3 = new Carro(6, 0, "Carro 3", RepositorioMalha.getInstance().getMalhaRodovias()[6][0]);
//        carro3.start();
//        Carro carro4 = new Carro(5, 24, "Carro 4", RepositorioMalha.getInstance().getMalhaRodovias()[5][24]);
//        carro4.start();
    }

    public int[][] getMalhaRodoviariaNumeros() {
        return malhaRodoviariaNumeros;
    }
    
    @Override
    public void notificarInicioCarro(int linha, int coluna, int r, int g, int b) {
        observer.notificarInicioCarro(linha, coluna, r, g, b);
    }
    
    @Override
    public void notificarMovimentoCarro(int linhaAntiga, int colunaAntiga, int linhaNova, int colunaNova, int r, int g, int b) {
        observer.notificarMovimentoCarro(linhaAntiga, colunaAntiga, linhaNova, colunaNova, r, g, b);
    }
    
    @Override
    public void notificarFimCarro(int linha, int coluna) {
        observer.notificarFimCarro(linha, coluna);
    }
}
