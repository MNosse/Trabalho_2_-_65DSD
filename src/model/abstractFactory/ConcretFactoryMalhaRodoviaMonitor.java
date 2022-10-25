package model.abstractFactory;

import model.malhas.*;
import model.observer.ObserverMalhaRodovia;

import java.util.List;

public class ConcretFactoryMalhaRodoviaMonitor extends AbstractFactoryMalhaRodovia {
    
    @Override
    public AbstractMalhaRodovia createMalhaRodovia(ObserverMalhaRodovia observer, int linha, int coluna) {
        return new MalhaRodoviaMonitor(observer, linha, coluna);
    }
    
    @Override
    public AbstractMalhaRodoviaCruzamento createMalhaRodoviaCruzamento(ObserverMalhaRodovia observer, int linha, int coluna) {
        return new MalhaRodoviaMonitorCruzamento(observer, linha, coluna);
    }
    
    @Override
    public AbstractCruzamento createCruzamento(List<AbstractMalhaRodoviaCruzamento> malhasCruzamento) {
        return new CruzamentoMonitor(malhasCruzamento);
    }
}
