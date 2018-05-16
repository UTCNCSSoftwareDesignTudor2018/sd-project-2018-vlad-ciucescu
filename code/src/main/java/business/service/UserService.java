package business.service;

import business.dto.AccountDTO;
import business.dto.UserDTO;
import com.google.inject.Inject;
import dataAccess.entity.Account;
import dataAccess.entity.User;
import dataAccess.sqlRepository.AccountRepository;
import dataAccess.sqlRepository.UserRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.Set;

public class UserService implements Service {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private FolderService folderService;

    @Inject
    private ValidationService<UserService> validatorService;

    @Inject
    private EmailService emailService;

    @Inject
    private PasswordService passwordService;

    public UserService() {
    }

    public Optional<UserDTO> logIn(@NotNull(message = "Account username cannot be null.")String username,
                                   @NotNull(message = "Account password cannot be null.")String pass) {
        //validationService.validateMethod(this, UserService.class.getMethod("logIn", String.class, String.class), username, pass);
        Optional<User> opt = userRepository.findByUsername(username);
        if (!opt.isPresent()) return Optional.empty();
        if (!passwordService.match(pass, opt.get().getPassword())) return Optional.empty();
        User acc = opt.get();
        if (acc.getBlocked()) return Optional.empty();
        return opt.map(UserDTO::new);
    }

    public Optional<User> createAccount(@NotNull(message = "Account username cannot be null.")
                                        @Size(min = 3, max = 20, message = "Account username must be between 3 and 20 characters.")String username,
                                        @NotNull(message = "Account email cannot be null.")
                                        @Email(message = "Account email must be valid.")String email) {
        //validationService.validateMethod(this, UserService.class.getMethod("createAccount", String.class, String.class), username, email);
        String tempPass = passwordService.randomPass(10);
        Account acc = new Account(0, username, passwordService.hash(tempPass), email);
        accountRepository.persist(acc);
        User user = new User(acc);
        userRepository.persist(user);
        folderService.createRepo(user);
        emailService.sendMail(email, "Account created", "Username: " + username + "\nPassword: " + tempPass + "\n\nPlease do no reply do this email.");
        return Optional.of(user);
    }

    public void setBlockStatus(UserDTO dto, boolean blocked) {
        Optional<User> opt = userRepository.find(dto.getId());
        if (!opt.isPresent()) return;
        User acc = opt.get();
        acc.setBlocked(blocked);
    }
}
