package dataAccess.business.dto;

import java.time.Instant;

public class FileDescriptionDTO implements DataTransferObject {

    private final String name;
    private final Long size;
    private final Instant addTime;

    public FileDescriptionDTO() {
        this.name = "file";
        this.size = 0L;
        this.addTime = Instant.now();
    }

    public FileDescriptionDTO(String name, Long size, Instant addTime) {
        this.name = name;
        this.size = size;
        this.addTime = addTime;
    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }

    public Instant getAddTime() {
        return addTime;
    }

    @Override
    public String toString() {
        return "FileDescriptionDTO{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", addTime=" + addTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileDescriptionDTO that = (FileDescriptionDTO) o;

        if (!name.equals(that.name)) return false;
        if (!size.equals(that.size)) return false;
        return addTime.equals(that.addTime);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + addTime.hashCode();
        return result;
    }
}
