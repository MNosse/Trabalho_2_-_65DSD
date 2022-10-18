package model.semaforo.malhas;

import model.thread.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;

public class MalhaRodoviaCima extends MalhaRodovia {
    
    @Override
    public void movimentarCarro(Carro carro){
        carro.setLinha(carro.getLinha()-1);
        System.out.println("Eu sou o "+ carro.getNomeCarro() +" e estou passando para a coluna "+ carro.getColuna() + " e linha "+ carro.getLinha());
    }
    
    @Override
    public MalhaRodovia getProximaMalhaRodovia(Carro carro) {
        int novaLinha = carro.getLinha()-1;
        MalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        if(novaLinha >= 0 && malhaRodovias[novaLinha][carro.getColuna()] != null)
            return malhaRodovias[novaLinha][carro.getColuna()];
        else{
            this.getMutex().release();
            return null;
        }
    }
}
