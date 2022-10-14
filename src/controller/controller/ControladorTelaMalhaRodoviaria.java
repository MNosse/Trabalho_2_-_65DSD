package controller.controller;

import controller.observer.ObserverTelaMalhaRodoviaria;
import model.Carro;
import model.semaforo.malhas.MalhaRodoviaBaixo;
import model.semaforo.malhas.MalhaRodoviaCima;
import model.semaforo.malhas.MalhaRodoviaDireita;
import model.semaforo.malhas.MalhaRodoviaEsquerda;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import model.thread.CarroThread;
import singleton.RepositorioMalha;

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
        RepositorioMalha.getInstance().setFactoryMalhas(new MalhaRodovia[malhaRodoviaria.length][malhaRodoviaria[0].length]);
    }

    public void geraFactoryMalha(int linha, int coluna) {
        switch (malhaRodoviaria[linha][coluna]){
            case 1:
                RepositorioMalha.getInstance().getFactoryMalhas()[linha][coluna] = new MalhaRodoviaCima();
                break;
            case 2:
                RepositorioMalha.getInstance().getFactoryMalhas()[linha][coluna] = new MalhaRodoviaDireita();
                break;
            case 3:
                RepositorioMalha.getInstance().getFactoryMalhas()[linha][coluna] = new MalhaRodoviaBaixo();
                break;
            case 4:
                RepositorioMalha.getInstance().getFactoryMalhas()[linha][coluna] = new MalhaRodoviaEsquerda();
                break;
            default:
        }
    }

    public void geraCarro(){
        CarroThread carro1 = new CarroThread(RepositorioMalha.getInstance().getFactoryMalhas()[0][7], new Carro(0, 7, "Carro 1"));
        carro1.start();
        CarroThread carro2 = new CarroThread(RepositorioMalha.getInstance().getFactoryMalhas()[0][7], new Carro(0, 7, "Carro 2"));
        carro2.start();
    }

    public int[][] getMalhaRodoviaria() {
        return malhaRodoviaria;
    }
}
