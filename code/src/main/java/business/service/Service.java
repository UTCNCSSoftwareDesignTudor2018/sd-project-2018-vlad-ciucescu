package business.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import main.GuiceConfig;

public abstract class Service {
    protected Injector injector = Guice.createInjector(new GuiceConfig());
}
