package org.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBHelper {
    private static final Logger logger = LoggerFactory.getLogger(MongoDBHelper.class);
    private MongoDatabase database;

    public MongoDBHelper() {
        try {
            String connectionString = "mongodb+srv://ivanjocij:lnxR0jb23nTM7zVs@ecowash.hgysnkq.mongodb.net/lavadero?retryWrites=true&w=majority&appName=Ecowash";
            MongoClient mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase("lavadero");
        } catch (Exception e) {
            logger.error("Error al conectar con la base de datos MongoDB", e);
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
