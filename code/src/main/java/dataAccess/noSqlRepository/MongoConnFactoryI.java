package dataAccess.noSqlRepository;

import com.mongodb.client.MongoDatabase;

public interface MongoConnFactoryI {

    static final String DEFAULT_HOST = "localhost";
    static final Integer DEFAULT_PORT = 27017;
    static final String DEFAULT_DB = "sd_proj";

    public MongoDatabase getDatabase();
}
