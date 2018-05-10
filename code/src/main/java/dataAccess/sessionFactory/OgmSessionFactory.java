package dataAccess.sessionFactory;

import dataAccess.entity.UserFile;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.ogm.cfg.OgmConfiguration;

public class OgmSessionFactory implements SessionFactory {

    private static final String CONFIG = "hibernate.cfg2.xml";
    private Session session;

    public OgmSessionFactory() {}

    public Session getSession() {
        Configuration con = new OgmConfiguration();
        con.addAnnotatedClass(UserFile.class).configure(CONFIG);
        con.setProperty(Environment.TRANSACTION_COORDINATOR_STRATEGY, "org.hibernate.transaction.JTATransactionFactory");
        con.setProperty(Environment.JTA_PLATFORM, "org.hibernate.service.jta.platform.internal.JBossStandAloneJtaPlatform");
        session = con.buildSessionFactory().openSession();
        return session;
    }
}
