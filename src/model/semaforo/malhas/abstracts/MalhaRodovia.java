package model.semaforo.malhas.abstracts;

import model.thread.Carro;

import java.util.concurrent.Semaphore;

public abstract class MalhaRodovia {

    protected Semaphore mutex = new Semaphore(1);

    public abstract void movimentarCarro(Carro carro);
    
    public abstract MalhaRodovia getProximaMalhaRodovia(Carro carro);

    public Semaphore getMutex() {
        return mutex;
    }

}
