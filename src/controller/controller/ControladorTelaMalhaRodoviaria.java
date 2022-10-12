package controller.controller;

import controller.observer.ObserverTelaMalhaRodoviaria;

public class ControladorTelaMalhaRodoviaria {
    private ObserverTelaMalhaRodoviaria observer;
    private int[][] malhaRodoviaria;
    
    public ControladorTelaMalhaRodoviaria(ObserverTelaMalhaRodoviaria observer) {
        this.observer = observer;
        malhaRodoviaria = new int[0][0];
    }
    
    public ControladorTelaMalhaRodoviaria(ObserverTelaMalhaRodoviaria observer, int[][] malhaRodoviaria) {
        this.observer = observer;
        this.malhaRodoviaria = malhaRodoviaria;
    }
    
    public int[][] getMalhaRodoviaria() {
        return malhaRodoviaria;
    }
}
