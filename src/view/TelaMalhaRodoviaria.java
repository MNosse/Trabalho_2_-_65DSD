package view;

import controller.controller.ControladorTelaMalhaRodoviaria;
import controller.observer.ObserverTelaMalhaRodoviaria;
import view.components.ImagemCarro;
import view.global.GlobalVariables;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class TelaMalhaRodoviaria extends JFrame implements ObserverTelaMalhaRodoviaria {
    
    private final ControladorTelaMalhaRodoviaria CONTROLADOR;
    private JLabel[][] lblsMalhaRodoviaria;
    
    public TelaMalhaRodoviaria() {
        CONTROLADOR = new ControladorTelaMalhaRodoviaria(this);
        initialize();
        CONTROLADOR.geraCarro();
    }
    
    private void initialize() {
        //LAYOUT
        GridBagLayout layout = new GridBagLayout();
        //CONSTRAINTS
        GridBagConstraints constraints = new GridBagConstraints();
        //JLABEL[][] lblsMalhaRodoviaria
        lblsMalhaRodoviaria = new JLabel[CONTROLADOR.getMalhaRodoviariaNumeros().length][CONTROLADOR.getMalhaRodoviariaNumeros()[0].length];
        for (int linha = 0; linha < lblsMalhaRodoviaria.length; linha++) {
            for (int coluna = 0; coluna < lblsMalhaRodoviaria[0].length; coluna++) {
                JLabel lblMalha = new JLabel(new ImageIcon(new ImageIcon("./src/view/assets/images/"+CONTROLADOR.getMalhaRodoviariaNumeros()[linha][coluna]+".png").getImage().getScaledInstance(GlobalVariables.LADO_QUADRADO, GlobalVariables.LADO_QUADRADO, Image.SCALE_SMOOTH)));
                lblMalha.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                lblsMalhaRodoviaria[linha][coluna] = lblMalha;
            }
        }
        //JPANEL panLayout
        JPanel panLayout = new JPanel();
        panLayout.setLayout(layout);
        panLayout.setSize(GlobalVariables.LARGURA_TELA, GlobalVariables.ALTURA_TELA);
        for (int linha = 0; linha < lblsMalhaRodoviaria.length; linha++) {
            constraints.gridy = linha;
            for (int coluna = 0; coluna < lblsMalhaRodoviaria[0].length; coluna++) {
                constraints.gridx = coluna;
                panLayout.add(lblsMalhaRodoviaria[linha][coluna], constraints);
            }
        }
        //JSCROLLPANE scpScroll
        JScrollPane scpScroll = new JScrollPane(panLayout);
        //THIS
        setTitle("Malha rodoviaria");
        setVisible(true);
        setSize(GlobalVariables.LARGURA_TELA, GlobalVariables.ALTURA_TELA);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(scpScroll);
    }
    
    @Override
    public void notificarInicioCarro(int linha, int coluna, int r, int g, int b) {
        lblsMalhaRodoviaria[linha][coluna].setIcon(new ImagemCarro((ImageIcon) lblsMalhaRodoviaria[linha][coluna].getIcon(), r, g, b));
        lblsMalhaRodoviaria[linha][coluna].revalidate();
        lblsMalhaRodoviaria[linha][coluna].repaint();
    }
    
    @Override
    public void notificarMovimentoCarro(int linhaAntiga, int colunaAntiga, int linhaNova, int colunaNova, int r, int g, int b) {
        lblsMalhaRodoviaria[linhaAntiga][colunaAntiga].setIcon(new ImageIcon(new ImageIcon("./src/view/assets/images/"+CONTROLADOR.getMalhaRodoviariaNumeros()[linhaAntiga][colunaAntiga]+".png").getImage().getScaledInstance(GlobalVariables.LADO_QUADRADO, GlobalVariables.LADO_QUADRADO, Image.SCALE_SMOOTH)));
        lblsMalhaRodoviaria[linhaAntiga][colunaAntiga].revalidate();
        lblsMalhaRodoviaria[linhaAntiga][colunaAntiga].repaint();
        lblsMalhaRodoviaria[linhaNova][colunaNova].setIcon(new ImagemCarro((ImageIcon) lblsMalhaRodoviaria[linhaNova][colunaNova].getIcon(), r, g, b));
        lblsMalhaRodoviaria[linhaNova][colunaNova].revalidate();
        lblsMalhaRodoviaria[linhaNova][colunaNova].repaint();
    }
    
    @Override
    public void notificarFimCarro(int linha, int coluna) {
        lblsMalhaRodoviaria[linha][coluna].setIcon(new ImageIcon(new ImageIcon("./src/view/assets/images/"+CONTROLADOR.getMalhaRodoviariaNumeros()[linha][coluna]+".png").getImage().getScaledInstance(GlobalVariables.LADO_QUADRADO, GlobalVariables.LADO_QUADRADO, Image.SCALE_SMOOTH)));
        lblsMalhaRodoviaria[linha][coluna].revalidate();
        lblsMalhaRodoviaria[linha][coluna].repaint();
    }
}
