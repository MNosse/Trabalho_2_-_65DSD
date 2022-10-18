package controller.controller;

import controller.observer.ObserverTelaMalhaRodoviaria;
import model.thread.Carro;
import singleton.RepositorioMalha;

public class ControladorTelaMalhaRodoviaria {
    private ObserverTelaMalhaRodoviaria observer;
    private int[][] malhaRodoviaria;

    public ControladorTelaMalhaRodoviaria(ObserverTelaMalhaRodoviaria observer) {
        this.observer = observer;
        malhaRodoviaria = RepositorioMalha.getInstance().getMalhaRodoviariaNumeros();
    }

    public void geraCarro(){
        Carro carro1 = new Carro(0, 7, "Carro 1", RepositorioMalha.getInstance().getMalhaRodovias()[0][7]);
        carro1.start();
        Carro carro2 = new Carro(0, 7, "Carro 2", RepositorioMalha.getInstance().getMalhaRodovias()[0][7]);
        carro2.start();
    }

    public int[][] getMalhaRodoviaria() {
        return malhaRodoviaria;
    }
}
