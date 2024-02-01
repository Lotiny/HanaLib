package me.lotiny.libs.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Mongo {

    private final MongoCredentials credentials;

    @Getter
    private MongoClient client;
    @Getter
    private MongoDatabase database;
    @Getter
    private boolean connected = true;

    public void connect() {
        try {
            this.client = MongoClients.create(this.credentials.getUri());
            this.database = this.client.getDatabase(this.credentials.getDatabaseName());
        } catch (Exception e) {
            this.connected = false;
        }
    }

    public void disconnect() {
        if (this.client != null) {
            this.client.close();
        }
    }
}
