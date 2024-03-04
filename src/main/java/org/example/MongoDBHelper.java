package org.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBHelper {
    private MongoDatabase database;

    public MongoDBHelper() {
        try {
            String connectionString = "mongodb+srv://ivanjocij:lnxR0jb23nTM7zVs@ecowash.hgysnkq.mongodb.net/lavadero?retryWrites=true&w=majority&appName=Ecowash";
            MongoClient mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase("lavadero");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
