package dataAccess.sessionFactory;

import org.hibernate.Session;

public interface SessionFactory {

    public Session getSession();
}
