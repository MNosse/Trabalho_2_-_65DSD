package singleton;

import model.abstractFactory.AbstractFactoryMalhaRodovia;
import model.malhas.AbstractCruzamento;
import model.malhas.AbstractMalhaRodovia;

import java.util.ArrayList;
import java.util.List;

public class RepositorioMalha {

    private static RepositorioMalha instance;
    private int[][] malhaRodoviariaNumeros;
    private AbstractFactoryMalhaRodovia factory;
    private AbstractMalhaRodovia[][] malhasRodovia;
    private List<AbstractCruzamento> cruzamentos;
    private List<AbstractMalhaRodovia> iniciosMalha;

    private RepositorioMalha() {
        cruzamentos = new ArrayList<>();
        iniciosMalha = new ArrayList<>();
    }

    public synchronized static RepositorioMalha getInstance(){
        if (instance == null)
            instance = new RepositorioMalha();
        return instance;
    }

    public AbstractMalhaRodovia[][] getMalhaRodovias() {
        return malhasRodovia;
    }

    public void setMalhaRodovias(AbstractMalhaRodovia[][] malhasRodovia) {
        this.malhasRodovia = malhasRodovia;
    }
    
    public int[][] getMalhaRodoviariaNumeros() {
        return malhaRodoviariaNumeros;
    }
    
    public void setMalhaRodoviariaNumeros(int[][] malhaRodoviariaNumeros) {
        this.malhaRodoviariaNumeros = malhaRodoviariaNumeros;
    }
    
    public List<AbstractCruzamento> getCruzamentos() {
        return cruzamentos;
    }
    
    public void setCruzamentos(List<AbstractCruzamento> cruzamentos) {
        this.cruzamentos = cruzamentos;
    }

    public List<AbstractMalhaRodovia> getIniciosMalha() {
        return iniciosMalha;
    }

    public void addInicioMalha(AbstractMalhaRodovia inicioMalha) {
        this.iniciosMalha.add(inicioMalha);
    }
    
    public AbstractFactoryMalhaRodovia getFactory() {
        return factory;
    }
    
    public void setFactory(AbstractFactoryMalhaRodovia factory) {
        this.factory = factory;
    }
}
