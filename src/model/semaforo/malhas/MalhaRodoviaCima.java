package model.semaforo.malhas;

import model.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;

public class MalhaRodoviaCima extends MalhaRodovia {

    @Override
    public void movimentarCarro(Carro carro, MalhaRodovia malha) throws InterruptedException {
        malha.getMutex().release();
        carro.setLinha(carro.getLinha() -1);
        MalhaRodovia proxMalha = RepositorioMalha.getInstance().getFactoryMalhas()[carro.getLinha()][carro.getColuna()];
        proxMalha.movimentarCarro(carro, this);
    }
}
