package dataAccess.sqlRepository;

import com.google.inject.Inject;
import dataAccess.entity.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class LogRepository implements Repository<Log> {

    @Inject
    private SessionFactory sessionFactory;

    private Transaction t;

    protected LogRepository() {
    }

    @Override
    public void persist(Log obj) {
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Log persist exception: " + e.toString(), e);
        }
    }

    @Override
    public Optional<Log> update(Log obj) {
        Log log;
        Optional<Log> logOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            log = (Log) session.merge(obj);
            logOptional = Optional.ofNullable(log);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Log update exception: " + e.toString(), e);
        }
        return logOptional;
    }

    @Override
    public Optional<Log> find(Integer id) {
        Log log;
        Optional<Log> logOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            log = session.find(Log.class, id);
            logOptional = Optional.ofNullable(log);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Log find exception: " + e.toString(), e);
        }
        return logOptional;
    }

    @Override
    public List<Log> findAll() {
        List<Log> logs = new ArrayList<>();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<Log> query = session.createQuery("from Log", Log.class);
            logs = query.list();
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Log find exception: " + e.toString(), e);
        }
        return logs;
    }

    @Override
    public void delete(Log obj) {
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Log delete exception: " + e.toString(), e);
        }
    }
}
