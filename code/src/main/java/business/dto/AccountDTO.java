package business.dto;

import dataAccess.entity.Account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;

public class AccountDTO extends DataTransferObject {

    private final String username;
    private final byte[] password;
    private final String email;

    public AccountDTO() {
        this.username = "username";
        this.password = new byte[]{};
        this.email = "email";
    }

    public AccountDTO(Integer id, String username, byte[] password, String email) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public AccountDTO(Account acc){
        this(acc.getId(), acc.getUsername(), acc.getPassword(), acc.getEmail());
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountDTO that = (AccountDTO) o;

        return username.equals(that.username) && Arrays.equals(password, that.password) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + Arrays.hashCode(password);
        result = 31 * result + email.hashCode();
        return result;
    }
}