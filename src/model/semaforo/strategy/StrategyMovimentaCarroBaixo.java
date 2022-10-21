package model.semaforo.strategy;

import model.semaforo.malhas.MalhaRodovia;
import model.thread.Carro;
import singleton.RepositorioMalha;

public class StrategyMovimentaCarroBaixo extends StrategyMovimentaCarro{
    public StrategyMovimentaCarroBaixo(MalhaRodovia malhaRodovia) {
        super(malhaRodovia);
    }
    
    @Override
    public void movimentarCarro(Carro carro) {
        carro.setLinha(carro.getLinha()+1);
        System.out.println("Eu sou o "+ carro.getNomeCarro() +" e estou passando para a coluna "+ carro.getColuna() + " e linha "+ carro.getLinha());
        malhaRodovia.getObserver().notificarMovimentoCarro(carro.getLinha()-1, carro.getColuna(), carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
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
