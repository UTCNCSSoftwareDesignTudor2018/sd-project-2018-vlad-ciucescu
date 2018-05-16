package business.dto;

import dataAccess.entity.FileDescription;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.Instant;

public class FileDescriptionDTO extends DataTransferObject {

    @Size(min = 1, max = 45, message = "File name must be between 1 and 45 characters.")
    private final String name;

    @NotNull(message = "File size cannot be null.")
    @Max(value = 1048576, message = "Maximum file size is 15Mb(1,048,576 bytes).")
    private final Long size;

    @NotNull(message = "File add time cannot be null.")
    @PastOrPresent(message = "File add time must be in the past or present.")
    private final Instant addTime;

    public FileDescriptionDTO() {
        this.name = "file";
        this.size = 0L;
        this.addTime = Instant.now();
    }

    public FileDescriptionDTO(Integer id, String name, Long size, Instant addTime) {
        super(id);
        this.name = name;
        this.size = size;
        this.addTime = addTime;
    }

    public FileDescriptionDTO(FileDescription fd) {
        this(fd.getId(), fd.getName(), fd.getSize(), fd.getAddTime());
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
