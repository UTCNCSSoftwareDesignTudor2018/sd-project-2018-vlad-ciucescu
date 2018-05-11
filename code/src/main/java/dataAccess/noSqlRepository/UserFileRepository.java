package dataAccess.noSqlRepository;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dataAccess.entity.UserFile;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static com.mongodb.client.model.Filters.*;

public class UserFileRepository implements MongoRepository {

    @Inject
    private MongoConnFactoryI connFactory;

    private UserFileAdapter adapter = new UserFileAdapter();

    private String collection = "files";

    @Override
    public void setCollection(String coll) {
        this.collection = coll;
    }

    private MongoCollection<Document> getArticleCollection() {
        MongoDatabase db = connFactory.getDatabase();
        return db.getCollection(collection);
    }

    public void persist(UserFile obj) {
        try {
			Optional<UserFile> opt = find(obj.getName(), obj.getExtension());
            if (opt.isPresent()) return;
            Document document = adapter.getDocument(obj);
            getArticleCollection().insertOne(document);
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User file persist exception: " + e.toString(), e );
        }
    }

    public void update(String name, String ext, UserFile obj) {
        UserFile user;
        Optional<UserFile> userOptional = Optional.empty();
        try {
            getArticleCollection().replaceOne(and(eq("name", name), eq("ext", ext)), adapter.getDocument(obj));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "User file update exception: " + e.toString(), e);
        }
    }

    public Optional<UserFile> find(String name, String ext) {
        Optional<UserFile> userOptional = Optional.empty();
        try {
            Document document = getArticleCollection().find(and(eq("name", name), eq("ext", ext))).first();
            userOptional = Optional.ofNullable(adapter.getUserFile(document));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "User file find exception: " + e.toString(), e);
        }
        return userOptional;
    }

    public List<UserFile> findAll() {
        List<UserFile> userFiles = new ArrayList<>();
        try {
            Block<Document> addToList = (d->userFiles.add(adapter.getUserFile(d)));
            getArticleCollection().find().forEach(addToList);
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User file find all exception: " + e.toString(), e );
        }
        return userFiles;
    }

    public void delete(UserFile obj) {
        try {
            Document document = adapter.getDocument(obj);
            getArticleCollection().deleteOne(document);
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User file delete exception: " + e.toString(), e );
        }
    }
}
