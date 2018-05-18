package dataAccess.sqlRepository;

import dataAccess.entity.DataEntity;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public interface Repository<T extends DataEntity> {

    void persist(T obj) throws Exception;
    Optional<T> update(T obj) throws Exception;
    Optional<T> find(Integer id) throws Exception;
    List<T> findAll() throws Exception;
    void delete(T obj) throws Exception;
}
