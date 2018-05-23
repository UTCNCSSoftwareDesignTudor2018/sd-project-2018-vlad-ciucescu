package presentation.controller;

import com.google.inject.Inject;

import business.dto.AdminDTO;
import business.dto.UserDTO;
import business.service.AdminService;
import business.service.UserService;
import presentation.Model;

public class LoginController extends Controller {

	private Model model;
	
	private boolean userLogin = true;
	
	@Inject
	private UserService userService;
	
	@Inject
	private AdminService adminService;
	
	public LoginController(Model model) {

        injector.injectMembers(this);
		this.model = model;
	}
	
	public void isUserLogin(boolean b) {
		userLogin = b;
		model.isUserSession(b);
	}
	
	public String tryLogin(String username, char[] password) {
		try {
			if (userLogin) {
				UserDTO user = userService.logIn(username, password);
				model.setUser(user);
			} else {
				AdminDTO admin = adminService.logIn(username, password);
				model.setAdmin(admin);
			}
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
