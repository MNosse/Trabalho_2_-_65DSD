package model.semaforo.strategy;

import model.semaforo.malhas.MalhaRodovia;
import model.thread.Carro;
import singleton.RepositorioMalha;

public class StrategyMovimentaCarroDireita extends StrategyMovimentaCarro{
    public StrategyMovimentaCarroDireita(MalhaRodovia malhaRodovia) {
        super(malhaRodovia);
    }
    
    @Override
    public void movimentarCarro(Carro carro) {
        carro.setColuna(carro.getColuna()+1);
        System.out.println("Eu sou o "+ carro.getNomeCarro() +" e estou passando para a coluna "+ carro.getColuna() + " e linha "+ carro.getLinha());
        malhaRodovia.getObserver().notificarMovimentoCarro(carro.getLinha(), carro.getColuna()-1, carro.getLinha(), carro.getColuna(), carro.getR(), carro.getG(), carro.getB());
    }
    
    @Override
    public MalhaRodovia getProximaMalhaRodovia(Carro carro) {
        int novaColuna = carro.getColuna()+1;
        MalhaRodovia[][] malhaRodovias = RepositorioMalha.getInstance().getMalhaRodovias();
        if(novaColuna < malhaRodovias[0].length && malhaRodovias[carro.getLinha()][novaColuna] != null)
            return malhaRodovias[carro.getLinha()][novaColuna];
        else{

            return null;
        }
    }
}
