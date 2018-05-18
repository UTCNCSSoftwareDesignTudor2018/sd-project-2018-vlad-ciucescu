package business.service;

import business.dto.LogDTO;
import com.google.inject.Inject;
import dataAccess.entity.Account;
import dataAccess.entity.Activity;
import dataAccess.entity.ActivityType;
import dataAccess.entity.Log;
import dataAccess.sqlRepository.ActivityRepository;
import dataAccess.sqlRepository.LogRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogService extends Service {

    @Inject
    private LogRepository logRepository;

    @Inject
    private ActivityRepository activityRepository;

    public LogService() {
        injector.injectMembers(this);
    }

    public void log(Account acc, ActivityType type) throws Exception{
        Optional<Activity> act =activityRepository.find(type.ordinal());
        if (!act.isPresent()) throw new Exception("Error: Invalid activity");
        Log log = new Log(0, acc, Instant.now(), act.get());
        logRepository.persist(log);
    }

    public List<LogDTO> findLogs(Account acc) throws Exception{
        return logRepository.findAllForAcc(acc).stream().map(LogDTO::new).collect(Collectors.toList());
    }

    public List<LogDTO> findLogsBetween(Account acc, Instant t1, Instant t2) throws Exception {
        List<LogDTO> logs = findLogs(acc);
        return logs.stream().filter(l->l.getTimestamp().isAfter(t1) && l.getTimestamp().isBefore(t2)).collect(Collectors.toList());
    }
}
