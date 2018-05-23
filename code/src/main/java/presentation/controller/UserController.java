package presentation.controller;

import static dataAccess.entity.ActivityType.LOGOUT;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import business.dto.FolderDTO;
import business.dto.UserDTO;
import business.service.AccountService;
import business.service.FileDescriptionService;
import business.service.FolderService;
import business.service.LogService;
import business.service.PasswordService;
import presentation.Model;

public class UserController extends Controller{

	private Model model;
	
	@Inject
	private LogService logService;
	
	@Inject
	private AccountService accountService;
	
	@Inject
	private PasswordService passwordService;
	
	@Inject
	private FolderService folderService;
	
	@Inject
	private FileDescriptionService fileService;
	
	public UserController(Model model) {
        injector.injectMembers(this);
		this.model = model;
	}

	public String logOut() {
		try {
			logService.log(model.getUserAcc(), LOGOUT);
		} catch (Exception e) {
			return e.getMessage();
		}
		model.logOut();
		return "";
	}

	public void seeFiles() {
		model.seeFiles();
	}

	public String passChange(char[] newPass, char[] currPass) {
		UserDTO acc = model.getUserAcc();
		byte[] hashed = acc.getPassword();
		if(!passwordService.match(currPass, hashed)) return "Current password is incorrect.";
		try {
			accountService.changePass(acc, new String(newPass));
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public String emailChange(String newEmail, char[] currPass) {
		UserDTO acc = model.getUserAcc();
		byte[] hashed = acc.getPassword();
		if(!passwordService.match(currPass, hashed)) return "Current password is incorrect.";
		try {
			accountService.changeEmail(acc, newEmail);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public List<FolderDTO> getFolders() {
		try {
			return folderService.getRepos(model.getUserAcc());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public int fileNr() {
		try {
			int sum = 0;
			for(FolderDTO f : getFolders()) {
				sum += fileService.fileDescriptionsForRepo(f).size();
			}
			return sum;
		} catch (Exception e) {
			return 0;
		}
	}

	public String createRepo(String name) {
		try {
			folderService.createRepo(model.getUserAcc(), name);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
