package business.dto;

import dataAccess.entity.FileDescription;

import java.time.Instant;

public class FileDescriptionDTO extends DataTransferObject {

    private final String name;
    private final Long size;
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

    private String format(Long size) {
    	if (size < 1024) return size + " Bytes";
    	if (size < 1024*1024) return size/1024 + "KB";
    	return size/1024/1024+"MB";
    }
    
    @Override
    public String toString() {
    	return String.format("%-20s%-10s%-50s", name, format(size), "Added on " + addTime);
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
