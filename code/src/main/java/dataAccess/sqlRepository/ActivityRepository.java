package dataAccess.sqlRepository;

import com.google.inject.Inject;
import dataAccess.entity.Activity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class ActivityRepository implements Repository<Activity> {

    @Inject
    private SessionFactory SessionFactory;

    private Transaction t;

    protected ActivityRepository() {}

    @Override
    public void persist(Activity obj) {
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Activity persist exception: " + e.toString(), e );
        }
    }

    @Override
    public Optional<Activity> update(Activity obj) {
        Activity activity;
        Optional<Activity> activityOptional = Optional.empty();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            activity = (Activity)session.merge(obj);
            activityOptional = Optional.ofNullable(activity);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Activity update exception: " + e.toString(), e );
        }
        return activityOptional;
    }

    @Override
    public Optional<Activity> find(Integer id) {
        Activity activity;
        Optional<Activity> activityOptional = Optional.empty();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            activity = session.find(Activity.class, id);
            activityOptional = Optional.ofNullable(activity);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Activity find exception: " + e.toString(), e );
        }
        return activityOptional;
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<Activity> query = session.createQuery("from Activity", Activity.class);
            activities = query.list();
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Activity find exception: " + e.toString(), e );
        }
        return activities;
    }

    @Override
    public void delete(Activity obj) {
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Activity delete exception: " + e.toString(), e );
        }
    }
}
