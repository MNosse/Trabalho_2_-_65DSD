package model.semaforo.malhas.abstracts;

import model.Carro;
import singleton.RepositorioMalha;

import java.util.concurrent.Semaphore;

public abstract class MalhaRodovia {

    protected Semaphore mutex = new Semaphore(1);

    public abstract void movimentarCarro(Carro carro) throws InterruptedException;

}
