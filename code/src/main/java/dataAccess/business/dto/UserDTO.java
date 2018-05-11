package dataAccess.business.dto;

public class UserDTO extends AccountDTO {

    public UserDTO() {
        super();
    }

    public UserDTO(String username, String password, String email) {
        super(username, password, email);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + this.getUsername() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }

}
