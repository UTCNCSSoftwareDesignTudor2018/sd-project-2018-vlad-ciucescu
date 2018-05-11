package dataAccess.sqlRepository;

import dataAccess.entity.DataEntity;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public interface Repository<T extends DataEntity> {

    static final Logger LOGGER = Logger.getLogger(AccountRepository.class.getName());

    public void persist(T obj);
    public Optional<T> update(T obj);
    public Optional<T> find(Integer id);
    public List<T> findAll();
    public void delete(T obj);
}
