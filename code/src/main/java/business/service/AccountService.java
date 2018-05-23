package business.service;

import business.dto.AccountDTO;
import com.google.inject.Inject;
import dataAccess.entity.Account;
import dataAccess.sqlRepository.AccountRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dataAccess.entity.ActivityType.*;

public class AccountService extends Service {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private EmailService emailService;

    @Inject
    private PasswordService passwordService;

    @Inject
    private ValidationService<AccountService> validationService;

    @Inject
    private LogService logService;

    public AccountService() {
        injector.injectMembers(this);
    }

    public boolean usernameInUse(@NotNull(message = "Username cannot be null.")
                                 @Size(min = 5, max = 20, message = "Account username must be between 5 and 20 characters")String username)
                                throws Exception {
        Set<String> errors = validationService.validateMethod(this, AccountService.class.getMethod("usernameInUse", String.class), username);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        List<Account> list = accountRepository.findAll();
        return list.stream().map(Account::getUsername).collect(Collectors.toList()).contains(username);
    }

    public boolean emailInUse(@NotNull(message = "Account email cannot be null.")
                              @Email(message = "Account email must be valid.")String email) throws Exception {
        Set<String> errors = validationService.validateMethod(this, AccountService.class.getMethod("emailInUse", String.class), email);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        List<Account> list = accountRepository.findAll();
        return list.stream().map(Account::getEmail).collect(Collectors.toList()).contains(email);
    }

    public AccountDTO changePass(AccountDTO dto,
                                           @NotNull(message = "Account password cannot be null.")
                                           @Size(min = 5, message = "Account password  have at least 5 characters.")String newPass)
                                            throws Exception{
        Set<String> errors = validationService.validateMethod(this, AccountService.class.getMethod("changePass", AccountDTO.class, String.class), dto, newPass);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        Optional<Account> opt = accountRepository.find(dto.getId());
        if (!opt.isPresent()) throw new Exception("Error: Invalid account.");
        Account acc = opt.get();
        acc.setPassword(passwordService.hash(newPass));
        accountRepository.update(acc);
        emailService.sendMail(dto.getEmail(), "Password changed", "Your password has been changed\n\nPlease do not reply to this email.");
        logService.log(acc, PASSCHG);
        return new AccountDTO(acc);
    }

    public AccountDTO changeEmail(AccountDTO dto,
                                            @NotNull(message = "Account email cannot be null.")
                                            @Email(message = "Account email must be valid.")String newEmail)
                                            throws Exception{
        Set<String> errors = validationService.validateMethod(this, AccountService.class.getMethod("changeEmail", AccountDTO.class, String.class), dto, newEmail);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        Optional<Account> opt = accountRepository.find(dto.getId());
        if (!opt.isPresent()) throw new Exception("Error: Invalid account.");
        if (emailInUse(newEmail)) throw new Exception("Error: Email already in use.");
        Account acc = opt.get();
        acc.setEmail(newEmail);
        accountRepository.update(acc);
        emailService.sendMail(dto.getEmail(), "Email changed", "Your email has been changed\n\nPlease do not reply to this email.");
        logService.log(acc, EMAILCHG);
        return new AccountDTO(acc);
    }

    public void deleteAccount(AccountDTO dto) throws Exception{
        Account acc = new Account(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getEmail());
        accountRepository.delete(acc);
    }
}
