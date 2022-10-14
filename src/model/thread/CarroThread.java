package model.thread;

import model.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;

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
            this.malhaRodovia.getMutex().acquire();
            this.malhaRodovia.movimentarCarro(this.carro, RepositorioMalha.getInstance().getFactoryMalhas()[carro.getLinha()][carro.getColuna()]);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
