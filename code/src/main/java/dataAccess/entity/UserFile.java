package dataAccess.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "files")
public class UserFile {

    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private final String id;
    private String name;
    private String extension;
    private byte[] data;

    public UserFile() {
        this.id = "";
    }

    public UserFile(String id, String name, String extension, byte[] data) {
        this.id = id;
        this.name = name;
        this.extension = extension;
        this.data = data;
    }

    public String getId() {
        return id;
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
                "id=" + id +
                ", name='" + name + '.' + extension + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFile userFile = (UserFile) o;
        return id.equals(userFile.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
