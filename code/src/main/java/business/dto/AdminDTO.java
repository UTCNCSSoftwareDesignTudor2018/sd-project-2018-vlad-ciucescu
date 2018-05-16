package business.dto;

import dataAccess.entity.Admin;

public class AdminDTO extends AccountDTO {

    public AdminDTO() {
        super();
    }

    public AdminDTO(Integer id, String username, byte[] password, String email) {
        super(id, username, password, email);
    }

    public AdminDTO(Admin admin) {
        this(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getEmail());
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "username='" + this.getUsername() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}