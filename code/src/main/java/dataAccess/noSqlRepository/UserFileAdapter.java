package dataAccess.noSqlRepository;

import dataAccess.entity.UserFile;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserFileAdapter {

    public UserFileAdapter() {
    }

    public Document getDocument(UserFile file) {
        if (file == null) return null;
        return new Document("name", file.getName())
                .append("data", file.getData());
    }

    public UserFile getUserFile(Document document, String collection) {
        if (document == null) return null;
        Binary bin = document.get("data", Binary.class);
        return new UserFile(collection, document.getString("name"), bin.getData());
    }

    public UserFile getUserFile(Path path, String collection) throws Exception{
        try {
            byte[] data = Files.readAllBytes(path);
            return new UserFile(collection, path.getFileName().toString(), data);
        } catch (Exception e) {
            throw new Exception("Error: unable to read file.");
        }
    }
}
