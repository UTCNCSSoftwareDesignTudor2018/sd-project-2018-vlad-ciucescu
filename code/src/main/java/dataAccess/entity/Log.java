package dataAccess.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "logs")
public class Log extends DataEntity {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "description", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType activity;

    public Log() {
        super();
    }

    public Log(Integer id, Account account, Instant timestamp, ActivityType activity) {
        super(id);
        this.account = account;
        this.timestamp = timestamp;
        this.activity = activity;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Log{" +
                "account=" + account +
                ", timestamp=" + timestamp +
                ", activity=" + activity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() && super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
