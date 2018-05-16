package business.dto;

import dataAccess.entity.User;

public class UserDTO extends AccountDTO {

    public UserDTO() {
        super();
    }

    public UserDTO(Integer id, String username, byte[] password, String email) {

        super(id, username, password, email);
    }

    public UserDTO(User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + this.getUsername() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }

}