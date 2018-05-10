package dataAccess.repository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dataAccess.entity.Folder;
import dataAccess.sessionFactory.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class FolderRepository implements Repository<Folder> {

    @Inject
    @Named("orm")
    private SessionFactory sessionFactory;

    private Transaction t;


    @Override
    public void persist(Folder obj) {
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Folder persist exception: " + e.toString(), e );
        }
    }

    @Override
    public Optional<Folder> update(Folder obj) {
        Folder folder;
        Optional<Folder> folderOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            folder = (Folder) session.merge(obj);
            folderOptional = Optional.ofNullable(folder);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Folder update exception: " + e.toString(), e );
        }
        return folderOptional;
    }

    @Override
    public Optional<Folder> find(Integer id) {
        Folder folder;
        Optional<Folder> folderOptional = Optional.empty();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            folder = session.find(Folder.class, id);
            folderOptional = Optional.ofNullable(folder);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Folder find exception: " + e.toString(), e );
        }
        return folderOptional;
    }

    @Override
    public List<Folder> findAll() {
        List<Folder> folders = new ArrayList<>();
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<Folder> query = session.createQuery("from Folder", Folder.class);
            folders = query.list();
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Folder find exception: " + e.toString(), e );
        }
        return folders;
    }

    @Override
    public void delete(Folder obj) {
        try (Session session = sessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Folder delete exception: " + e.toString(), e );
        }
    }
}
