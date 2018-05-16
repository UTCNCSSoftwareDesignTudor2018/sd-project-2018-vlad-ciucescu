package business.service;

import business.dto.AdminDTO;
import com.google.inject.Inject;
import dataAccess.entity.Account;
import dataAccess.entity.Admin;
import dataAccess.sqlRepository.AdminRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class AdminService implements Service {

    @Inject
    private ValidationService<AdminService> validationService;

    @Inject
    private AdminRepository adminRepository;

    @Inject
    private PasswordService passwordService;

    public AdminService() {
    }

    public Optional<AdminDTO> logIn(@NotNull(message = "Account username cannot be null.")String username,
                                    @NotNull(message = "Account password cannot be null.")String pass) {
        //validationService.validateMethod(this, AdminService.class.getMethod("logIn", String.class, String.class), username, pass);
        Optional<Admin> opt = adminRepository.findByUsername(username);
        if (!opt.isPresent()) return Optional.empty();
        if (!passwordService.match(pass, opt.get().getPassword())) return Optional.empty();
        return opt.map(AdminDTO::new);
    }
}
