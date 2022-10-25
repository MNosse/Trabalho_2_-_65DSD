package model.malhas;

import model.observer.ObserverMalhaRodovia;
import model.thread.Carro;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MalhaRodoviaMutex extends AbstractMalhaRodovia {

    protected Semaphore mutex = new Semaphore(1);
    
    public MalhaRodoviaMutex(ObserverMalhaRodovia observer, int linha, int coluna) {
        super(observer, linha, coluna);
    }

    @Override
    public void movimentarCarro(Carro carro) {
        strategy.movimentarCarro(carro);
    }
    
    @Override
    public void movimentarCarroSimples(Carro carro) throws InterruptedException {
        strategy.movimentarCarroSimples(carro);
    }
    
    public AbstractMalhaRodovia getProximaMalhaRodovia(Carro carro) {
        return strategy.getProximaMalhaRodovia(carro);
    }
    
    @Override
    public boolean tryBloquear() throws InterruptedException {
        return mutex.tryAcquire(2, TimeUnit.SECONDS);
    }
    
    @Override
    public void bloquear() throws InterruptedException {
        mutex.acquire();
    }
    
    @Override
    public void liberar() {
        mutex.release();
    }
}
