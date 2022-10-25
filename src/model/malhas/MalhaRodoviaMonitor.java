package model.malhas;

import model.observer.ObserverMalhaRodovia;
import model.thread.Carro;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MalhaRodoviaMonitor extends AbstractMalhaRodovia {
    protected Lock lock = new ReentrantLock();
    
    public MalhaRodoviaMonitor(ObserverMalhaRodovia observer, int linha, int coluna) {
        super(observer, linha, coluna);
    }

    @Override
    public synchronized void movimentarCarro(Carro carro) {
        strategy.movimentarCarro(carro);
    }
    
    @Override
    public synchronized void movimentarCarroSimples(Carro carro) throws InterruptedException {
        strategy.movimentarCarroSimples(carro);
    }
    
    public AbstractMalhaRodovia getProximaMalhaRodovia(Carro carro) {
        return strategy.getProximaMalhaRodovia(carro);
    }
    
    @Override
    public boolean tryBloquear() throws InterruptedException {
        return lock.tryLock(2, TimeUnit.SECONDS);
    }
    
    @Override
    public void bloquear() throws InterruptedException {
        lock.lockInterruptibly();
    }
    
    @Override
    public void liberar() {
        try {
            lock.unlock();
        } catch(IllegalMonitorStateException e) {
            //Trata erro do unlock
        }
    }
}
