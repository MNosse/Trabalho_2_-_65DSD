package view;

import controller.controller.ControladorTelaAbstractFactory;
import controller.controller.ControladorTelaInicial;
import controller.observer.ObserverTelaAbstractFactory;

import javax.swing.*;
import java.awt.*;

public class TelaAbstractFactory extends JFrame implements ObserverTelaAbstractFactory {
    
    private final ControladorTelaAbstractFactory CONTROLADOR;
    private JButton btnMutex;
    private JButton btnMonitor;
    
    public TelaAbstractFactory() {
        CONTROLADOR = new ControladorTelaAbstractFactory(this);
        initialize();
    }
    
    private void initialize() {
        //LAYOUT
        GridBagLayout layout = new GridBagLayout();
        //CONSTRAINTS
        GridBagConstraints constraints = new GridBagConstraints();
        //JLABEL lblTitulo
        JLabel lblTitulo = new JLabel("Escolha o tipo de exclusao mutua");
        //JBUTTON btnMutex
        btnMutex = new JButton("Mutex");
        btnMutex.setPreferredSize(new Dimension(90, 26));
        btnMutex.setMinimumSize(new Dimension(90, 26));
        //JBUTTON btnMonitor
        btnMonitor = new JButton("Monitor");
        btnMonitor.setPreferredSize(new Dimension(90, 26));
        btnMonitor.setMinimumSize(new Dimension(90, 26));
        //JPANEL panLinha
        JPanel panLinha = new JPanel();
        panLinha.setLayout(layout);
        panLinha.setSize(400, 50);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 0);
        panLinha.add(btnMutex, constraints);
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 10, 0, 0);
        panLinha.add(btnMonitor, constraints);
        
        //JPANEL panLayout
        JPanel panLayout = new JPanel();
        panLayout.setLayout(layout);
        panLayout.setSize(450, 150);
        constraints.gridx = 0;
        panLayout.add(lblTitulo, constraints);
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 0, 0);
        panLayout.add(panLinha, constraints);
        setVisible(true);
        //THIS
        setTitle("Escolha da exclusao mutua");
        setVisible(true);
        setSize(450, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panLayout);
        initializeActions();
    }
    
    private void initializeActions() {
        btnMutex.addActionListener(click -> {
            CONTROLADOR.selecionarMutex();
        });
        
        btnMonitor.addActionListener(click -> {
            CONTROLADOR.selecionarMonitor();
        });
    }
    
    @Override
    public void navegarParaTelaMalhaRodoviaria() {
        new TelaMalhaRodoviaria();
        this.dispose();
    }
}
