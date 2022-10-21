package model.thread;

import model.semaforo.malhas.Cruzamento;
import model.semaforo.malhas.MalhaRodovia;
import model.semaforo.malhas.MalhaRodoviaCruzamento;
import singleton.RepositorioMalha;

import java.util.List;
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
        tempoSleep = new Random().nextInt(3001 - 1000) + 1000;
        r = new Random().nextInt(256);
        g = new Random().nextInt(256);
        b = new Random().nextInt(256);
        this.malhaRodovia = malhaRodovia;
    }
    
    @Override
    public void run() {
        MalhaRodovia proximaMalhaRodovia = null;
        MalhaRodovia malhaRodoviaAtual = malhaRodovia;
        try {
            malhaRodoviaAtual.getMutex().acquire();
            malhaRodoviaAtual.getObserver().notificarInicioCarro(linha, coluna, r, g, b);
            while(true) {
                proximaMalhaRodovia = malhaRodovia.getProximaMalhaRodovia(this);
                malhaRodoviaAtual = malhaRodovia;
                //VERIFICA SE NAO SAI DA PISTA
                if (proximaMalhaRodovia != null) {
                    //VERIFICA SE E CRUZAMENTO, CASO SEJA ELE EXECUTA O METODO MOVIMENTARCARRO DO CRUZAMENTO
                    //CUJO UTILIZA SYNCHRONIZED
                    if (proximaMalhaRodovia.getClass().equals(MalhaRodoviaCruzamento.class)) {
                        for (Cruzamento cruzamento : RepositorioMalha.getInstance().getCruzamentos()) {
                            if (cruzamento.getMalhasQueCompoe().contains((MalhaRodoviaCruzamento) proximaMalhaRodovia)) {
                                cruzamento.movimentarCarro(this);
                                malhaRodoviaAtual.getMutex().release();
                                break;
                            }
                        }
                    }
                    //COMO A MALHA NAO E CRUZAMENTO EXECUTA O METODO PADRAO DA MALHA USANDO MUTEX
                    else {
                        proximaMalhaRodovia.getMutex().acquire();
                        setMalhaRodovia(proximaMalhaRodovia);
                        malhaRodoviaAtual.movimentarCarro(this);
                        malhaRodoviaAtual.getMutex().release();
                    }
                    dormir();
                }
                else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            //NOTIFICA O OBSERVER DO CONTROLADR PARA APAGAR O CARRO
            malhaRodovia.getObserver().notificarFimCarro(linha, coluna);
            //LIBERA ATUAL
            malhaRodoviaAtual.getMutex().release();
            //LIBERA PROXIMA CASO NAO FOR NULA
            if (proximaMalhaRodovia != null) {
                proximaMalhaRodovia.getMutex().release();
            }
        } finally {
            //NOTIFICA O OBSERVER DO CONTROLADR PARA APAGAR O CARRO
            malhaRodovia.getObserver().notificarFimCarro(linha, coluna);
            //LIBERA ATUAL
            malhaRodoviaAtual.getMutex().release();
            //LIBERA PROXIMA CASO NAO FOR NULA
            if (proximaMalhaRodovia != null) {
                proximaMalhaRodovia.getMutex().release();
            }
        }
    }
    
    public void dormir() {
        try {
            sleep(tempoSleep);
        } catch(InterruptedException e) {
        
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
