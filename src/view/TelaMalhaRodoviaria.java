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
    private JLabel lblNumeroThreadAtual;
    
    public TelaMalhaRodoviaria() {
        CONTROLADOR = new ControladorTelaMalhaRodoviaria(this);
        initialize();
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
        //JLabel lblTituloNumeroThreadMaximo
        JLabel lblTituloNumeroThreadMaximo = new JLabel();
        lblTituloNumeroThreadMaximo.setText("Máximo de threads");
        lblTituloNumeroThreadMaximo.setPreferredSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        lblTituloNumeroThreadMaximo.setMinimumSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        //JLabel lblTituloNumeroThreadAtual
        JLabel lblTituloNumeroThreadAtual = new JLabel();
        lblTituloNumeroThreadAtual.setText("Threads em funcionamento");
        lblTituloNumeroThreadAtual.setPreferredSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        lblTituloNumeroThreadAtual.setMinimumSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        //JBUTTON btnIniciar
        JButton btnIniciar = new JButton();
        btnIniciar.setText("INICIAR SIMULAÇÃO");
        btnIniciar.setPreferredSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        btnIniciar.setMinimumSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        //JBUTTON btnEncerrar
        JButton btnEncerrar = new JButton();
        btnEncerrar.setText("ENCERRAR SIMULAÇÃO");
        btnEncerrar.setPreferredSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        btnEncerrar.setMinimumSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        btnEncerrar.setEnabled(false);
        //JTextField txtNumeroThreads
        JTextField txtNumeroThreads = new JTextField();
        txtNumeroThreads.setPreferredSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        txtNumeroThreads.setMinimumSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        //JLabel lblTituloNumeroThreadAtual
        lblNumeroThreadAtual = new JLabel();
        lblNumeroThreadAtual.setText("0");
        lblNumeroThreadAtual.setPreferredSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        lblNumeroThreadAtual.setMinimumSize(new Dimension(GlobalVariables.LARGURA_TELA/6, GlobalVariables.LADO_QUADRADO));
        //JPANEL panLinhaBotoes
        JPanel panLinhasBotoes = new JPanel();
        panLinhasBotoes.setLayout(layout);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, GlobalVariables.MARGEM_BOTOES, 0, 0);
        panLinhasBotoes.add(lblTituloNumeroThreadMaximo, constraints);
        constraints.gridx = 3;
        panLinhasBotoes.add(lblTituloNumeroThreadAtual, constraints);
        constraints.insets = new Insets(0, 0, GlobalVariables.MARGEM_BOTOES, 0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panLinhasBotoes.add(btnIniciar, constraints);
        constraints.insets = new Insets(0, GlobalVariables.MARGEM_BOTOES, GlobalVariables.MARGEM_BOTOES, 0);
        constraints.gridx = 1;
        panLinhasBotoes.add(btnEncerrar, constraints);
        constraints.gridx = 2;
        panLinhasBotoes.add(txtNumeroThreads, constraints);
        constraints.gridx = 3;
        panLinhasBotoes.add(lblNumeroThreadAtual, constraints);
        constraints.insets = new Insets(0, 0, 0, 0);
        //JPANEL panMalha
        JPanel panMalha = new JPanel();
        panMalha.setLayout(layout);
        for (int linha = 0; linha < lblsMalhaRodoviaria.length; linha++) {
            constraints.gridy = linha;
            for (int coluna = 0; coluna < lblsMalhaRodoviaria[0].length; coluna++) {
                constraints.gridx = coluna;
                panMalha.add(lblsMalhaRodoviaria[linha][coluna], constraints);
            }
        }
        //JPANEL panLayout
        JPanel panLayout = new JPanel();
        panLayout.setLayout(layout);
        panLayout.setSize(GlobalVariables.LARGURA_TELA, GlobalVariables.ALTURA_TELA);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panLayout.add(panLinhasBotoes, constraints);
        constraints.gridy = 1;
        panLayout.add(panMalha, constraints);
        
        btnIniciar.addActionListener(e -> {
            CONTROLADOR.onIniciar(txtNumeroThreads.getText().toString());
            btnEncerrar.setEnabled(true);
            btnIniciar.setEnabled(false);
        });
        btnEncerrar.addActionListener(e -> {
            CONTROLADOR.onEncerrarCarros();
            btnIniciar.setEnabled(true);
            btnEncerrar.setEnabled(false);
        });
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
    
    public synchronized void aumentarlblNumeroThreadAtual() {
        lblNumeroThreadAtual.setText(String.valueOf(Integer.parseInt(lblNumeroThreadAtual.getText())+1));
    }
    
    public synchronized void diminuirlblNumeroThreadAtual() {
        lblNumeroThreadAtual.setText(String.valueOf(Math.max(Integer.parseInt(lblNumeroThreadAtual.getText()) - 1, 0)));
    }
    
    @Override
    public void notificarInicioCarro(int linha, int coluna, int r, int g, int b) {
        lblsMalhaRodoviaria[linha][coluna].setIcon(new ImagemCarro((ImageIcon) lblsMalhaRodoviaria[linha][coluna].getIcon(), r, g, b));
        lblsMalhaRodoviaria[linha][coluna].revalidate();
        lblsMalhaRodoviaria[linha][coluna].repaint();
        aumentarlblNumeroThreadAtual();
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
        diminuirlblNumeroThreadAtual();
    }
}
