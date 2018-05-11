package dataAccess.sqlRepository;

import com.google.inject.Inject;
import dataAccess.entity.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class AccountRepository implements Repository<Account> {

    @Inject
    private SessionFactory SessionFactory;

    private Transaction t;

    protected AccountRepository() {}

    @Override
    public void persist(Account obj) {
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.persist(obj);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Account persist exception: " + e.toString(), e );
        }
    }

    @Override
    public Optional<Account> update(Account obj) {
        Account account;
        Optional<Account> accountOptional = Optional.empty();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.evict(obj);
            account = (Account)session.merge(obj);
            accountOptional = Optional.ofNullable(account);
            t.commit();
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Account update exception: " + e.toString(), e );
        }
        return accountOptional;
    }

    @Override
    public Optional<Account> find(Integer id) {
        Account account;
        Optional<Account> accountOptional = Optional.empty();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            account = session.find(Account.class, id);
            accountOptional = Optional.ofNullable(account);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Account find exception: " + e.toString(), e );
        }
        return accountOptional;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            Query<Account> query = session.createQuery("from Account", Account.class);
            accounts = query.list();
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Account find exception: " + e.toString(), e );
        }
        return accounts;
    }

    @Override
    public void delete(Account obj) {
        try (Session session = SessionFactory.getSession()) {
            t = session.beginTransaction();
            session.delete(obj);
            t.commit();
        }
        catch (Exception e) {
            LOGGER.log( Level.SEVERE, "Account delete exception: " + e.toString(), e );
        }
    }
}
