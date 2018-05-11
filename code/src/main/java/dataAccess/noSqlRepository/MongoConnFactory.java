package dataAccess.noSqlRepository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnFactory implements MongoConnFactoryI {

    private MongoClient mongoClient;

    public MongoConnFactory() {
        mongoClient = new MongoClient(DEFAULT_HOST, DEFAULT_PORT);
    }

    public MongoConnFactory(String host, int port) {
        mongoClient = new MongoClient(host, port);
    }

    @Override
    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DEFAULT_DB);
    }
}
