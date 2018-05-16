package dataAccess.sqlRepository;

import com.google.inject.Inject;
import dataAccess.entity.Account;
import dataAccess.entity.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class AdminRepository implements Repository<Admin> {

    @Inject
    private SessionFactory sessionFactory;

    private Transaction t;

    protected AdminRepository() {
    }

    @Override
    public void persist(Admin obj) {
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Admin persist exception: " + e.toString(), e);
        }
    }

    @Override
    public Optional<Admin> update(Admin obj) {
        Admin admin;
        Optional<Admin> adminOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            admin = (Admin) session.merge(obj);
            adminOptional = Optional.ofNullable(admin);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Admin update exception: " + e.toString(), e);
        }
        return adminOptional;
    }

    @Override
    public Optional<Admin> find(Integer id) {
        Admin admin;
        Optional<Admin> adminOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            admin = session.find(Admin.class, id);
            adminOptional = Optional.ofNullable(admin);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Admin find exception: " + e.toString(), e);
        }
        return adminOptional;
    }

    public Optional<Admin> findByUsername(String username) {

        Optional<Admin> accountOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query q = session.createQuery("FROM Account A WHERE A.username = :username");
            q.setParameter("username", username);
            List res = q.list();
            if (!res.isEmpty()) accountOptional = Optional.ofNullable((Admin)res.get(0));
        }
        return accountOptional;
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<Admin> query = session.createQuery("from Admin", Admin.class);
            admins = query.list();
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Admin find exception: " + e.toString(), e);
        }
        return admins;
    }

    @Override
    public void delete(Admin obj) {
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Admin delete exception: " + e.toString(), e);
        }
    }
}
