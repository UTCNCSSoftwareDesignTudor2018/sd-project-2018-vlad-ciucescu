package dataAccess.sqlRepository;

import dataAccess.entity.DataEntity;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

public interface Repository<T extends DataEntity> {

	static Session session = new SessionFactory().getSession();
	
    void persist(T obj) throws Exception;
    Optional<T> update(T obj) throws Exception;
    Optional<T> find(Integer id) throws Exception;
    List<T> findAll() throws Exception;
    void delete(T obj) throws Exception;
}
