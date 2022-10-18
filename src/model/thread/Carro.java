package model.thread;

import model.semaforo.malhas.abstracts.MalhaRodovia;

import java.util.Random;

public class Carro extends Thread {

    private int linha;
    private int coluna;
    private String nomeCarro;
    private int r;
    private int g;
    private int b;
    private int tempoSleep;
    private MalhaRodovia malhaRodovia;

    public Carro(int linha, int coluna, String nomeCarro, MalhaRodovia malhaRodovia) {
        this.linha = linha;
        this.coluna = coluna;
        this.nomeCarro= nomeCarro;
        tempoSleep = new Random().nextInt(5001);
        r = new Random().nextInt(256);
        g = new Random().nextInt(256);
        b = new Random().nextInt(256);
        this.malhaRodovia = malhaRodovia;
    }
    
    @Override
    public void run() {
        try {
            //Bloqueia o mutex da malha inicial
            malhaRodovia.getMutex().acquire();
            malhaRodovia.getObserver().notificarInicioCarro(linha, coluna, r, g, b);
            while(true) {
                //Recupera o mutex da malha seguinte a partir da atual
                MalhaRodovia proximaMalhaRodovia = malhaRodovia.getProximaMalhaRodovia(this);
                //Armazena o mutex da malha atual
                MalhaRodovia malhaRodoviaAtual = malhaRodovia;
                //Caso a malha seguinte não seja nula, ou seja, não saia do mapa
                if (proximaMalhaRodovia != null) {
                    //Bloqueia o mutex da malha seguinte
                    proximaMalhaRodovia.getMutex().acquire();
                    //Seta a malha seguinte como a malha do carro
                    setMalhaRodovia(proximaMalhaRodovia);
                    //Executa a ação de movimentar o carro, troca a linha ou coluna dele e imprime
                    malhaRodoviaAtual.movimentarCarro(this);
                    //Libera o mutex da malha antiga armazenada na variavel local
                    malhaRodoviaAtual.getMutex().release();
                    sleep(tempoSleep);
                }
                //Caso a malha seguinte seja nula, ou seja, saia do mapa
                else {
                    //Libera o mutex da malha atual
                    malhaRodovia.getObserver().notificarFimCarro(linha, coluna);
                    malhaRodoviaAtual.getMutex().release();
                    System.out.println("Eu sou o "+ getNomeCarro() +" e estou saindo da pista");
                    break;
                }
            }
        } catch (InterruptedException e) {
            malhaRodovia.getObserver().notificarFimCarro(linha, coluna);
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
    
    public int getR() {
        return r;
    }
    
    public int getG() {
        return g;
    }
    
    public int getB() {
        return b;
    }
}
