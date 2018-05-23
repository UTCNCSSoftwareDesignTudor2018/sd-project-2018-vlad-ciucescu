package business.service;

import business.dto.AccountDTO;
import business.dto.LogDTO;
import com.google.inject.Inject;
import dataAccess.entity.Account;
import dataAccess.entity.ActivityType;
import dataAccess.entity.Log;
import dataAccess.sqlRepository.AccountRepository;
import dataAccess.sqlRepository.LogRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogService extends Service {

    @Inject
    private LogRepository logRepository;

    @Inject
    private AccountRepository accountRepository;
    
    public LogService() {
        injector.injectMembers(this);
    }
    
    public void log(AccountDTO acc, ActivityType type) throws Exception{
    	Optional<Account> opt = accountRepository.find(acc.getId());
    	if (!opt.isPresent()) throw new Exception("Log exception: cannot find account");
        Log log = new Log(0, opt.get(), Instant.now(), type);
        logRepository.update(log);
    }
    
    public void log(Account acc, ActivityType type) throws Exception{
        Log log = new Log(0, acc, Instant.now(), type);
        logRepository.update(log);
    }

    public List<LogDTO> findLogs(Account acc) throws Exception{
        return logRepository.findAllForAcc(acc).stream().map(LogDTO::new).collect(Collectors.toList());
    }

    public List<LogDTO> findLogsBetween(Account acc, Instant t1, Instant t2) throws Exception {
        List<LogDTO> logs = findLogs(acc);
        return logs.stream().filter(l->l.getTimestamp().isAfter(t1) && l.getTimestamp().isBefore(t2)).collect(Collectors.toList());
    }

	public List<LogDTO> findLogs(AccountDTO acc) throws Exception {
		Optional<Account> opt = accountRepository.find(acc.getId());
    	if (!opt.isPresent()) throw new Exception("Log exception: cannot find account");
    	return findLogs(opt.get());
	}
}
