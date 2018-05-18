package business.service;

import business.dto.FileDescriptionDTO;
import business.dto.FolderDTO;
import business.dto.UserDTO;
import com.google.inject.Inject;
import dataAccess.entity.Folder;
import dataAccess.entity.User;
import dataAccess.noSqlRepository.UserFileRepository;
import dataAccess.sqlRepository.FolderRepository;
import dataAccess.sqlRepository.UserRepository;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dataAccess.entity.ActivityType.CREATEREPO;
import static dataAccess.entity.ActivityType.DELREPO;
import static dataAccess.entity.ActivityType.RENAMEREPO;

public class FolderService extends Service {

    @Inject
    private FolderRepository folderRepository;

    @Inject
    private ValidationService<FolderService> validationService;

    @Inject
    private LogService logService;

    @Inject
    private FileDescriptionService fileDescriptionService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserFileRepository userFileRepository;

    public FolderService() {
        injector.injectMembers(this);
    }

    public boolean nameInUse(UserDTO userDTO,
                              @NotNull(message = "Repository name cannot be null.")
                              @Size(min = 1, max = 20, message = "Repository name must be between 1 and 20 characters.")String name)
                            throws Exception {
        Set<String> errors = validationService.validateMethod(this, FolderService.class.getMethod("nameInUse", UserDTO.class, String.class), userDTO, name);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        Optional<User> opt = userRepository.find(userDTO.getId());
        if (!opt.isPresent()) throw new Exception("Error: cannot find user");
        List<Folder> folders = folderRepository.findAllForUser(opt.get());
        for (Folder f:folders) {
            if (f.getRepositoryName().equals(name)) return true;
        }
        return false;
    }

    public FolderDTO createRepo(User acc,
                                @NotNull(message = "Repository max size cannot be null.")
                                @Max(value = 104857600, message = "Repository max size is 100Mb(104857600 bytes).")Long size,
                                @NotNull(message = "Repository name cannot be null.")
                                @Size(min = 1, max = 20, message = "Repository name must be between 1 and 20 characters.")String name)
                                throws Exception {
        Set<String> errors = validationService.validateMethod(this, FolderService.class.getMethod("createRepo",User.class, Long.class, String.class), acc, size, name);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        Folder folder = new Folder(0, acc, name, size);
        folderRepository.persist(folder);
        logService.log(acc, CREATEREPO);
        return new FolderDTO(folder);
    }

    public FolderDTO updateName(FolderDTO folderDTO,
                                @NotNull(message = "Repository name cannot be null.")
                                @Size(min = 1, max = 20, message = "Repository name must be between 1 and 20 characters.")String name)
                                throws Exception {
        Set<String> errors = validationService.validateMethod(this, FolderService.class.getMethod("updateName", FolderDTO.class, String.class), folderDTO, name);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        Optional<Folder> opt = folderRepository.find(folderDTO.getId());
        if(!opt.isPresent()) throw new Exception("Error: could not find repository");
        Folder folder = opt.get();
        folder.setRepositoryName(name);
        folderRepository.update(folder);
        logService.log(folder.getUser(), RENAMEREPO);
        return new FolderDTO(folder);
    }

    public List<FolderDTO> getRepos(User user) throws Exception{
        List<Folder> folders = folderRepository.findAllForUser(user);
        return folders.stream().map(FolderDTO::new).collect(Collectors.toList());
    }

    public Long getRepoSize(FolderDTO folder) throws Exception {
        List<FileDescriptionDTO> files = fileDescriptionService.fileDescriptionsForRepo(folder);
        return files.stream().mapToLong(FileDescriptionDTO::getSize).sum();
    }

    public void removeRepo(FolderDTO folderDTO) throws Exception {
        Optional<Folder> opt = folderRepository.find(folderDTO.getId());
        if (!opt.isPresent()) throw new Exception("Error: cannot find repository.");
        Folder folder = opt.get();
        folderRepository.delete(folder);
        userFileRepository.deleteCollection(folder.getUser().getUsername()+folder.getRepositoryName());
        logService.log(folder.getUser(), DELREPO);
    }
}
