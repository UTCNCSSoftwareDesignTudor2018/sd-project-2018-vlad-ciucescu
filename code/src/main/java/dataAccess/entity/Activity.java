package dataAccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "activity")
public class Activity extends DataEntity {

    enum ActivityType{
        LOGIN,
        LOGOUT,
        ADDFILE,
        RMFILE,
        DLFILE,
        SEARCH,
        REQREPO;
    }

    @Column(name = "description", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @OneToOne(mappedBy = "activity", cascade = CascadeType.ALL, optional = false)
    private Log log;

    public Activity() {
        super();
    }

    public Activity(Integer id, ActivityType type, Log log) {
        super(id);
        this.type = type;
        this.log = log;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "type=" + type +
                ", log=" + log +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() && super.equals(o) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
