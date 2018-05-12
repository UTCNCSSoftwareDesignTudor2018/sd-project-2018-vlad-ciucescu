package dataAccess.sqlRepository;

import dataAccess.entity.DataEntity;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public interface Repository<T extends DataEntity> {

    Logger LOGGER = Logger.getLogger(AccountRepository.class.getName());

    void persist(T obj);
    Optional<T> update(T obj);
    Optional<T> find(Integer id);
    List<T> findAll();
    void delete(T obj);
}
