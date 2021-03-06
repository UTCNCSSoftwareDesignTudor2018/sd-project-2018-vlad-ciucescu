package repository;

import com.google.inject.Inject;
import dataAccess.entity.Account;
import dataAccess.sqlRepository.AccountRepository;
import dataAccess.sqlRepository.Repository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccountRepositoryTest extends RepositoryTest {

    @Inject
    private AccountRepository repo;

    @Test
    public void findTest() throws Exception{
        Optional<Account> acc = repo.find(6);
        assertTrue(acc.isPresent());
        Account actual = acc.get();
        Account expected = new Account(6, "test", new byte[]{0}, "mail");
        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() throws Exception{
        List<Account> expected = new ArrayList<>();
        expected.add(new Account(6, "test", new byte[]{0}, "mail"));
        expected.add(new Account(7, "test3", new byte[]{0}, "mail3"));
        List<Account> actual = repo.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void persistTest() throws Exception{
        Account inserted = new Account(0, "test2", new byte[]{0}, "mail2");
        repo.persist(inserted);
        List<Account> accounts = repo.findAll();
        assertTrue(accounts.contains(inserted));
        repo.delete(inserted);
    }

    @Test
    public void updateTest() throws Exception{
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
    public void deleteTest() throws Exception {
        Account inserted = new Account(0, "test2", new byte[]{0}, "mail2");
        repo.persist(inserted);
        repo.delete(inserted);
        List<Account> accounts = repo.findAll();
        assertFalse(accounts.contains(inserted));
    }
}
