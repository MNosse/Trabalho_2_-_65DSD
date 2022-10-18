package model.semaforo.malhas.abstracts;

import model.semaforo.observer.ObserverMalhaRodovia;
import model.thread.Carro;

import java.util.concurrent.Semaphore;

public abstract class MalhaRodovia{

    protected Semaphore mutex = new Semaphore(1);
    
    private ObserverMalhaRodovia observer;
    
    public MalhaRodovia(ObserverMalhaRodovia observer) {
        this.observer = observer;
    }

    public abstract void movimentarCarro(Carro carro);
    
    public abstract MalhaRodovia getProximaMalhaRodovia(Carro carro);

    public Semaphore getMutex() {
        return mutex;
    }
    
    public ObserverMalhaRodovia getObserver() {
        return observer;
    }
}
