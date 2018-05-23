package main;

import presentation.Model;
import presentation.controller.AdminController;
import presentation.controller.FileController;
import presentation.controller.LoginController;
import presentation.controller.UserController;
import presentation.view.AdminGUI;
import presentation.view.FileGUI;
import presentation.view.LoginGUI;
import presentation.view.UserGUI;

public class Main {

    public static void main(final String[] args) {
    	Model model = new Model();
		LoginController loginController = new LoginController(model);
		UserController userController = new UserController(model);
		AdminController adminController = new AdminController(model);
		FileController fileController = new FileController(model);
		LoginGUI loginGUI = new LoginGUI(loginController, model);
		UserGUI userGUI = new UserGUI(userController, model);
		AdminGUI adminGUI = new AdminGUI(adminController, model);
		FileGUI fileGUI = new FileGUI(fileController, model);
		model.addObserver(loginGUI);
		model.addObserver(userGUI);
		model.addObserver(adminGUI);
		model.addObserver(fileGUI);
		userGUI.setVisible(false);
		adminGUI.setVisible(false);
		fileGUI.setVisible(false);
		loginGUI.setVisible(true);
    }
}
