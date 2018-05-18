package dataAccess.sqlRepository;

import com.google.inject.Inject;
import dataAccess.entity.Admin;
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
    private SessionFactory sessionFactory;

    private Transaction t;

    protected UserRepository() {
    }

    @Override
    public void persist(User obj) throws Exception{
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            throw new Exception("User persist exception: " + e.toString(), e);
        }
    }

    @Override
    public Optional<User> update(User obj) throws Exception{
        User user;
        Optional<User> userOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            user = (User) session.merge(obj);
            userOptional = Optional.ofNullable(user);
            t.commit();
        } catch (Exception e) {
            throw new Exception("User update exception: " + e.toString(), e);
        }
        return userOptional;
    }

    @Override
    public Optional<User> find(Integer id) throws Exception{
        User user;
        Optional<User> userOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            user = session.find(User.class, id);
            userOptional = Optional.ofNullable(user);
            t.commit();
        } catch (Exception e) {
            throw new Exception("User find exception: " + e.toString(), e);
        }
        return userOptional;
    }

    public Optional<User> findByUsername(String username) throws Exception{

        Optional<User> accountOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query q = session.createQuery("FROM Account A WHERE A.username = :username");
            q.setParameter("username", username);
            List res = q.list();
            if (!res.isEmpty()) accountOptional = Optional.ofNullable((User)res.get(0));
        } catch (Exception e) {
            throw new Exception("User find exception: " + e.toString(), e);
        }
        return accountOptional;
    }

    @Override
    public List<User> findAll() throws Exception{
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<User> query = session.createQuery("from User", User.class);
            users = query.list();
            t.commit();
        } catch (Exception e) {
            throw new Exception("User find exception: " + e.toString(), e);
        }
        return users;
    }

    @Override
    public void delete(User obj) throws Exception{
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        } catch (Exception e) {
            throw new Exception("User delete exception: " + e.toString(), e);
        }
    }
}
