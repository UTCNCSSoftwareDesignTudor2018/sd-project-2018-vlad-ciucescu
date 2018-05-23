package presentation.view;

import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import presentation.Model;
import presentation.controller.LoginController;

import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dimension;

public class LoginGUI extends View{
	
	private static final long serialVersionUID = 1L;
	
	private LoginController controller;
	
	private JTextField tfUser;
	private JPasswordField passwordField;
	private JPanel pnlLogin;
	private JPanel pnlButtons;
	
	public LoginGUI(LoginController con, Model mod) {
		super(mod);
		setSize(new Dimension(750, 520));
		this.controller = con;
		
		setTitle("Backup Service");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Secure Backup Service");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblTitle.setBounds(253, 27, 249, 42);
		getContentPane().add(lblTitle);

		pnlLogin = new JPanel();
		pnlButtons = new JPanel();
		pnlButtons.setBounds(179, 82, 385, 152);
		getContentPane().add(pnlButtons);
		pnlButtons.setLayout(null);
		
		JButton btnAdmin = new JButton("Admin Login");
		btnAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdmin.addActionListener(a->{
			pnlButtons.setVisible(false);
			pnlLogin.setVisible(true);
			controller.isUserLogin(false);
		});
		btnAdmin.setBounds(104, 13, 166, 36);
		pnlButtons.add(btnAdmin);
		
		JButton btnUser = new JButton("User Login");
		btnUser.addActionListener(a->{
			pnlButtons.setVisible(false);
			pnlLogin.setVisible(true);
			controller.isUserLogin(true);
		});
		btnUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUser.setBounds(104, 62, 166, 36);
		pnlButtons.add(btnUser);
		
		JButton btnReq = new JButton("Request Account");
		btnReq.addActionListener(a->{});
		btnReq.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnReq.setBounds(104, 111, 166, 36);
		pnlButtons.add(btnReq);
		
		pnlLogin.setVisible(false);
		pnlLogin.setBounds(179, 233, 385, 210);
		getContentPane().add(pnlLogin);
		pnlLogin.setLayout(null);
		
		tfUser = new JTextField();
		tfUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfUser.setBounds(105, 13, 202, 34);
		pnlLogin.add(tfUser);
		tfUser.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBounds(105, 60, 202, 34);
		pnlLogin.add(passwordField);
		
		JLabel lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		
		JButton btnLogin = new JButton("Log In");
		btnLogin.addActionListener(a -> {
			String message = controller.tryLogin(tfUser.getText(), passwordField.getPassword());
			passwordField.setText(message);
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogin.setBounds(105, 123, 166, 36);
		pnlLogin.add(btnLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(0, 22, 93, 25);
		pnlLogin.add(lblUsername);
		
		JLabel lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPass.setBounds(0, 69, 93, 25);
		pnlLogin.add(lblPass);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(a->{
			pnlButtons.setVisible(true);
			pnlLogin.setVisible(false);
			tfUser.setText("");
			passwordField.setText("");
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.setBounds(105, 172, 166, 36);
		pnlLogin.add(btnBack);
		
		lblError.setBackground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblError.setBounds(12, 450, 728, 29);
		getContentPane().add(lblError);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals("logout")) {
			this.setVisible(true);
			pnlButtons.setVisible(true);
			pnlLogin.setVisible(false);
			tfUser.setText("");
			passwordField.setText("");
		}
	}
}
