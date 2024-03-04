package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Ecowash");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//                Register registerForm = new Register();
//                frame.setContentPane(registerForm.getMainPanel());

                Search registerForm = new Search();
                frame.setContentPane(registerForm.getMainPanel());

                frame.pack();
                frame.setSize(1200, 500);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}