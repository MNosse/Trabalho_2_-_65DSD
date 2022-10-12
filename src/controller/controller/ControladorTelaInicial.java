package controller.controller;

import controller.observer.ObserverTelaInicial;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;
import utils.LeitorArquivoMalha;

import java.io.File;

public class ControladorTelaInicial {
    private ObserverTelaInicial observer;
    private int[][] malhaRodoviaria;
    
    public ControladorTelaInicial(ObserverTelaInicial observer) {
        this.observer = observer;
    }
    
    public void atualizarConteudoMalhaRodoviaria(File arquivoMalhaRodoviaria) {
        try {
            malhaRodoviaria = LeitorArquivoMalha.gerarMalhaRodoviaria(arquivoMalhaRodoviaria);
            observer.atualizarTxtCaminho(arquivoMalhaRodoviaria.getPath());
            observer.ativarBotaoIniciar();
        } catch(Exception e) {
            observer.alertarErroNoArquivo();
        }
    }
    
    public void navegarParaTelaMalhaRodoviaria() {
        observer.navegarParaTelaMalhaRodoviaria(malhaRodoviaria);
    }
}
