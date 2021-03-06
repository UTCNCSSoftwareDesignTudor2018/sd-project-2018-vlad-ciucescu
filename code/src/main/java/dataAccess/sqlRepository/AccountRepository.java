package dataAccess.sqlRepository;

import dataAccess.entity.Account;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository implements Repository<Account> {

    private Transaction t;

    protected AccountRepository() {}

    @Override
    public void persist(Account obj) throws Exception {
        try {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
           throw new Exception("Account persist exception: " + e.toString(), e );
        }
    }

    @Override
    public Optional<Account> update(Account obj) throws Exception {
        Account account;
        Optional<Account> accountOptional = Optional.empty();
        try {
            t = session.beginTransaction();
            session.evict(obj);
            account = (Account)session.merge(obj);
            accountOptional = Optional.ofNullable(account);
            t.commit();
        } catch (Exception e) {
            throw new Exception("Account update exception: " + e.toString(), e );
        }
        return accountOptional;
    }

    @Override
    public Optional<Account> find(Integer id) throws Exception {
        Account account;
        Optional<Account> accountOptional = Optional.empty();
        try {
            t = session.beginTransaction();
            account = session.find(Account.class, id);
            accountOptional = Optional.ofNullable(account);
            t.commit();
        }
        catch (Exception e) {
            throw new Exception("Account find exception: " + e.toString(), e );
        }
        return accountOptional;
    }

    @Override
    public List<Account> findAll() throws Exception {
        List<Account> accounts = new ArrayList<>();
        try {
            t = session.beginTransaction();
            Query<Account> query = session.createQuery("from Account", Account.class);
            accounts = query.list();
            t.commit();
        }
        catch (Exception e) {
            throw new Exception("Account find exception: " + e.toString(), e );
        }
        return accounts;
    }

    @Override
    public void delete(Account obj) throws Exception{
        try {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        }
        catch (Exception e) {
            throw new Exception("Account delete exception: " + e.toString(), e);
        }
    }
}
