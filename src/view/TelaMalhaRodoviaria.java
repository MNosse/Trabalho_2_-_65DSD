package view;

import controller.controller.ControladorTelaMalhaRodoviaria;
import controller.observer.ObserverTelaMalhaRodoviaria;
import view.global.GlobalVariables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class TelaMalhaRodoviaria extends JFrame implements ObserverTelaMalhaRodoviaria {
    
    private final ControladorTelaMalhaRodoviaria CONTROLADOR;
    private JLabel[][] lblsMalhaRodoviaria;
    
    public TelaMalhaRodoviaria() {
        CONTROLADOR = new ControladorTelaMalhaRodoviaria(this);
        initialize();
    }
    
    public TelaMalhaRodoviaria(int[][] malhaRodoviaria) {
        CONTROLADOR = new ControladorTelaMalhaRodoviaria(this, malhaRodoviaria);
        initialize();
    }
    
    private void initialize() {
        //LAYOUT
        GridBagLayout layout = new GridBagLayout();
        //CONSTRAINTS
        GridBagConstraints constraints = new GridBagConstraints();
        //JLABEL[][] lblsMalhaRodoviaria
        lblsMalhaRodoviaria = new JLabel[CONTROLADOR.getMalhaRodoviaria().length][CONTROLADOR.getMalhaRodoviaria()[0].length];
        for (int linha = 0; linha < lblsMalhaRodoviaria.length; linha++) {
            for (int coluna = 0; coluna < lblsMalhaRodoviaria[0].length; coluna++) {
                JLabel lblMalha = new JLabel(new ImageIcon(new ImageIcon("./src/view/assets/images/"+CONTROLADOR.getMalhaRodoviaria()[linha][coluna]+".png").getImage().getScaledInstance(GlobalVariables.LADO_QUADRADO, GlobalVariables.LADO_QUADRADO, Image.SCALE_SMOOTH)));
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
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(scpScroll);
    }
}
