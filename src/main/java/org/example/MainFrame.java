package org.example;

import javax.swing.*;

public class MainFrame {
    final private JFrame frame;
    final private Register registerPanel;
    final private Search searchPanel;

    public MainFrame() {
        frame = new JFrame("Ecowash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 500);
        frame.setLocationRelativeTo(null);

        // Inicializar los paneles
        registerPanel = new Register();
        searchPanel = new Search();

        // Configurar el panel inicial
        frame.setContentPane(registerPanel.getMainPanel());

        // Configuración de botones para cambiar paneles
        registerPanel.getListaBtn().addActionListener(e -> showSearchPanel());
        searchPanel.getIngresarBtn().addActionListener(e -> showRegisterPanel());

        frame.setVisible(true);
    }

    private void showRegisterPanel() {
        frame.setContentPane(registerPanel.getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    private void showSearchPanel() {
        frame.setContentPane(searchPanel.getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
