package dataAccess.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends Account {

    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Folder> folders;

    public User() {
        super();
    }

    public User(Integer id, String username, byte[] password, String email, Boolean blocked) {
        super(id, username, password, email);
        this.blocked = blocked;
    }

    public User(Account acc) {
        this(acc.getId(), acc.getUsername(), acc.getPassword(), acc.getEmail(), false);
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + this.getUsername() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                "blocked=" + blocked +
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
