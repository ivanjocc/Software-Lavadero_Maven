package org.example;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    final private JFrame frame;
    final private Register registerPanel;
    final private Search searchPanel;
    final private JPanel cards;
    final static String REGISTERPANEL = "Card with Register";
    final static String SEARCHPANEL = "Card with Search";

    public MainFrame() {
        frame = new JFrame("Ecowash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 500);
        frame.setLocationRelativeTo(null);

        // Inicializar los paneles
        registerPanel = new Register();
        searchPanel = new Search();

        // Configurar CardLayout
        cards = new JPanel(new CardLayout());
        cards.add(registerPanel.getMainPanel(), REGISTERPANEL);
        cards.add(searchPanel.getMainPanel(), SEARCHPANEL);

        frame.setContentPane(cards);

        // Configuración de botones para cambiar paneles usando CardLayout
        registerPanel.getListaBtn().addActionListener(e -> showCard(SEARCHPANEL));
        searchPanel.getIngresarBtn().addActionListener(e -> showCard(REGISTERPANEL));

        frame.setVisible(true);
    }

    private void showCard(String card) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, card);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
