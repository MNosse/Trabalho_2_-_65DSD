package model.semaforo.malhas;

import model.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;

public class MalhaRodoviaEsquerda extends MalhaRodovia {
    @Override
    public void movimentarCarro(Carro carro) throws InterruptedException {
        mutex.acquire();
        MalhaRodovia malha = RepositorioMalha.getInstance().getFactoryMalhas()[carro.getLinha() - 1][carro.getColuna()];
        malha.movimentarCarro(carro);
        mutex.release();
    }
}
