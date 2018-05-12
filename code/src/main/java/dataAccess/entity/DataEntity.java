package dataAccess.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private final Integer id;

    public DataEntity() {
        this.id = 0;
    }

    public DataEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataEntity dataEntity = (DataEntity) o;
        return id.equals(dataEntity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
