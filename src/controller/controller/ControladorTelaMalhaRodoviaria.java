package controller.controller;

import controller.observer.ObserverTelaMalhaRodoviaria;
import model.abstractFactory.AbstractFactoryMalhaRodovia;
import model.abstractFactory.ConcretFactoryMalhaRodoviaMonitor;
import model.abstractFactory.ConcretFactoryMalhaRodoviaMutex;
import model.malhas.*;
import model.observer.ObserverMalhaRodovia;
import model.strategy.StrategyMovimentaCarroBaixo;
import model.strategy.StrategyMovimentaCarroCima;
import model.strategy.StrategyMovimentaCarroDireita;
import model.strategy.StrategyMovimentaCarroEsquerda;
import model.thread.Carro;
import singleton.RepositorioMalha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControladorTelaMalhaRodoviaria implements ObserverMalhaRodovia {
    private ObserverTelaMalhaRodoviaria observer;
    private int[][] malhaRodoviariaNumeros;
    private boolean interruptClick;
    private List<Carro> carros;

    public ControladorTelaMalhaRodoviaria(ObserverTelaMalhaRodoviaria observer) {
        this.observer = observer;
        interruptClick = false;
        malhaRodoviariaNumeros = RepositorioMalha.getInstance().getMalhaRodoviariaNumeros();
    }
    
    public void iniciarMalhasRodoviarias() {
        AbstractMalhaRodovia[][] malhasRodovia = new AbstractMalhaRodovia[malhaRodoviariaNumeros.length][malhaRodoviariaNumeros[0].length];
        AbstractFactoryMalhaRodovia factory = RepositorioMalha.getInstance().getFactory();
        for (int linha = 0; linha < malhaRodoviariaNumeros.length; linha++) {
            for (int coluna = 0; coluna < malhaRodoviariaNumeros[0].length; coluna++) {
                AbstractMalhaRodovia malhaRodovia = factory.createMalhaRodovia(this, linha, coluna);
                AbstractMalhaRodoviaCruzamento malhaRodoviaCruzamento = factory.createMalhaRodoviaCruzamento(this, linha, coluna);
                switch (malhaRodoviariaNumeros[linha][coluna]){
                    case 1:
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroCima(malhaRodovia));
                        break;
                    case 2:
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroDireita(malhaRodovia));
                        break;
                    case 3:
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroBaixo(malhaRodovia));
                        break;
                    case 4:
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroEsquerda(malhaRodovia));
                        break;
                    case 5:
                        malhaRodoviaCruzamento.setPodeMoverCima(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroCima(malhaRodovia));
                        break;
                    case 6:
                        malhaRodoviaCruzamento.setPodeMoverDireita(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroDireita(malhaRodovia));
                        break;
                    case 7:
                        malhaRodoviaCruzamento.setPodeMoverBaixo(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroBaixo(malhaRodovia));
                        break;
                    case 8:
                        malhaRodoviaCruzamento.setPodeMoverEsquerda(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroEsquerda(malhaRodovia));
                        break;
                    case 9:
                        malhaRodoviaCruzamento.setPodeMoverCima(true);
                        malhaRodoviaCruzamento.setPodeMoverDireita(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroCima(malhaRodovia));
                        break;
                    case 10:
                        malhaRodoviaCruzamento.setPodeMoverCima(true);
                        malhaRodoviaCruzamento.setPodeMoverEsquerda(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroCima(malhaRodovia));
                        break;
                    case 11:
                        malhaRodoviaCruzamento.setPodeMoverBaixo(true);
                        malhaRodoviaCruzamento.setPodeMoverDireita(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroBaixo(malhaRodovia));
                        break;
                    case 12:
                        malhaRodoviaCruzamento.setPodeMoverBaixo(true);
                        malhaRodoviaCruzamento.setPodeMoverEsquerda(true);
                        malhaRodovia = malhaRodoviaCruzamento;
                        malhaRodovia.setStrategy(new StrategyMovimentaCarroBaixo(malhaRodovia));
                        break;
                    default:
                        break;
                }
                malhasRodovia[linha][coluna] = malhaRodovia;
            }
        }
        RepositorioMalha.getInstance().setMalhaRodovias(malhasRodovia);
        for (int linha = 0; linha < malhasRodovia.length; linha++) {
            for(int coluna = 0; coluna < malhasRodovia[0].length; coluna++) {
                if (malhasRodovia[linha][coluna] instanceof AbstractMalhaRodoviaCruzamento) {
                    if (!(malhasRodovia[linha-1][coluna] instanceof AbstractMalhaRodoviaCruzamento)
                            && !(malhasRodovia[linha][coluna-1] instanceof AbstractMalhaRodoviaCruzamento)) {
                        List<AbstractMalhaRodoviaCruzamento> malhasRodoviaCruzamento = new ArrayList<>();
                        malhasRodoviaCruzamento.add((AbstractMalhaRodoviaCruzamento) malhasRodovia[linha][coluna]);
                        malhasRodoviaCruzamento.add((AbstractMalhaRodoviaCruzamento) malhasRodovia[linha][coluna+1]);
                        malhasRodoviaCruzamento.add((AbstractMalhaRodoviaCruzamento) malhasRodovia[linha+1][coluna]);
                        malhasRodoviaCruzamento.add((AbstractMalhaRodoviaCruzamento) malhasRodovia[linha+1][coluna+1]);
                        AbstractCruzamento cruzamento = factory.createCruzamento(malhasRodoviaCruzamento);
                        RepositorioMalha.getInstance().getCruzamentos().add(cruzamento);
                    }
                }
            }
        }
        mapeamentoEntradas();
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
    public void notificarFimCarro(int linha, int coluna, Carro carro) {
        observer.notificarFimCarro(linha, coluna);
        if (!interruptClick) {
            carros.remove(carro);
            geraCarro();
        }
    }
    
    public void geraCarro(){
        int posicao = new Random().nextInt(RepositorioMalha.getInstance().getIniciosMalha().size());
        int linha = RepositorioMalha.getInstance().getIniciosMalha().get(posicao).getLINHA();
        int coluna = RepositorioMalha.getInstance().getIniciosMalha().get(posicao).getCOLUNA();
        Carro carro = new Carro(linha, coluna, "Carro", RepositorioMalha.getInstance().getMalhaRodovias()[linha][coluna]);
        carros.add(carro);
        carro.start();
    }
    
    public void onIniciar(String s) {
        interruptClick = false;
        iniciarMalhasRodoviarias();
        AbstractMalhaRodovia[][] a = RepositorioMalha.getInstance().getMalhaRodovias();
        carros = new ArrayList<>();
        if(s.matches("^\\d+$")){
            int numThreads = Integer.parseInt(s);
            for(int i = 0; i < numThreads; i ++){
                geraCarro();
            }
        }
    }
    
    public void onEncerrarCarros(){
        interruptClick = true;
        for (Carro carro: carros) {
            carro.setInterruptedTrue();
        }
        carros.clear();
    }

    private void mapeamentoEntradas(){
        for (int coluna = 0; coluna < malhaRodoviariaNumeros[0].length; coluna++) {
            if (malhaRodoviariaNumeros[0][coluna] == 3) {
                RepositorioMalha.getInstance().addInicioMalha(RepositorioMalha.getInstance().getMalhaRodovias()[0][coluna]);
            }
            if (malhaRodoviariaNumeros[malhaRodoviariaNumeros.length -1][coluna] == 1) {
                RepositorioMalha.getInstance().addInicioMalha(RepositorioMalha.getInstance().getMalhaRodovias()[malhaRodoviariaNumeros.length -1][coluna]);
            }
        }
        for (int linha = 0; linha < malhaRodoviariaNumeros.length -1; linha++) {
            if (malhaRodoviariaNumeros[linha][0] == 2) {
                RepositorioMalha.getInstance().addInicioMalha(RepositorioMalha.getInstance().getMalhaRodovias()[linha][0]);
            }
            if (malhaRodoviariaNumeros[linha][malhaRodoviariaNumeros[0].length -1] == 4) {
                RepositorioMalha.getInstance().addInicioMalha(RepositorioMalha.getInstance().getMalhaRodovias()[linha][malhaRodoviariaNumeros[0].length -1]);
            }
        }
    }
}
