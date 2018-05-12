package dataAccess.sqlRepository;

import dataAccess.entity.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class SessionFactory {

    public SessionFactory() {
    }

    public Session getSession() {
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
            return configuration.buildSessionFactory().openSession();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
