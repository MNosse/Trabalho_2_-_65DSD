package controller.controller;

import controller.observer.ObserverTelaAbstractFactory;
import model.abstractFactory.ConcretFactoryMalhaRodoviaMonitor;
import model.abstractFactory.ConcretFactoryMalhaRodoviaMutex;
import singleton.RepositorioMalha;

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
