package presentation.view;

import java.util.Observer;

import javax.swing.JFrame;

import presentation.Model;

public abstract class View extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;

	protected Model model;
	
	public View(Model model) {
		this.model = model;
	}
}
