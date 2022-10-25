package model.abstractFactory;

import model.malhas.AbstractCruzamento;
import model.malhas.AbstractMalhaRodovia;
import model.malhas.AbstractMalhaRodoviaCruzamento;
import model.observer.ObserverMalhaRodovia;

import java.util.List;

public abstract class AbstractFactoryMalhaRodovia {
    public abstract AbstractMalhaRodovia createMalhaRodovia(ObserverMalhaRodovia observer, int linha, int coluna);
    public abstract AbstractMalhaRodoviaCruzamento createMalhaRodoviaCruzamento(ObserverMalhaRodovia observer, int linha, int coluna);
    public abstract AbstractCruzamento createCruzamento(List<AbstractMalhaRodoviaCruzamento> malhasCruzamento);
}
