package dataAccess.sqlRepository;

import dataAccess.entity.Folder;
import dataAccess.entity.User;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FolderRepository implements Repository<Folder> {

    private Transaction t;

    protected FolderRepository() {
    }

    @Override
    public void persist(Folder obj) throws Exception {
        try {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Folder persist exception: " + e.toString(), e);
        }
    }

    @Override
    public Optional<Folder> update(Folder obj)  throws Exception {
        Folder folder;
        Optional<Folder> folderOptional = Optional.empty();
        try {
            t = session.beginTransaction();
            session.evict(obj);
            folder = (Folder) session.merge(obj);
            folderOptional = Optional.ofNullable(folder);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Folder update exception: " + e.toString(), e);
        }
        return folderOptional;
    }

    @Override
    public Optional<Folder> find(Integer id)  throws Exception {
        Folder folder;
        Optional<Folder> folderOptional = Optional.empty();
        try {
            t = session.beginTransaction();
            folder = session.find(Folder.class, id);
            folderOptional = Optional.ofNullable(folder);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Folder find exception: " + e.toString(), e);
        }
        return folderOptional;
    }

    @Override
    public List<Folder> findAll()  throws Exception {
        List<Folder> folders = new ArrayList<>();
        try {
            t = session.beginTransaction();
            Query<Folder> query = session.createQuery("from Folder", Folder.class);
            folders = query.list();
            t.commit();
        } catch (Exception e) {
            throw new Exception("Folder find exception: " + e.toString(), e);
        }
        return folders;
    }

    public List<Folder> findAllForUser(User user) throws Exception {
        List<Folder> folders = new ArrayList<>();
        try {
            t = session.beginTransaction();
            Query<Folder> query = session.createQuery("from Folder F where F.user = :user", Folder.class);
            query.setParameter("user", user);
            folders = query.list();
            t.commit();
        } catch (Exception e) {
            throw new Exception("Folder find exception: " + e.toString(), e);
        }
        return folders;
    }

    @Override
    public void delete(Folder obj) throws Exception  {
        try {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Folder delete exception: " + e.toString(), e);
        }
    }
}
