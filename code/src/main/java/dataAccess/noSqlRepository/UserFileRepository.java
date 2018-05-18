package dataAccess.noSqlRepository;

import com.google.inject.Inject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dataAccess.entity.UserFile;
import dataAccess.sqlRepository.AccountRepository;
import org.bson.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class UserFileRepository {

    @Inject
    private MongoConnFactory connFactory;

    @Inject
    private UserFileAdapter adapter;

    public UserFileRepository() {
    }

    private MongoCollection<Document> getArticleCollection(String collection) {
        MongoDatabase db = connFactory.getDatabase();
        return db.getCollection(collection);
    }

    public void persist(UserFile file) throws Exception {
        try {
            Document document = adapter.getDocument(file);
            getArticleCollection(file.getCollection()).insertOne(document);
        } catch (Exception e) {
            throw new Exception("User file persist exception: " + e.toString(), e);
        }
    }

    public void update(String name, UserFile file) throws Exception {
        try {
            getArticleCollection(file.getCollection()).replaceOne(eq("name", name), adapter.getDocument(file));
        } catch (Exception e) {
            throw new Exception("User file update exception: " + e.toString(), e);
        }
    }

    public Optional<UserFile> find(String name, String collection) throws Exception{
        Optional<UserFile> userOptional = Optional.empty();
        try {
            Document document = getArticleCollection(collection).find(eq("name", name)).first();
            userOptional = Optional.ofNullable(adapter.getUserFile(document, collection));
        } catch (Exception e) {
            throw new Exception("User file find exception: " + e.toString(), e);
        }
        return userOptional;
    }

    public List<UserFile> findAll(String collection) throws Exception{
        List<UserFile> userFiles = new ArrayList<>();
        try {
            Block<Document> addToList = (d -> userFiles.add(adapter.getUserFile(d, collection)));
            getArticleCollection(collection).find().forEach(addToList);
        } catch (Exception e) {
            throw new Exception("User file find all exception: " + e.toString(), e);
        }
        return userFiles;
    }

    public void delete(UserFile file) throws Exception {
        try {
            Document document = adapter.getDocument(file);
            getArticleCollection(file.getCollection()).deleteOne(document);
        } catch (Exception e) {
            throw new Exception("User file delete exception: " + e.toString(), e);
        }
    }

    public void deleteCollection(String collection) throws Exception {
        try {
            getArticleCollection(collection).drop();
        } catch (Exception e) {
            throw new Exception("Delete collection exception: " + e.toString(), e);
        }
    }
}
