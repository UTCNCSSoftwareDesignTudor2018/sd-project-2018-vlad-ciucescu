package dataAccess.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="admins")
public class Admin extends Account {

    public Admin() {
        super();
    }

    public Admin(Integer id, String username, byte[] password, String email) {
        super(id, username, password, email);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + this.getUsername() + '\'' +
                ", password=" + this.getPassword() +
                ", email='" + this.getEmail() + '\'' +
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
