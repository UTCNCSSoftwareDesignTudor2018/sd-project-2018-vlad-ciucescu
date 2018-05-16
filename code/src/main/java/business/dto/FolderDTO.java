package business.dto;

import dataAccess.entity.Folder;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FolderDTO extends DataTransferObject {

    @Size(min = 1, max = 20, message = "Repository name must be between 1 and 20 characters.")
    private final String name;

    @NotNull(message = "Repository max size cannot be null.")
    @Max(value = 104857600, message = "Repository max size is 100Mb(104857600 bytes).")
    private final Long maxSize;

    public FolderDTO() {
        this.name = "folder";
        this.maxSize = 1000L;
    }

    public FolderDTO(Integer id, String name, Long maxSize) {
        super(id);
        this.name = name;
        this.maxSize = maxSize;
    }

    public FolderDTO(Folder folder) {
        this(folder.getId(), folder.getRepositoryName(), folder.getMaxSize());
    }

    public String getName() {

        return name;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    @Override
    public String toString() {
        return "FolderDTO{" +
                "name='" + name + '\'' +
                ", maxSize=" + maxSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FolderDTO folderDTO = (FolderDTO) o;

        if (name != null ? !name.equals(folderDTO.name) : folderDTO.name != null) return false;
        return maxSize.equals(folderDTO.maxSize);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + maxSize.hashCode();
        return result;
    }
}
