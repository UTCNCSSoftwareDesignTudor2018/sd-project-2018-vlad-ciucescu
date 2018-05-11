package dataAccess.business.dto;

public class FolderDTO implements DataTransferObject {

    private final String name;
    private final Long maxSize;

    public FolderDTO() {
        this.name = "folder";
        this.maxSize = 1000L;
    }

    public FolderDTO(String name, Long maxSize) {
        this.name = name;
        this.maxSize = maxSize;
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
