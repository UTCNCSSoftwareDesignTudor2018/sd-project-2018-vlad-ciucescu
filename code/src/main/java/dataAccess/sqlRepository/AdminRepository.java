package dataAccess.sqlRepository;

import dataAccess.entity.Admin;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminRepository implements Repository<Admin> {

    private Transaction t;

    protected AdminRepository() {
    }

    @Override
    public void persist(Admin obj) throws Exception{
        try {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Admin persist exception: " + e.toString(), e);
        }
    }

    @Override
    public Optional<Admin> update(Admin obj) throws Exception{
        Admin admin;
        Optional<Admin> adminOptional = Optional.empty();
        try {
            t = session.beginTransaction();
            session.evict(obj);
            admin = (Admin) session.merge(obj);
            adminOptional = Optional.ofNullable(admin);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Admin update exception: " + e.toString(), e);
        }
        return adminOptional;
    }

    @Override
    public Optional<Admin> find(Integer id) throws Exception{
        Admin admin;
        Optional<Admin> adminOptional = Optional.empty();
        try {
            t = session.beginTransaction();
            admin = session.find(Admin.class, id);
            adminOptional = Optional.ofNullable(admin);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Admin find exception: " + e.toString(), e);
        }
        return adminOptional;
    }

    public Optional<Admin> findByUsername(String username) throws Exception{

        Optional<Admin> accountOptional = Optional.empty();
        try {
            t = session.beginTransaction();
            Query q = session.createQuery("FROM Admin A WHERE A.username = :username");
            q.setParameter("username", username);
            List res = q.list();
            t.commit();
            if (!res.isEmpty()) accountOptional = Optional.ofNullable((Admin)res.get(0));
        } catch (Exception e) {
            throw new Exception("Admin find exception: " + e.toString(), e);
        }
        return accountOptional;
    }

    @Override
    public List<Admin> findAll() throws Exception{
        List<Admin> admins = new ArrayList<>();
        try {
            t = session.beginTransaction();
            Query<Admin> query = session.createQuery("from Admin", Admin.class);
            admins = query.list();
            t.commit();
        } catch (Exception e) {
            throw new Exception("Admin find exception: " + e.toString(), e);
        }
        return admins;
    }

    @Override
    public void delete(Admin obj) throws Exception{
        try {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Admin delete exception: " + e.toString(), e);
        }
    }
}
