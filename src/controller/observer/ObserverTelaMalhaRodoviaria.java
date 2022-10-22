package controller.observer;

import model.thread.Carro;

public interface ObserverTelaMalhaRodoviaria {
    void notificarInicioCarro(int linha, int coluna, int r, int g, int b);
    void notificarMovimentoCarro(int linhaAntiga, int colunaAntiga, int linhaNova, int colunaNova, int r, int g, int b);
    void notificarFimCarro(int linha, int coluna);
}
