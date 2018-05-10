package repository;

import com.google.inject.Guice;
import com.google.inject.Injector;
import main.GuiceConfig;
import org.junit.Before;

public class RepositoryTest {
    protected Injector injector = Guice.createInjector(new GuiceConfig());

    @Before
    public void setup () {
        injector.injectMembers(this);
    }
}
