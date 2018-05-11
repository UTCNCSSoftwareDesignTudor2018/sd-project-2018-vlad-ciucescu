package dataAccess.noSqlRepository;

import dataAccess.entity.UserFile;
import org.bson.Document;
import org.bson.types.Binary;

import java.util.Arrays;

public class UserFileAdapter {

    public UserFileAdapter() {}

    public Document getDocument(UserFile file) {
        if (file==null) return null;
        return new Document("name", file.getName())
                .append("ext", file.getExtension())
                .append("data", file.getData());
    }

    public UserFile getUserFile(Document document) {
        if (document==null) return null;
        Binary bin = document.get("data", Binary.class);
        return new UserFile(document.getString("name"),
                document.getString("ext"),
                bin.getData());
    }
}
