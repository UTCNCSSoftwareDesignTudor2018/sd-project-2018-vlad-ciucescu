package business.service;

import business.dto.FileDescriptionDTO;
import business.dto.FolderDTO;
import com.google.inject.Inject;
import dataAccess.entity.FileDescription;
import dataAccess.entity.Folder;
import dataAccess.entity.UserFile;
import dataAccess.noSqlRepository.UserFileAdapter;
import dataAccess.noSqlRepository.UserFileRepository;
import dataAccess.sqlRepository.FileDescriptionRepository;
import dataAccess.sqlRepository.FolderRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static dataAccess.entity.ActivityType.ADDFILE;
import static dataAccess.entity.ActivityType.DLFILE;
import static dataAccess.entity.ActivityType.RMFILE;

public class UserFileService extends Service {

    @Inject
    private ValidationService<UserFileService> validationService;

    @Inject
    private LogService logService;

    @Inject
    private FolderService folderService;

    @Inject
    private UserFileRepository fileRepository;

    @Inject
    private FileDescriptionRepository fileDescriptionRepository;

    @Inject
    private FolderRepository folderRepository;

    @Inject
    private UserFileAdapter adapter;

    @Inject
    private FileDescriptionService fileDescriptionService;

    public UserFileService() {
        injector.injectMembers(this);
    }

    public boolean fileNameInUse(FolderDTO folderDTO,
                                 @NotNull(message = "Repository name cannot be null.")
                                 @Size(min = 1, max = 45, message = "File name must be between 1 and 45 characters.")String name) throws Exception {
        Set<String> errors = validationService.validateMethod(this, UserFileService.class.getMethod("fileNameInUse", FolderDTO.class, String.class), folderDTO, name);
        if (!errors.isEmpty()) throw new Exception("Errors" + errors.toString());
        List<FileDescriptionDTO> files = fileDescriptionService.fileDescriptionsForRepo(folderDTO);
        for (FileDescriptionDTO f:files) {
            if (f.getName().equals(name)) return true;
        }
        return false;
    }

    public FileDescriptionDTO addFile(FolderDTO folderDTO, Path path) throws Exception {
        Long repoSize = folderService.getRepoSize(folderDTO);
        Long fileSize = Files.size(path);
        if (fileSize > 25 * 1024 * 1024) throw new Exception("Error: file too big");
        if (repoSize + fileSize > folderDTO.getMaxSize()) throw new Exception("Error: not enough space");
        Optional<Folder> opt = folderRepository.find(folderDTO.getId());
        if (!opt.isPresent()) throw new Exception("Error: cannot find repository.");
        if (fileNameInUse(folderDTO, path.getFileName().toString())) throw new Exception("Error: file name already in use.");
        Folder folder = opt.get();
        fileRepository.persist(adapter.getUserFile(path, folder.getUser().getUsername()+folder.getRepositoryName()));
        FileDescription fd = new FileDescription(0, folder, path.getFileName().toString(), fileSize, Instant.now());
        fileDescriptionRepository.persist(fd);
        logService.log(folder.getUser(), ADDFILE);
        return new FileDescriptionDTO(fd);
    }

    public void removeFile(FolderDTO folderDTO, FileDescriptionDTO fd) throws Exception {
        Optional<Folder> opt = folderRepository.find(folderDTO.getId());
        if (!opt.isPresent()) throw new Exception("Error: cannot find repository.");
        Folder folder = opt.get();
        Optional<UserFile> optF = fileRepository.find(fd.getName(), folder.getUser().getUsername()+folder.getRepositoryName());
        if (!optF.isPresent()) throw new Exception("Error: cannot find file.");
        Optional<FileDescription> optFD = fileDescriptionRepository.find(fd.getId());
        if (!optFD.isPresent()) throw new Exception("Error: cannot find file description.");
        fileRepository.delete(optF.get());
        fileDescriptionRepository.delete(optFD.get());
        logService.log(folder.getUser(), RMFILE);
    }

    public void downloadFile(FolderDTO folderDTO, FileDescriptionDTO fd, Path path) throws Exception {
        Optional<Folder> opt = folderRepository.find(folderDTO.getId());
        if (!opt.isPresent()) throw new Exception("Error: cannot find repository.");
        Folder folder = opt.get();
        Optional<UserFile> optF = fileRepository.find(fd.getName(), folder.getUser().getUsername()+folder.getRepositoryName());
        if (!optF.isPresent()) throw new Exception("Error: cannot find file.");
        UserFile file = optF.get();
        Path newf = Paths.get(path.toString(), file.getName());
        Files.write(newf, file.getData());
        logService.log(folder.getUser(), DLFILE);
    }
}
