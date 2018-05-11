package dataAccess.noSqlRepository;

import dataAccess.entity.UserFile;
import dataAccess.sqlRepository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public interface MongoRepository {

    static final Logger LOGGER = Logger.getLogger(AccountRepository.class.getName());

    public void setCollection(String coll);

    public void persist(UserFile obj);
    public void update(String name, String ext, UserFile obj);
    public Optional<UserFile> find(String name, String ext);
    public List<UserFile> findAll();
    public void delete(UserFile obj);
}
