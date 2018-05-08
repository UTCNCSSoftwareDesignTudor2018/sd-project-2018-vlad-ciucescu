package dataAccess.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "repositories")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private final Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "repository_name")
    private String repositoryName;

    @Column(name = "max_size", nullable = false)
    private Long maxSize;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileDescription> files;

    public Folder() {
        this.id = 0;
    }

    public Folder(Integer id, User user, String repositoryName, Long maxSize) {
        this.id = id;
        this.user = user;
        this.repositoryName = repositoryName;
        this.maxSize = maxSize;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", user=" + user +
                ", repositoryName='" + repositoryName + '\'' +
                ", maxSize=" + maxSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Folder folder = (Folder) o;

        return id != null ? id.equals(folder.id) : folder.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
