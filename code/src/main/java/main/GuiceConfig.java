package main;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import dataAccess.entity.Account;
import dataAccess.repository.AccountRepository;
import dataAccess.repository.Repository;
import dataAccess.sessionFactory.OgmSessionFactory;
import dataAccess.sessionFactory.OrmSessionFactory;
import dataAccess.sessionFactory.SessionFactory;

public class GuiceConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<Repository<Account>>() {})
                .annotatedWith(Names.named("accRepo"))
                .to(AccountRepository.class);

        bind(SessionFactory.class)
                .annotatedWith(Names.named("orm"))
                .to(OrmSessionFactory.class);
        bind(SessionFactory.class)
                .annotatedWith(Names.named("ogm"))
                .to(OgmSessionFactory.class);
    }
}
