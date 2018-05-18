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

import static dataAccess.entity.ActivityType.*;

public class UserService extends Service {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private FolderService folderService;

    @Inject
    private ValidationService<UserService> validationService;

    @Inject
    private EmailService emailService;

    @Inject
    private PasswordService passwordService;

    @Inject
    private LogService logService;

    public UserService() {
        injector.injectMembers(this);
    }

    public UserDTO logIn(@NotNull(message = "Account username cannot be null.")String username,
                                   @NotNull(message = "Account password cannot be null.")String pass)
                                    throws Exception{
        Set<String> errors = validationService.validateMethod(this, UserService.class.getMethod("logIn", String.class, String.class), username, pass);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        Optional<User> opt = userRepository.findByUsername(username);
        if (!opt.isPresent()) throw new Exception("Error: Invalid account.");
        if (!passwordService.match(pass, opt.get().getPassword())) throw new Exception("Error: Invalid password.");
        User acc = opt.get();
        if (acc.getBlocked()) throw new Exception("Error: account blocked.");
        logService.log(acc, LOGIN);
        return new UserDTO(acc);
    }

    public UserDTO createAccount(@NotNull(message = "Account username cannot be null.")
                                        @Size(min = 3, max = 20, message = "Account username must be between 3 and 20 characters.")String username,
                                        @NotNull(message = "Account email cannot be null.")
                                        @Email(message = "Account email must be valid.")String email)
                                        throws Exception{
        Set<String> errors = validationService.validateMethod(this, UserService.class.getMethod("createAccount", String.class, String.class), username, email);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        String tempPass = passwordService.randomPass(10);
        Account acc = new Account(0, username, passwordService.hash(tempPass), email);
        accountRepository.persist(acc);
        User user = new User(acc);
        userRepository.persist(user);
        folderService.createRepo(user, 20*1024*1024L, "repository1");
        emailService.sendMail(email, "Account created", "Username: " + username + "\nPassword: " + tempPass + "\n\nPlease do no reply do this email.");
        logService.log(user, CREATEACC);
        return new UserDTO(user);
    }

    public void setBlockStatus(UserDTO dto, boolean blocked) throws Exception{
        Optional<User> opt = userRepository.find(dto.getId());
        if (!opt.isPresent()) throw new Exception("Error: Invalid account.");
        User acc = opt.get();
        acc.setBlocked(blocked);

        logService.log(acc, blocked?BLOCKED:UNBLOCKED);
    }
}
