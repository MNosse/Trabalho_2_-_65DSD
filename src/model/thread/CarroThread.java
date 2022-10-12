package model.thread;

import model.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;

public class CarroThread extends Thread{

    private MalhaRodovia malhaRodovia;
    private Carro carro;

    public CarroThread(MalhaRodovia malhaRodovia, Carro carro) {
        this.malhaRodovia = malhaRodovia;
        this.carro = carro;
    }

    @Override
    public void run() {
        try {
            this.malhaRodovia.movimentarCarro(carro);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
