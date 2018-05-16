package business.dto;

import dataAccess.entity.ActivityType;
import dataAccess.entity.Log;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;

public class LogDTO extends DataTransferObject {

    @NotNull(message = "Log timestamp cannot be null.")
    @PastOrPresent(message = "Log timestamp must be in past or present time.")
    private final Instant timestamp;

    @NotNull(message = "Log activity type cannot be null.")
    private final ActivityType activityType;

    public LogDTO() {
        this.timestamp = Instant.now();
        this.activityType = ActivityType.LOGIN;
    }

    public LogDTO(Integer id, Instant timestamp, ActivityType activityType) {
        super(id);
        this.timestamp = timestamp;
        this.activityType = activityType;
    }

    public LogDTO(Log log) {
        this(log.getId(), log.getTimestamp(), log.getActivity().getType());
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    @Override
    public String toString() {
        return "LogDTO{" +
                "timestamp=" + timestamp +
                ", activityType=" + activityType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogDTO logDTO = (LogDTO) o;

        if (timestamp != null ? !timestamp.equals(logDTO.timestamp) : logDTO.timestamp != null) return false;
        return activityType == logDTO.activityType;
    }

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (activityType != null ? activityType.hashCode() : 0);
        return result;
    }
}
