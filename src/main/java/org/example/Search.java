package org.example;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCollection;


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
    private JButton enviarBtn;
    private JPanel carroPanel;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public Search() {
        // Asume que initComponents() es llamado automáticamente para inicializar los componentes de UI.

        String[] columnNames = {"Placa", "Propietario", "Teléfono", "Fecha", "Hora", "Valor", "Tamaño", "Tipo de Lavado", "Adicional"};
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable.setModel(tableModel);

        cargarDatosDesdeMongoDB();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void cargarDatosDesdeMongoDB() {
        MongoDBHelper db = new MongoDBHelper();
        MongoDatabase database = db.getDatabase();
        MongoCollection<Document> collection = database.getCollection("info");

        // Limpiar el modelo de la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Recuperar todos los documentos de la colección
        for (Document doc : collection.find()) {
            Object[] rowData = new Object[]{
                    doc.getString("placa"),
                    doc.getString("propietario"),
                    doc.get("telefono", Integer.class), // Uso get() con tipo para manejar posibles nulos
                    doc.getString("fecha"),
                    doc.getString("hora"),
                    doc.get("valor", Integer.class), // Uso get() con tipo para manejar posibles nulos
                    doc.getString("tamano"),
                    doc.getString("tipo_lavado"),
                    doc.getString("adicional")
            };
            tableModel.addRow(rowData);
        }
    }
}
