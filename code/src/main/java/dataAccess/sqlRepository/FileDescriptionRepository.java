package dataAccess.sqlRepository;

import com.google.inject.Inject;
import dataAccess.entity.FileDescription;
import dataAccess.entity.Folder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class FileDescriptionRepository implements Repository<FileDescription> {

    @Inject
    private SessionFactory sessionFactory;

    private Transaction t;

    protected FileDescriptionRepository() {
    }

    @Override
    public void persist(FileDescription obj) throws Exception{
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            throw new Exception("File description persist exception: " + e.toString(), e);
        }
    }

    @Override
    public Optional<FileDescription> update(FileDescription obj) throws Exception{
        FileDescription fileDescription;
        Optional<FileDescription> descriptionOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            fileDescription = (FileDescription) session.merge(obj);
            descriptionOptional = Optional.ofNullable(fileDescription);
            t.commit();
        } catch (Exception e) {
            throw new Exception("File description update exception: " + e.toString(), e);
        }
        return descriptionOptional;
    }

    @Override
    public Optional<FileDescription> find(Integer id) throws Exception{
        FileDescription fileDescription;
        Optional<FileDescription> descriptionOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            fileDescription = session.find(FileDescription.class, id);
            descriptionOptional = Optional.ofNullable(fileDescription);
            t.commit();
        } catch (Exception e) {
            throw new Exception("File description find exception: " + e.toString(), e);
        }
        return descriptionOptional;
    }

    @Override
    public List<FileDescription> findAll() throws Exception{
        List<FileDescription> descriptions = new ArrayList<>();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<FileDescription> query = session.createQuery("from FileDescription", FileDescription.class);
            descriptions = query.list();
            t.commit();
        } catch (Exception e) {
            throw new Exception("File description find exception: " + e.toString(), e);
        }
        return descriptions;
    }

    public List<FileDescription> findAllForRepo(Folder folder) throws Exception{
        List<FileDescription> descriptions = new ArrayList<>();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<FileDescription> query = session.createQuery("from FileDescription F where F.folder = :folder", FileDescription.class);
            query.setParameter("folder", folder);
            descriptions = query.list();
            t.commit();
        } catch (Exception e) {
            throw new Exception("File description find exception: " + e.toString(), e);
        }
        return descriptions;
    }

    @Override
    public void delete(FileDescription obj) throws Exception{
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        } catch (Exception e) {
            throw new Exception("File description delete exception: " + e.toString(), e);
        }
    }
}
