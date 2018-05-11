package dataAccess.sqlRepository;

import com.google.inject.Inject;
import dataAccess.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class UserRepository implements Repository<User> {

    @Inject
    private SessionFactory SessionFactory;

    private Transaction t;

    protected UserRepository() {}

    @Override
    public void persist(User obj) {
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User persist exception: " + e.toString(), e );
        }
    }

    @Override
    public Optional<User> update(User obj) {
        User user;
        Optional<User> userOptional = Optional.empty();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            user = (User)session.merge(obj);
            userOptional = Optional.ofNullable(user);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User update exception: " + e.toString(), e );
        }
        return userOptional;
    }

    @Override
    public Optional<User> find(Integer id) {
        User user;
        Optional<User> userOptional = Optional.empty();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            user = session.find(User.class, id);
            userOptional = Optional.ofNullable(user);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User find exception: " + e.toString(), e );
        }
        return userOptional;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<User> query = session.createQuery("from User", User.class);
            users = query.list();
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User find exception: " + e.toString(), e );
        }
        return users;
    }

    @Override
    public void delete(User obj) {
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User delete exception: " + e.toString(), e );
        }
    }
}
