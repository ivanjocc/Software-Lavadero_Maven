package org.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoDBHelper {
    private static final Logger logger = LoggerFactory.getLogger(MongoDBHelper.class);
    private MongoDatabase database;

    public MongoDBHelper() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            String connectionString = prop.getProperty("mongodb.uri");
            MongoClient mongoClient = MongoClients.create(connectionString);
            // Asume que el nombre de la base de datos está incluido en la URI
            database = mongoClient.getDatabase("lavadero"); // O extrae el nombre de la base de datos de la URI si es variable
        } catch (IOException ex) {
            logger.error("Error al cargar la configuración de MongoDB", ex);
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
