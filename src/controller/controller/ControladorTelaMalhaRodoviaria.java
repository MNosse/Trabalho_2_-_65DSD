package controller.controller;

import controller.observer.ObserverTelaMalhaRodoviaria;
import model.semaforo.malhas.MalhaRodoviaBaixo;
import model.semaforo.malhas.MalhaRodoviaCima;
import model.semaforo.malhas.MalhaRodoviaDireita;
import model.semaforo.malhas.MalhaRodoviaEsquerda;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import model.semaforo.observer.ObserverMalhaRodovia;
import model.thread.Carro;
import singleton.RepositorioMalha;

public class ControladorTelaMalhaRodoviaria implements ObserverMalhaRodovia {
    private ObserverTelaMalhaRodoviaria observer;
    private int[][] malhaRodoviariaNumeros;

    public ControladorTelaMalhaRodoviaria(ObserverTelaMalhaRodoviaria observer) {
        this.observer = observer;
        malhaRodoviariaNumeros = RepositorioMalha.getInstance().getMalhaRodoviariaNumeros();
        MalhaRodovia[][] malhaRodovias = new MalhaRodovia[malhaRodoviariaNumeros.length][malhaRodoviariaNumeros[0].length];
        for (int linha = 0; linha < malhaRodoviariaNumeros.length; linha++) {
            for (int coluna = 0; coluna < malhaRodoviariaNumeros[0].length; coluna++) {
                switch (malhaRodoviariaNumeros[linha][coluna]){
                    case 1:
                        malhaRodovias[linha][coluna] = new MalhaRodoviaCima(this);
                        break;
                    case 2:
                        malhaRodovias[linha][coluna] = new MalhaRodoviaDireita(this);
                        break;
                    case 3:
                        malhaRodovias[linha][coluna] = new MalhaRodoviaBaixo(this);
                        break;
                    case 4:
                        malhaRodovias[linha][coluna] = new MalhaRodoviaEsquerda(this);
                        break;
                    default:
                }
            }
        }
        RepositorioMalha.getInstance().setMalhaRodovias(malhaRodovias);
    }

    public void geraCarro(){
        Carro carro1 = new Carro(0, 7, "Carro 1", RepositorioMalha.getInstance().getMalhaRodovias()[0][7]);
        carro1.start();
        Carro carro2 = new Carro(0, 7, "Carro 2", RepositorioMalha.getInstance().getMalhaRodovias()[0][7]);
        carro2.start();
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
