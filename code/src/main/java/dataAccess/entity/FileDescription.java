package dataAccess.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="file_descriptions")
public class FileDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private final Integer id;

    @ManyToOne
    @JoinColumn(name = "repo_id")
    private Folder folder;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    public FileDescription() {
        this.id = 0;
    }

    public FileDescription(Integer id, Folder folder, String name, Long size, Instant addTime) {
        this.id = id;
        this.folder = folder;
        this.name = name;
        this.size = size;
        this.addTime = addTime;
    }

    public Integer getId() {
        return id;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Instant getAddTime() {
        return addTime;
    }

    public void setAddTime(Instant addDate) {
        this.addTime = addDate;
    }

    @Override
    public String toString() {
        return "FileDescription{" +
                "id=" + id +
                ", folder=" + folder +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", addDate=" + addTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileDescription that = (FileDescription) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
