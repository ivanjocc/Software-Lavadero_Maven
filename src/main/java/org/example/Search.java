package org.example;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Search {
    private JPanel mainPanel;
    private JPanel btnPanel;
    private JButton ingresarBtn;
    private JButton listaBtn;
    private JButton cerrarBtn;
    private JPanel infoPanel;
    private JPanel detallesPanel;
    private JTextField placaTxt;
    private JTextField propietarioTxt;
    private JTextField fechaTxt;
    private JButton buscarBtn;
    private JPanel carroPanel;
    private JTable datasTable;
    final private DefaultTableModel tableModel;

    public Search() {
        String[] columnNames = {"Placa", "Propietario", "Teléfono", "Fecha", "Hora", "Valor", "Tamaño", "Tipo de Lavado", "Adicional"};
        tableModel = new DefaultTableModel(columnNames, 0);
        datasTable.setModel(tableModel);

        // Cargar todos los datos al inicio
        cargarDatosDesdeMongoDB();

        // Función del botón de buscar
        buscarBtn.addActionListener(e -> {
            String placa = placaTxt.getText().trim();
            String propietario = propietarioTxt.getText().trim();
            String fecha = fechaTxt.getText().trim();
            cargarDatosDesdeMongoDB(placa, propietario, fecha);
        });

        // Listener para cerrar al dar click al boton "Cerrar"
        cerrarBtn.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    public JButton getIngresarBtn() {
        return ingresarBtn;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Método sobrecargado para cargar todos los datos al inicio
    private void cargarDatosDesdeMongoDB() {
        cargarDatosDesdeMongoDB("", "", "");
    }

    private void cargarDatosDesdeMongoDB(String placa, String propietario, String fecha) {
        MongoDBHelper db = new MongoDBHelper();
        MongoDatabase database = db.getDatabase();
        MongoCollection<Document> collection = database.getCollection("info");

        Document query = new Document();
        if (!placa.isEmpty()) {
            query.append("placa", placa);
        }
        if (!propietario.isEmpty()) {
            query.append("propietario", propietario);
        }
        if (!fecha.isEmpty()) {
            query.append("fecha", fecha);
        }

        // Limpiar el modelo de la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Recuperar documentos que coincidan con los criterios de búsqueda
        for (Document doc : collection.find(query)) {
            Object[] rowData = new Object[]{
                    doc.getString("placa"),
                    doc.getString("propietario"),
                    doc.get("telefono", Integer.class), // Uso de get() con tipo para manejar posibles nulos
                    doc.getString("fecha"),
                    doc.getString("hora"),
                    doc.get("valor", Integer.class), // Uso de get() con tipo para manejar posibles nulos
                    doc.getString("tamano"),
                    doc.getString("tipo_lavado"),
                    doc.getString("adicional")
            };
            tableModel.addRow(rowData);
        }
    }
}
