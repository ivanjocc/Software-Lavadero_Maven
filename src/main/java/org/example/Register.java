package org.example;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Register {
    private JPanel mainPanel;
    private JButton ingresarBtn;
    private JButton listaBtn;
    private JButton cerrarBtn;
    private JRadioButton pequenoRb;
    private JRadioButton medianoRb;
    private JRadioButton grandeRb;
    private JRadioButton sencilloRb;
    private JRadioButton completoRb;
    private JRadioButton siRb;
    private JRadioButton noRb;
    private JTextField siTxt;
    private JRadioButton otroRb;
    private JTextField placaTxt;
    private JTextField propietarioTxt;
    private JTextField telefonoTxt;
    private JTextField fechaTxt;
    private JTextField valorTxt;
    private JButton sendBtn;
    private JPanel btnPanel;
    private JPanel infoPanel;
    private JPanel detallesPanel;
    private JPanel carroPanel;
    private JPanel tamanoPanel;
    private JPanel lavadoPanel;
    private JPanel adicionalPanel;
    private JTextField hourTxt;

    public Register() {
//        Listener para enviar la info a la base de datos
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enviarDatos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al enviar datos: " + ex.getMessage());
                }
            }
        });

//        Listener para cerrar al dar click al boton "Cerrar"
        cerrarBtn.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });

//        Grupo de radiobutton, para que solo pueda seleccionar uno
        ButtonGroup grupoTamano = new ButtonGroup();
        grupoTamano.add(pequenoRb);
        grupoTamano.add(medianoRb);
        grupoTamano.add(grandeRb);
        grupoTamano.add(otroRb);

        ButtonGroup grupoLavado = new ButtonGroup();
        grupoLavado.add(sencilloRb);
        grupoLavado.add(completoRb);

        ButtonGroup grupoAdicional = new ButtonGroup();
        grupoAdicional.add(siRb);
        grupoAdicional.add(noRb);

        pequenoRb.setSelected(true);
        sencilloRb.setSelected(true);
        noRb.setSelected(true);

//        Fecha de hoy
        fechaTxt.setText(LocalDate.now().toString());

//        Listener para ocultar el form para ingresar la info
        listaBtn.addActionListener(e -> {
            mainPanel.setVisible(false);
        });

//        Hora actual
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        hourTxt.setText(formatter.format(now));

    }

    public JButton getListaBtn() {
        return listaBtn;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

//    Funcion para enviar info a la base de datos
private void enviarDatos() {
    // Verificar si los campos de placa y valor no están vacíos
    if (placaTxt.getText().trim().isEmpty() || valorTxt.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Error: los campos de placa y valor son obligatorios.");
        return;
    }

    // Intentar convertir el valor a número, si es necesario, y enviar datos
    try {
        MongoDBHelper db = new MongoDBHelper();
        MongoDatabase database = db.getDatabase();
        MongoCollection<Document> collection = database.getCollection("info");

        // Convertir el valor del teléfono a entero solo si no está vacío
        Integer telefono = telefonoTxt.getText().trim().isEmpty() ? null : Integer.parseInt(telefonoTxt.getText());
        Integer valor = Integer.parseInt(valorTxt.getText().trim());

        Document doc = new Document("placa", placaTxt.getText().trim())
                .append("propietario", propietarioTxt.getText().trim())
                .append("telefono", telefono)
                .append("fecha", fechaTxt.getText().trim())
                .append("hora", hourTxt.getText().trim())
                .append("valor", valor)
                .append("tamano", obtenerTamano())
                .append("tipo_lavado", obtenerTipoLavado())
                .append("adicional", obtenerAdicional());

        collection.insertOne(doc);

        resetFormulario();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Error: hay un campo no válido.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al enviar datos: " + e.getMessage());
    }
}

    // Método para obtener el tamaño seleccionado
    private String obtenerTamano() {
        if (pequenoRb.isSelected()) return "pequeño";
        if (medianoRb.isSelected()) return "mediano";
        if (grandeRb.isSelected()) return "grande";
        if (otroRb.isSelected()) return "otro";
        return null; //
    }

    // Método para obtener el tipo de lavado seleccionado
    private String obtenerTipoLavado() {
        if (sencilloRb.isSelected()) return "sencillo";
        if (completoRb.isSelected()) return "completo";
        return null;
    }

    // Método para obtener el adicional seleccionado
    private String obtenerAdicional() {
        if (siRb.isSelected()) return siTxt.getText();
        if (noRb.isSelected()) return "no";
        return null;
    }

//    Funcion para reiniciar el formulario
    private void resetFormulario() {
        placaTxt.setText("");
        propietarioTxt.setText("");
        telefonoTxt.setText("");
        fechaTxt.setText(LocalDate.now().toString());
        valorTxt.setText("");

        pequenoRb.setSelected(true);
        sencilloRb.setSelected(true);
        noRb.setSelected(true);

        siTxt.setText("");
    }
}
