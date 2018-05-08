package dataAccess.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="admins")
public class Admin extends Account {

    public Admin() {
        super();
    }

    public Admin(Integer id, String username, Integer password, String email) {
        super(id, username, password, email);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + this.getId() +
                ", username='" + this.getUsername() + '\'' +
                ", password=" + this.getPassword() +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}
