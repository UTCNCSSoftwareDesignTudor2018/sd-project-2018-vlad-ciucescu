package dataAccess.entity;

import java.util.Arrays;

public class UserFile {

    private String name;
    private String extension;
    private byte[] data;

    public UserFile() {
        name = "file";
    }

    public UserFile(String name, String extension, byte[] data) {
        this.name = name;
        this.extension = extension;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "name='" + name + '.' + extension + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFile userFile = (UserFile) o;

        if (!name.equals(userFile.name)) return false;
        if (!extension.equals(userFile.extension)) return false;
        return Arrays.equals(data, userFile.data);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + extension.hashCode();
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
