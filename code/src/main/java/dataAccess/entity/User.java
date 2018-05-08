package dataAccess.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
public class User extends Account {

    @Column(name="blocked", nullable=false)
    private Boolean blocked;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Folder> folders;

    public User() {
        super();
    }

    public User(Integer id, String username, Integer password, String email, Boolean blocked) {
        super(id, username, password, email);
        this.blocked = blocked;
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
                "id=" + this.getId() +
                ", username='" + this.getUsername() + '\'' +
                ", password=" + this.getPassword() +
                ", email='" + this.getEmail() + '\'' +
                "blocked=" + blocked +
                '}';
    }
}
