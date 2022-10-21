package model.semaforo.observer;

public interface ObserverMalhaRodovia {
    void notificarInicioCarro(int linha, int coluna, int r, int g, int b);
    void notificarMovimentoCarro(int linhaAntiga, int colunaAntiga, int linhaNova, int colunaNova, int r, int g, int b);
    void notificarFimCarro(int linha, int coluna);
}
