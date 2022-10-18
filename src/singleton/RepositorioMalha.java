package singleton;

import model.semaforo.malhas.abstracts.MalhaRodovia;

public class RepositorioMalha {

    private static RepositorioMalha instance;
    private int[][] malhaRodoviariaNumeros;
    private MalhaRodovia[][] malhaRodovias;

    public RepositorioMalha() {
    }

    public synchronized static RepositorioMalha getInstance(){
        if (instance == null)
            instance = new RepositorioMalha();
        return instance;
    }

    public MalhaRodovia[][] getMalhaRodovias() {
        return malhaRodovias;
    }

    public void setMalhaRodovias(MalhaRodovia[][] malhaRodovias) {
        this.malhaRodovias = malhaRodovias;
    }
    
    public int[][] getMalhaRodoviariaNumeros() {
        return malhaRodoviariaNumeros;
    }
    
    public void setMalhaRodoviariaNumeros(int[][] malhaRodoviariaNumeros) {
        this.malhaRodoviariaNumeros = malhaRodoviariaNumeros;
    }
}
