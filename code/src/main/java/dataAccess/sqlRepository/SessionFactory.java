package dataAccess.sqlRepository;

import org.hibernate.Session;

import javax.persistence.EntityManager;

public interface SessionFactory {

    public Session getSession();
}
