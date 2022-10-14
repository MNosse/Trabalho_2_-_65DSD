package model.semaforo.malhas;

import model.thread.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;

public class MalhaRodoviaEsquerda extends MalhaRodovia {
    
    @Override
    public void movimentarCarro(Carro carro) {
        carro.setColuna(carro.getColuna()-1);
        System.out.println("Eu sou o "+ carro.getNomeCarro() +" e estou passando para a coluna "+ carro.getColuna() + " e linha "+ carro.getLinha());
    }
    
    @Override
    public MalhaRodovia getProximaMalhaRodovia(Carro carro) {
        int novaColuna = carro.getColuna()-1;
        MalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        if(novaColuna >= 0 && malhaRodovias[carro.getLinha()][novaColuna] != null)
            return malhaRodovias[carro.getLinha()][novaColuna];
        else{
            this.getMutex().release();
            return null;
        }
    }
}
