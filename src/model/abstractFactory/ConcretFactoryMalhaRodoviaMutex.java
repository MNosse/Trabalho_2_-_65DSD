package model.abstractFactory;

import model.malhas.*;
import model.observer.ObserverMalhaRodovia;

import java.util.List;

public class ConcretFactoryMalhaRodoviaMutex extends AbstractFactoryMalhaRodovia {
    
    @Override
    public AbstractMalhaRodovia createMalhaRodovia(ObserverMalhaRodovia observer, int linha, int coluna) {
        return new MalhaRodoviaMutex(observer, linha, coluna);
    }
    
    @Override
    public AbstractMalhaRodoviaCruzamento createMalhaRodoviaCruzamento(ObserverMalhaRodovia observer, int linha, int coluna) {
        return new MalhaRodoviaMutexCruzamento(observer, linha, coluna);
    }
    
    @Override
    public AbstractCruzamento createCruzamento(List<AbstractMalhaRodoviaCruzamento> malhasCruzamento) {
        return new CruzamentoMutex(malhasCruzamento);
    }
}
