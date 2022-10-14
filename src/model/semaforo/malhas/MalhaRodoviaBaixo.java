package model.semaforo.malhas;

import model.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;

public class MalhaRodoviaBaixo extends MalhaRodovia {
    @Override
    public void movimentarCarro(Carro carro, MalhaRodovia malha) throws InterruptedException {
        malha.getMutex().release();
        carro.setLinha(carro.getLinha()+1);
        System.out.println("Eu sou o "+ carro.getNomeCarro() +" e estou passando para a coluna "+ carro.getColuna() + " e linha "+ carro.getLinha());
        MalhaRodovia proxMalha = RepositorioMalha.getInstance().getFactoryMalhas()[carro.getLinha()][carro.getColuna()];
        if(proxMalha != null)
            proxMalha.movimentarCarro(carro, this);
        else{
            this.getMutex().release();
        }
    }
}
