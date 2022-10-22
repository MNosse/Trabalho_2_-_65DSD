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
import java.util.Random;

public class ControladorTelaMalhaRodoviaria implements ObserverMalhaRodovia {
    private ObserverTelaMalhaRodoviaria observer;
    private int[][] malhaRodoviariaNumeros;
    private boolean encerramento;

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
        mapeamentoEntradas();
    }

    public void geraCarro(){
        int posicao = new Random().nextInt(RepositorioMalha.getInstance().getIniciosMalha().size());
        int linha = RepositorioMalha.getInstance().getIniciosMalha().get(posicao).getLinha();
        int coluna = RepositorioMalha.getInstance().getIniciosMalha().get(posicao).getColuna();
        Carro carro = new Carro(linha, coluna, "Carro", RepositorioMalha.getInstance().getMalhaRodovias()[linha][coluna]);
        RepositorioMalha.getInstance().setCarros(carro);
        carro.start();
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
        if(!encerramento){
            RepositorioMalha.getInstance().getCarros().remove(carro);
            geraCarro();
        }
    }

    public void onEncerrarCarros(){
        for(Carro carro : RepositorioMalha.getInstance().getCarros()){
            carro.stop();
            this.encerramento = true;
            observer.notificarFimCarro(carro.getLinha(), carro.getColuna());
        }
    }

    @Override
    public void onIniciar(String s) {
        this.encerramento = false;
        if(s.matches("^\\d+$")){
            int numThreads = Integer.parseInt(s);
            for(int i = 0; i < numThreads; i ++){
                geraCarro();
            }
        }
    }

    private void mapeamentoEntradas(){
        for (int coluna = 0; coluna < malhaRodoviariaNumeros[0].length; coluna++) {
            if (malhaRodoviariaNumeros[0][coluna] == 3) {
                RepositorioMalha.getInstance().addIniciosMalha(RepositorioMalha.getInstance().getMalhaRodovias()[0][coluna]);
            }
            if (malhaRodoviariaNumeros[malhaRodoviariaNumeros.length -1][coluna] == 1) {
                RepositorioMalha.getInstance().addIniciosMalha(RepositorioMalha.getInstance().getMalhaRodovias()[malhaRodoviariaNumeros.length -1][coluna]);
            }
        }
        for (int linha = 0; linha < malhaRodoviariaNumeros.length -1; linha++) {
            if (malhaRodoviariaNumeros[linha][0] == 2) {
                RepositorioMalha.getInstance().addIniciosMalha(RepositorioMalha.getInstance().getMalhaRodovias()[linha][0]);
            }
            if (malhaRodoviariaNumeros[linha][malhaRodoviariaNumeros[0].length -1] == 4) {
                RepositorioMalha.getInstance().addIniciosMalha(RepositorioMalha.getInstance().getMalhaRodovias()[linha][malhaRodoviariaNumeros[0].length -1]);
            }
        }
    }
}
