package presentation;

import java.util.Observable;

import business.dto.AdminDTO;
import business.dto.FolderDTO;
import business.dto.UserDTO;

public class Model extends Observable{

	private UserDTO user;
	private AdminDTO admin;
	private FolderDTO currentFolder;
	
	public void isUserSession(boolean b) {
	}

	public void setUser(UserDTO user) {
		this.user = user;
		this.setChanged();
		this.notifyObservers("userlogin");
	}
	
	public void setAdmin(AdminDTO admin) {
		this.admin = admin;
		this.setChanged();
		System.out.println(admin);
		this.notifyObservers("adminlogin");
	}

	public void logOut() {
		this.user = null;
		this.admin = null;
		this.setChanged();
		this.notifyObservers("logout");
	}

	public void backToProfile() {
		this.setChanged();
		this.notifyObservers("back");
	}

	public AdminDTO getAdminAcc() {
		return admin;
	}
	
	public UserDTO getUserAcc() {
		return user;
	}

	public void seeFiles() {
		this.setChanged();
		this.notifyObservers("seefiles");
	}

	public void currentFolder(FolderDTO item) {
		currentFolder = item;
	}

	public FolderDTO getFolder() {
		return currentFolder;
	}

	public void currentUser(UserDTO item) {
		user = item;
	}
	
}
