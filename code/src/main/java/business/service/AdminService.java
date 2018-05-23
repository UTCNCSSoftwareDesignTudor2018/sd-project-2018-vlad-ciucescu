package business.service;

import business.dto.AdminDTO;
import com.google.inject.Inject;
import dataAccess.entity.Admin;
import dataAccess.sqlRepository.AdminRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;
import static dataAccess.entity.ActivityType.*;

public class AdminService extends Service {

    @Inject
    private ValidationService<AdminService> validationService;

    @Inject
    private AdminRepository adminRepository;

    @Inject
    private PasswordService passwordService;

    @Inject
    private LogService logService;

    public AdminService() {
        injector.injectMembers(this);
    }

    public AdminDTO logIn(@NotNull(message = "Account username cannot be null.")String username,
                                    @NotNull(message = "Account password cannot be null.")char[] pass)
                                    throws Exception{
        Set<String> errors = validationService.validateMethod(this, AdminService.class.getMethod("logIn", String.class, char[].class), username, pass);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        Optional<Admin> opt = adminRepository.findByUsername(username);
        if (!opt.isPresent()) throw new Exception("Error: Invalid account.");
        if (!passwordService.match(pass, opt.get().getPassword())) throw new Exception("Error: Invalid password.");
        logService.log(opt.get(), LOGIN);
        return new AdminDTO(opt.get());
    }
}
