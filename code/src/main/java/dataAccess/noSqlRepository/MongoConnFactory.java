package dataAccess.noSqlRepository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnFactory {

    static final String DEFAULT_HOST = "localhost";
    static final Integer DEFAULT_PORT = 27017;
    static final String DEFAULT_DB = "sd_proj";

    private MongoClient mongoClient;

    public MongoConnFactory() {
        mongoClient = new MongoClient(DEFAULT_HOST, DEFAULT_PORT);
    }

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DEFAULT_DB);
    }
}
