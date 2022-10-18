package model.semaforo.malhas;

import model.semaforo.observer.ObserverMalhaRodovia;
import model.thread.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;

public class MalhaRodoviaDireita extends MalhaRodovia {
    public MalhaRodoviaDireita(ObserverMalhaRodovia observer) {
        super(observer);
    }
    
    @Override
    public void movimentarCarro(Carro carro){
        carro.setColuna(carro.getColuna()+1);
        System.out.println("Eu sou o "+ carro.getNomeCarro() +" e estou passando para a coluna "+ carro.getColuna() + " e linha "+ carro.getLinha());
        getObserver().notificarMovimentoCarro(carro.getLinha(), carro.getColuna()-1, carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
    }
    
    @Override
    public MalhaRodovia getProximaMalhaRodovia(Carro carro) {
        int novaColuna = carro.getColuna()+1;
        MalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        if(novaColuna < malhaRodovias[0].length && malhaRodovias[carro.getLinha()][novaColuna] != null)
            return malhaRodovias[carro.getLinha()][novaColuna];
        else{
            this.getMutex().release();
            return null;
        }
    }
}
