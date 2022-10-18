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
        observer.navegarParaTelaMalhaRodoviaria();
    }
}
