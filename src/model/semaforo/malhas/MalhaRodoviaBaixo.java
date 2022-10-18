package model.semaforo.malhas;

import model.semaforo.observer.ObserverMalhaRodovia;
import model.thread.Carro;
import model.semaforo.malhas.abstracts.MalhaRodovia;
import singleton.RepositorioMalha;

public class MalhaRodoviaBaixo extends MalhaRodovia {
    public MalhaRodoviaBaixo(ObserverMalhaRodovia observer) {
        super(observer);
    }
    
    @Override
    public void movimentarCarro(Carro carro) {
        carro.setLinha(carro.getLinha()+1);
        System.out.println("Eu sou o "+ carro.getNomeCarro() +" e estou passando para a coluna "+ carro.getColuna() + " e linha "+ carro.getLinha());
        getObserver().notificarMovimentoCarro(carro.getLinha()-1, carro.getColuna(), carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
    }
    
    @Override
    public MalhaRodovia getProximaMalhaRodovia(Carro carro) {
        int novaLinha = carro.getLinha()+1;
        MalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        if(novaLinha < malhaRodovias.length && malhaRodovias[novaLinha][carro.getColuna()] != null)
            return malhaRodovias[novaLinha][carro.getColuna()];
        else{
            return null;
        }
    }
}
