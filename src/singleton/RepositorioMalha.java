package singleton;

import model.semaforo.malhas.abstracts.MalhaRodovia;

public class RepositorioMalha {

    private static RepositorioMalha instance;
    private MalhaRodovia[][] factoryMalhas;

    public RepositorioMalha() {
    }

    public synchronized static RepositorioMalha getInstance(){
        if (instance == null)
            instance = new RepositorioMalha();
        return instance;
    }

    public MalhaRodovia[][] getFactoryMalhas() {
        return factoryMalhas;
    }

    public void setFactoryMalhas(MalhaRodovia[][] factoryMalhas) {
        this.factoryMalhas = factoryMalhas;
    }
}
