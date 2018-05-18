package repository;

import com.google.inject.Inject;
import dataAccess.entity.UserFile;
import dataAccess.noSqlRepository.UserFileRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserFileRepositoryTest extends RepositoryTest {

    @Inject
    private UserFileRepository repo;

    @Test
    public void findAllTest() throws Exception{
        List<UserFile> expected = new ArrayList<>();
        expected.add(new UserFile("files","file1", new byte[]{0}));
        expected.add(new UserFile("files","file2", new byte[]{0}));
        List<UserFile> actual = repo.findAll("files");
        assertEquals(expected, actual);
    }

    @Test
    public void persistTest() throws Exception{
        UserFile file = new UserFile("files","file3", new byte[]{0});
        repo.persist(file);
        Optional<UserFile> opt = repo.find("file3", "files");
        assertTrue(opt.isPresent());
        repo.delete(opt.get());
    }

    @Test
    public void updateTest() throws Exception{
        Optional<UserFile> opt = repo.find("file2", "files");
        UserFile file = opt.get();
        file.setName("changed");
        repo.update("file2", file);
        opt = repo.find("changed", "files");
        assertTrue(opt.isPresent());
        file.setName("file2");
        repo.update("changed", file);
    }

    @Test
    public void deleteTest() throws Exception{
        UserFile file = new UserFile("files", "file3", new byte[]{0});
        repo.persist(file);
        repo.delete(file);
        Optional<UserFile> opt = repo.find("file3", "txt");
        assertFalse(opt.isPresent());
    }
}
