package main;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import dataAccess.entity.*;
import dataAccess.sqlRepository.*;

public class GuiceConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<Repository<Account>>(){})
                .to(AccountRepository.class);

        bind(new TypeLiteral<Repository<Activity>>(){})
                .to(ActivityRepository.class);

        bind(new TypeLiteral<Repository<Admin>>(){})
                .to(AdminRepository.class);

        bind(new TypeLiteral<Repository<FileDescription>>(){})
                .to(FileDescriptionRepository.class);

        bind(new TypeLiteral<Repository<Folder>>(){})
                .to(FolderRepository.class);

        bind(new TypeLiteral<Repository<Log>>(){})
                .to(LogRepository.class);

        bind(new TypeLiteral<Repository<User>>(){})
                .to(UserRepository.class);
    }
}
