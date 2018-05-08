package dataAccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {

    enum ActivityType{
        LOGIN,
        LOGOUT,
        ADDFILE,
        RMFILE,
        DLFILE,
        SEARCH,
        REQREPO;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "description", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @OneToOne(mappedBy = "activity", cascade = CascadeType.ALL, optional = false)
    private Log log;

    public Activity() {
        this.id = 0;
    }
}
