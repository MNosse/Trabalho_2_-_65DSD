package singleton;

import model.semaforo.malhas.Cruzamento;
import model.semaforo.malhas.MalhaRodovia;

import java.util.ArrayList;
import java.util.List;

public class RepositorioMalha {

    private static RepositorioMalha instance;
    private int[][] malhaRodoviariaNumeros;
    private MalhaRodovia[][] malhaRodovias;
    private List<Cruzamento> cruzamentos;

    private RepositorioMalha() {
        cruzamentos = new ArrayList<>();
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
    
    public List<Cruzamento> getCruzamentos() {
        return cruzamentos;
    }
    
    public void setCruzamentos(List<Cruzamento> cruzamentos) {
        this.cruzamentos = cruzamentos;
    }
}
