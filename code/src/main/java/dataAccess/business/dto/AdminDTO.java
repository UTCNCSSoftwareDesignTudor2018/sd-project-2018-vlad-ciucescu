package dataAccess.business.dto;

public class AdminDTO extends AccountDTO {

    public AdminDTO() {
        super();
    }

    public AdminDTO(String username, String password, String email) {
        super(username, password, email);
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "username='" + this.getUsername() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}
