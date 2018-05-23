package presentation.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;

import main.GuiceConfig;

abstract class Controller {
	protected Injector injector = Guice.createInjector(new GuiceConfig());
}
