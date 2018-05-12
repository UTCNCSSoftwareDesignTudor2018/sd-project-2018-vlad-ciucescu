package business.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountDTO implements DataTransferObject {

    @NotNull(message = "Account username cannot be null.")
    @Size(min = 3, max = 20, message = "Account username must be between 3 and 20 characters.")
    private final String username;

    @NotNull(message = "Account password cannot be null.")
    @Size(min = 5, message = "Account password  have at least 5 characters.")
    private final String password;

    @NotNull(message = "Account email cannot be null.")
    @Email(message = "Account email must be valid.")
    private final String email;

    public AccountDTO() {
        this.username = "username";
        this.password = "password";
        this.email = "email";
    }

    public AccountDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountDTO that = (AccountDTO) o;

        return username.equals(that.username) && password.equals(that.password) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
