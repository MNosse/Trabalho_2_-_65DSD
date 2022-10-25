package controller.controller;

import controller.observer.ObserverTelaAbstractFactory;
import controller.observer.ObserverTelaInicial;
import model.abstractFactory.ConcretFactoryMalhaRodoviaMonitor;
import model.abstractFactory.ConcretFactoryMalhaRodoviaMutex;
import singleton.RepositorioMalha;
import utils.LeitorArquivoMalha;

import java.io.File;

public class ControladorTelaAbstractFactory {
    private ObserverTelaAbstractFactory observer;
    
    public ControladorTelaAbstractFactory(ObserverTelaAbstractFactory observer) {
        this.observer = observer;
    }
    
    public void selecionarMutex() {
        RepositorioMalha.getInstance().setFactory(new ConcretFactoryMalhaRodoviaMutex());
        observer.navegarParaTelaMalhaRodoviaria();
    }
    
    public void selecionarMonitor() {
        RepositorioMalha.getInstance().setFactory(new ConcretFactoryMalhaRodoviaMonitor());
        observer.navegarParaTelaMalhaRodoviaria();
    }
}
