package view;

import controller.controller.ControladorTelaInicial;
import controller.observer.ObserverTelaInicial;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame implements ObserverTelaInicial {
    
    private final ControladorTelaInicial CONTROLADOR;
    private JTextField txtCaminho;
    private JButton btnProcurar;
    private JButton btnIniciar;
    private JFileChooser jfcArquivo;
    
    public TelaInicial() {
        CONTROLADOR = new ControladorTelaInicial(this);
        initialize();
    }
    
    private void initialize() {
        //LAYOUT
        GridBagLayout layout = new GridBagLayout();
        //CONSTRAINTS
        GridBagConstraints constraints = new GridBagConstraints();
        //JLABEL lblTitulo
        JLabel lblTitulo = new JLabel("Escolha o arquivo de texto da malha rodoviaria");
        //JTEXTFIELD txtCaminho
        txtCaminho = new JTextField();
        txtCaminho.setText("Selecione um arquivo de malha rodoviaria");
        txtCaminho.setPreferredSize(new Dimension(310, 26));
        txtCaminho.setMinimumSize(new Dimension(310, 26));
        txtCaminho.setEnabled(false);
        //JBUTTON btnProcurar
        btnProcurar = new JButton("Procurar");
        btnProcurar.setPreferredSize(new Dimension(90, 26));
        btnProcurar.setMinimumSize(new Dimension(90, 26));
        //JPANEL panLinhaBusca
        JPanel panLinhaBusca = new JPanel();
        panLinhaBusca.setLayout(layout);
        panLinhaBusca.setSize(400, 50);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 0);
        panLinhaBusca.add(txtCaminho, constraints);
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 10, 0, 0);
        panLinhaBusca.add(btnProcurar, constraints);
        //JBUTTON btnIniciar
        btnIniciar = new JButton("Iniciar");
        btnIniciar.setPreferredSize(new Dimension(90, 26));
        btnIniciar.setMinimumSize(new Dimension(90, 26));
        btnIniciar.setEnabled(false);
        //JFILECHOOSER jfcArquivo
        jfcArquivo = new JFileChooser();
        jfcArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //JPANEL panLayout
        JPanel panLayout = new JPanel();
        panLayout.setLayout(layout);
        panLayout.setSize(450, 150);
        constraints.gridx = 0;
        panLayout.add(lblTitulo, constraints);
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 0, 0);
        panLayout.add(panLinhaBusca, constraints);
        constraints.gridy = 2;
        constraints.insets = new Insets(10, 320, 0, 0);
        panLayout.add(btnIniciar, constraints);
        setVisible(true);
        //THIS
        setTitle("Escolha a malha rodoviaria");
        setVisible(true);
        setSize(450, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panLayout);
        initializeActions();
    }
    
    private void initializeActions() {
        btnProcurar.addActionListener(click -> {
            int i= jfcArquivo.showSaveDialog(null);
            if (i==1){
                txtCaminho.setText("Selecione um arquivo de malha rodoviaria");
                btnIniciar.setEnabled(false);
            } else {
                CONTROLADOR.atualizarConteudoMalhaRodoviariaNumeros(jfcArquivo.getSelectedFile());
            }
        });
        
        btnIniciar.addActionListener(click -> {
            CONTROLADOR.navegarParaTelaMalhaRodoviariaNumeros();
        });
    }
    
    @Override
    public void ativarBotaoIniciar() {
        btnIniciar.setEnabled(true);
    }
    
    @Override
    public void atualizarTxtCaminho(String caminho) {
        txtCaminho.setText(caminho);
    }
    
    @Override
    public void alertarErroNoArquivo() {
        JOptionPane.showMessageDialog(null, "O arquivo selecionado não possui o padrão adequado de uma malha rodoviaria",
                "Erro no arquivo", JOptionPane.ERROR_MESSAGE);
        btnIniciar.setEnabled(false);
    }
    
    @Override
    public void navegarParaTelaMalhaRodoviaria() {
        new TelaMalhaRodoviaria();
        this.dispose();
    }
}
