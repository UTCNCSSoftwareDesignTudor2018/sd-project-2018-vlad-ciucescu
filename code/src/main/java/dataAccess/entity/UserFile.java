package dataAccess.entity;

import java.util.Arrays;

public class UserFile {

    private String name;
    private byte[] data;
    private String collection;

    public UserFile() {
        name = "file";
        data = new byte[]{};
        collection = "files";
    }

    public UserFile(String coll, String name, byte[] data) {
        this.name = name;
        this.data = data;
        this.collection = coll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "name='" + name + '\'' +
                ", collection='" + collection + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFile userFile = (UserFile) o;

        if (!name.equals(userFile.name)) return false;
        return collection.equals(userFile.collection);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + collection.hashCode();
        return result;
    }
}
