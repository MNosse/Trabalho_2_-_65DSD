package controller.controller;

import controller.observer.ObserverTelaInicial;
import model.semaforo.malhas.MalhaRodoviaBaixo;
import model.semaforo.malhas.MalhaRodoviaCima;
import model.semaforo.malhas.MalhaRodoviaDireita;
import model.semaforo.malhas.MalhaRodoviaEsquerda;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;
import utils.LeitorArquivoMalha;

import java.io.File;

public class ControladorTelaInicial {
    private ObserverTelaInicial observer;
    private int[][] malhaRodoviariaNumeros;
    
    public ControladorTelaInicial(ObserverTelaInicial observer) {
        this.observer = observer;
    }
    
    public void atualizarConteudoMalhaRodoviariaNumeros(File arquivoMalhaRodoviaria) {
        try {
            malhaRodoviariaNumeros = LeitorArquivoMalha.gerarMalhaRodoviaria(arquivoMalhaRodoviaria);
            observer.atualizarTxtCaminho(arquivoMalhaRodoviaria.getPath());
            observer.ativarBotaoIniciar();
        } catch(Exception e) {
            observer.alertarErroNoArquivo();
        }
    }
    
    public void navegarParaTelaMalhaRodoviariaNumeros() {
        RepositorioMalha.getInstance().setMalhaRodoviariaNumeros(malhaRodoviariaNumeros);
        MalhaRodovia[][] malhaRodovias = new MalhaRodovia[malhaRodoviariaNumeros.length][malhaRodoviariaNumeros[0].length];
        for (int linha = 0; linha < malhaRodoviariaNumeros.length; linha++) {
            for (int coluna = 0; coluna < malhaRodoviariaNumeros[0].length; coluna++) {
                switch (malhaRodoviariaNumeros[linha][coluna]){
                    case 1:
                        malhaRodovias[linha][coluna] = new MalhaRodoviaCima();
                        break;
                    case 2:
                        malhaRodovias[linha][coluna] = new MalhaRodoviaDireita();
                        break;
                    case 3:
                        malhaRodovias[linha][coluna] = new MalhaRodoviaBaixo();
                        break;
                    case 4:
                        malhaRodovias[linha][coluna] = new MalhaRodoviaEsquerda();
                        break;
                    default:
                }
            }
        }
        RepositorioMalha.getInstance().setMalhaRodovias(malhaRodovias);
        observer.navegarParaTelaMalhaRodoviaria();
    }
}
