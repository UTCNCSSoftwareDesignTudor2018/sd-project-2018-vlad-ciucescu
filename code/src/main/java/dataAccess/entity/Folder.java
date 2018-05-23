package dataAccess.entity;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "repositories")
public class Folder extends DataEntity {

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
        super();
    }

    public Folder(Integer id, User user, String repositoryName, Long maxSize) {
        super(id);
        this.user = user;
        this.repositoryName = repositoryName;
        this.maxSize = maxSize;
        this.files = new HashSet<>();
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
                "user=" + user +
                ", repositoryName='" + repositoryName + '\'' +
                ", maxSize=" + maxSize +
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
