package model.thread;

import model.semaforo.malhas.abstracts.MalhaRodovia;

import java.util.Random;

public class Carro extends Thread {

    private int linha;
    private int coluna;
    private String nomeCarro;
    private int tempoSleep;
    private MalhaRodovia malhaRodovia;

    public Carro(int linha, int coluna, String nomeCarro, MalhaRodovia malhaRodovia) {
        this.linha = linha;
        this.coluna = coluna;
        this.nomeCarro= nomeCarro;
        tempoSleep = new Random().nextInt(5001);
        this.malhaRodovia = malhaRodovia;
    }
    
    @Override
    public void run() {
        try {
            malhaRodovia.getMutex().acquire();
            while(true) {
                MalhaRodovia proximaMalhaRodovia = malhaRodovia.getProximaMalhaRodovia(this);
                MalhaRodovia malhaRodoviaAtual = malhaRodovia;
                if (proximaMalhaRodovia != null) {
                    proximaMalhaRodovia.getMutex().acquire();
                    setMalhaRodovia(proximaMalhaRodovia);
                    malhaRodoviaAtual.movimentarCarro(this);
                    malhaRodoviaAtual.getMutex().release();
                    sleep(tempoSleep);
                } else {
                    malhaRodoviaAtual.getMutex().release();
                    System.out.println("Eu sou o "+ getNomeCarro() +" e estou saindo da pista");
                    break;
                }
            }
        } catch (InterruptedException e) {
            malhaRodovia.getMutex().release();
        }
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public String getNomeCarro() {
        return nomeCarro;
    }

    public void setNomeCarro(String nomeCarro) {
        this.nomeCarro = nomeCarro;
    }
    
    public MalhaRodovia getMalhaRodovia() {
        return malhaRodovia;
    }
    
    public void setMalhaRodovia(MalhaRodovia malhaRodovia) {
        this.malhaRodovia = malhaRodovia;
    }
}
