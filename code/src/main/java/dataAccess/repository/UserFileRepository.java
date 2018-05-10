package dataAccess.repository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dataAccess.entity.UserFile;
import dataAccess.sessionFactory.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class UserFileRepository implements Repository<UserFile> {

    @Inject
    @Named("ogm")
    private SessionFactory sessionFactory;

    private Transaction t;

    @Override
    public void persist(UserFile obj) {
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User file persist exception: " + e.toString(), e );
        }
    }

    @Override
    public Optional<UserFile> update(UserFile obj) {
        UserFile user;
        Optional<UserFile> userOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            user = (UserFile)session.merge(obj);
            userOptional = Optional.ofNullable(user);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User file update exception: " + e.toString(), e );
        }
        return userOptional;
    }

    @Override
    public Optional<UserFile> find(Integer id) {
        UserFile userFile;
        Optional<UserFile> userOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            userFile = session.find(UserFile.class, id);
            userOptional = Optional.ofNullable(userFile);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User file find exception: " + e.toString(), e );
        }
        return userOptional;
    }

    @Override
    public List<UserFile> findAll() {
        List<UserFile> userFiles = new ArrayList<>();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<UserFile> query = session.createQuery("from UserFile", UserFile.class);
            userFiles = query.list();
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User file find exception: " + e.toString(), e );
        }
        return userFiles;
    }

    @Override
    public void delete(UserFile obj) {
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "User file delete exception: " + e.toString(), e );
        }
    }
}
