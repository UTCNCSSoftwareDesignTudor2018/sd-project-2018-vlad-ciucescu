package repository;

import com.google.inject.Inject;
import dataAccess.entity.UserFile;
import dataAccess.noSqlRepository.MongoRepository;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserFileRepositoryTest extends RepositoryTest {

    @Inject
    private MongoRepository repo;

    @Test
    public void findAllTest() {
        List<UserFile> expected = new ArrayList<>();
        expected.add(new UserFile("file1", "txt", new byte[]{0}));
        expected.add(new UserFile("file2", "txt", new byte[]{0}));
        List<UserFile> actual = repo.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void persistTest() {
        UserFile file = new UserFile("file3", "txt", new byte[]{0});
        repo.persist(file);
        Optional<UserFile> opt = repo.find("file3", "txt");
        assertTrue(opt.isPresent());
        repo.delete(opt.get());
    }

    @Test
    public void updateTest() {
        Optional<UserFile> opt = repo.find("file2", "txt");
        UserFile file = opt.get();
        file.setName("changed");
        repo.update("file2", "txt", file);
        opt = repo.find("changed", "txt");
        assertTrue(opt.isPresent());
        file.setName("file2");
        repo.update("changed", "txt", file);
    }

    @Test
    public void deleteTest() {
        UserFile file = new UserFile("file3", "txt", new byte[]{0});
        repo.persist(file);
        repo.delete(file);
        Optional<UserFile> opt = repo.find("file3", "txt");
        assertFalse(opt.isPresent());
    }
}
