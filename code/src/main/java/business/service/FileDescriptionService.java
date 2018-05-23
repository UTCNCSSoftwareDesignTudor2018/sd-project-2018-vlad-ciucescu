package business.service;

import business.dto.FileDescriptionDTO;
import business.dto.FolderDTO;
import com.google.inject.Inject;
import dataAccess.entity.Folder;
import dataAccess.sqlRepository.FileDescriptionRepository;
import dataAccess.sqlRepository.FolderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileDescriptionService extends Service {

    @Inject
    private FolderRepository folderRepository;

    @Inject
    private FileDescriptionRepository fileDescriptionRepository;

    public FileDescriptionService() {
        injector.injectMembers(this);
    }

    public List<FileDescriptionDTO> fileDescriptionsForRepo(FolderDTO folderDTO) throws Exception{
        Optional<Folder> opt = folderRepository.find(folderDTO.getId());
        if (!opt.isPresent()) throw new Exception("Error: cannot find repository.");
        Folder folder = opt.get();
        return fileDescriptionRepository.findAllForRepo(folder).stream().map(FileDescriptionDTO::new).collect(Collectors.toList());
    }

    public List<FileDescriptionDTO> searchFile(FolderDTO folderDTO, String name) throws Exception {
        List<FileDescriptionDTO> files = fileDescriptionsForRepo(folderDTO);
        return files.stream().filter(f->f.getName().contains(name)).collect(Collectors.toList());
    }
}
