package repository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dataAccess.entity.Account;
import dataAccess.repository.Repository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccountRepositoryTest extends RepositoryTest {

    @Inject
    @Named("accRepo")
    private Repository<Account> repo;

    @Test
    public void findTest() {
        Optional<Account> acc = repo.find(6);
        assertTrue(acc.isPresent());
        Account actual = acc.get();
        Account expected = new Account(6, "test", new byte[]{0}, "mail");
        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() {
        List<Account> expected = new ArrayList<>();
        expected.add(new Account(6, "test", new byte[]{0}, "mail"));
        expected.add(new Account(7, "test3", new byte[]{0}, "mail3"));
        List<Account> actual = repo.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void persistTest() {
        Account inserted = new Account(0, "test2", new byte[]{0}, "mail2");
        repo.persist(inserted);
        List<Account> accounts = repo.findAll();
        assertTrue(accounts.contains(inserted));
        repo.delete(inserted);
    }

    @Test
    public void updateTest() {
        Optional<Account> acc = repo.find(6);
        Account actual = acc.get();
        actual.setUsername("changed");
        acc = repo.update(actual);
        actual = acc.get();
        Account expected = new Account(6, "changed", new byte[]{0}, "mail");
        assertEquals(expected, actual);
        actual.setUsername("test");
        repo.update(actual);
    }

    @Test
    public void deleteTest() {
        Account inserted = new Account(0, "test2", new byte[]{0}, "mail2");
        repo.persist(inserted);
        repo.delete(inserted);
        List<Account> accounts = repo.findAll();
        assertFalse(accounts.contains(inserted));
    }
}
