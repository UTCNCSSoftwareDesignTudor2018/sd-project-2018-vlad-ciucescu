package business.dto;

import dataAccess.entity.DataEntity;

public abstract class DataTransferObject {

    private Integer id;

    public DataTransferObject() {
    }

    public DataTransferObject(Integer id) {
        this.id = id;
    }

    public DataTransferObject(DataEntity entity) {
        this.id = entity.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
