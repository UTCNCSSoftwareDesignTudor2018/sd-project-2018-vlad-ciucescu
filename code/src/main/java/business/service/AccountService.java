package business.service;

import business.dto.AccountDTO;
import com.google.inject.Inject;
import dataAccess.entity.Account;
import dataAccess.sqlRepository.AccountRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.Set;

public class AccountService implements Service {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private EmailService emailService;

    @Inject
    private PasswordService passwordService;

    @Inject
    private ValidationService<AccountService> validationService;

    public AccountService() {}

    public boolean validatePass(byte[] actual, String pass) {
        return passwordService.match(pass, actual);
    }

    public Optional<AccountDTO> changePass(AccountDTO dto,
                                           @NotNull(message = "Account password cannot be null.")
                                           @Size(min = 5, message = "Account password  have at least 5 characters.")String newPass) {
        //validationService.validateMethod(this, Account.class.getMethod("changePass", AccountDTO.class, String.class), dto, newPass);
        Optional<Account> opt = accountRepository.find(dto.getId());
        if (!opt.isPresent()) return Optional.empty();
        Account acc = opt.get();
        acc.setPassword(passwordService.hash(newPass));
        Optional<Account> accOpt = accountRepository.update(acc);
        emailService.sendMail(dto.getEmail(), "Password changed", "Your password has been changed\n\nPlease do not reply to this email.");
        return accOpt.map(AccountDTO::new);
    }

    public Optional<AccountDTO> changeEmail(AccountDTO dto,
                                            @NotNull(message = "Account email cannot be null.")
                                            @Email(message = "Account email must be valid.")String newEmail) {
        //validationService.validateMethod(this, Account.class.getMethod("changeEmail", AccountDTO.class, String.class), dto, newEmail);
        Optional<Account> opt = accountRepository.find(dto.getId());
        if (!opt.isPresent()) return Optional.empty();
        Account acc = opt.get();
        acc.setEmail(newEmail);
        Optional<Account> accOpt = accountRepository.update(acc);
        emailService.sendMail(dto.getEmail(), "Email changed", "Your email has been changed\n\nPlease do not reply to this email.");
        return accOpt.map(AccountDTO::new);
    }

    public void deleteAccount(AccountDTO dto) {
        Account acc = new Account(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getEmail());
        accountRepository.delete(acc);
    }
}
