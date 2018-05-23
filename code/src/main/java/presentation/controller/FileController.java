package presentation.controller;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import business.dto.FileDescriptionDTO;
import business.dto.FolderDTO;
import business.service.FileDescriptionService;
import business.service.FolderService;
import business.service.UserFileService;
import presentation.Model;

public class FileController extends Controller{

	private Model model;
	
	@Inject
	private FolderService folderService;
	
	@Inject
	private FileDescriptionService fileDescriptionService;
	
	@Inject
	private UserFileService userFileService;
	
	@Inject
	private FileDescriptionService fileService;
	
	public FileController(Model model) {

        injector.injectMembers(this);
		this.model = model;
	}

	public void back() {
		model.backToProfile();
	}
	
	public List<FolderDTO> getFolders() {
		try {
			return folderService.getRepos(model.getUserAcc());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public List<FileDescriptionDTO> getFileDescriptions(FolderDTO folderDTO) {
		try {
			return fileDescriptionService.fileDescriptionsForRepo(folderDTO);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public long folderSize(FolderDTO folderDTO) {
		try {
			return folderService.getRepoSize(folderDTO);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int fileNr() {
		try {
			return fileService.fileDescriptionsForRepo(model.getFolder()).size();
		} catch (Exception e) {
			return 0;
		}
	}

	public String delRepo() {
		try {
			folderService.removeRepo(model.getFolder());
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String renameRepo(String name) {
		try {
			folderService.updateName(model.getFolder(), name);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String addFile(File file) {
		try {
			userFileService.addFile(model.getFolder(), file.toPath());
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}

	public String dlFile(FileDescriptionDTO file, Path path) {
		try {
			userFileService.downloadFile(model.getFolder(), file, path);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String remFile(FileDescriptionDTO file) {
		try {
			userFileService.removeFile(model.getFolder(), file);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public List<FileDescriptionDTO> search(String text) {
		try {
			return fileService.searchFile(model.getFolder(), text);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
}
