package dataAccess.sqlRepository;

import com.google.inject.Inject;
import dataAccess.entity.FileDescription;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class FileDescriptionRepository implements Repository<FileDescription> {

    @Inject
    private SessionFactory SessionFactory;

    private Transaction t;

    protected FileDescriptionRepository() {
    }

    @Override
    public void persist(FileDescription obj) {
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "File description persist exception: " + e.toString(), e);
        }
    }

    @Override
    public Optional<FileDescription> update(FileDescription obj) {
        FileDescription fileDescription;
        Optional<FileDescription> descriptionOptional = Optional.empty();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            fileDescription = (FileDescription) session.merge(obj);
            descriptionOptional = Optional.ofNullable(fileDescription);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "File description update exception: " + e.toString(), e);
        }
        return descriptionOptional;
    }

    @Override
    public Optional<FileDescription> find(Integer id) {
        FileDescription fileDescription;
        Optional<FileDescription> descriptionOptional = Optional.empty();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            fileDescription = session.find(FileDescription.class, id);
            descriptionOptional = Optional.ofNullable(fileDescription);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "File description find exception: " + e.toString(), e);
        }
        return descriptionOptional;
    }

    @Override
    public List<FileDescription> findAll() {
        List<FileDescription> descriptions = new ArrayList<>();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<FileDescription> query = session.createQuery("from FileDescription", FileDescription.class);
            descriptions = query.list();
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "File description find exception: " + e.toString(), e);
        }
        return descriptions;
    }

    @Override
    public void delete(FileDescription obj) {
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "File description delete exception: " + e.toString(), e);
        }
    }
}
