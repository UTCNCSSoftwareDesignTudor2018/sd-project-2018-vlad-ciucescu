package dataAccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class UserFile extends DataEntity {

    private String name;
    private String extension;
    private byte[] data;

    public UserFile() {
        super();
    }

    public UserFile(Integer id, String name, String extension, byte[] data) {
        super(id);
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
        return this == o || o != null && getClass() == o.getClass() && super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
