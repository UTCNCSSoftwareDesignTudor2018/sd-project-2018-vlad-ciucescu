package presentation.controller;

import com.google.inject.Inject;

import business.dto.FolderDTO;
import business.dto.LogDTO;
import business.dto.UserDTO;
import business.service.FileDescriptionService;
import business.service.FolderService;
import business.service.LogService;
import business.service.UserService;
import presentation.Model;

import static dataAccess.entity.ActivityType.*;

import java.util.ArrayList;
import java.util.List;

public class AdminController extends Controller{

	private Model model;
	
	@Inject
	private LogService logService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private FolderService folderService;
	
	@Inject
	private FileDescriptionService fileService;
	
	public AdminController(Model model) {
        injector.injectMembers(this);
		this.model = model;
	}

	public String logOut() {
		try {
			logService.log(model.getAdminAcc(), LOGOUT);
		} catch (Exception e) {
			return e.getMessage();
		}
		model.logOut();
		return "";
	}

	public List<UserDTO> getUsers() {
		try {
			return userService.getAllUsers();
		} catch (Exception e) {
			return new ArrayList<>();
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

	public List<LogDTO> getLogs() {
		try {
			return logService.findLogs(model.getUserAcc());
		} catch (Exception e) {
			return new ArrayList<>();
		}
		
	}

	public String blockUser() {
		UserDTO acc = model.getUserAcc();
		try {
			acc.setBlocked(!acc.getBlocked());
			userService.setBlockStatus(acc, acc.getBlocked());
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
