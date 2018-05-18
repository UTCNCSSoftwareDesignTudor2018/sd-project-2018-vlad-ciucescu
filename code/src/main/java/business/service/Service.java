package business.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dataAccess.sqlRepository.AccountRepository;
import main.GuiceConfig;

import java.util.logging.Logger;

public abstract class Service {
    protected Injector injector = Guice.createInjector(new GuiceConfig());
}
