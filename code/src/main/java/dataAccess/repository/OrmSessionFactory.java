package dataAccess.repository;

import dataAccess.entity.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class OrmSessionFactory {

    private Session session;

    public OrmSessionFactory() { }

    public Session getSession(){
        try {
            Configuration configuration = new Configuration()
                    .addPackage("dataAccess.entity")
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(Admin.class)
                    .addAnnotatedClass(FileDescription.class)
                    .addAnnotatedClass(Folder.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Log.class)
                    .addAnnotatedClass(Activity.class);
            configuration.configure();
            session = configuration.buildSessionFactory().openSession();
            return session;
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
