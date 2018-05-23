package presentation.view;

import java.util.Observable;

import presentation.Model;
import presentation.controller.UserController;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFrame;

public class UserGUI extends View{

	private static final long serialVersionUID = 1L;

	private UserController controller;
	private JTextField tfNew;
	private JPasswordField pfCurr;
	private JPasswordField pfNew;
	private boolean passChange;
	private JLabel lblUser;
	private JLabel lblEmail;
	private JLabel lblRepos;
	private JLabel lblFile;
	private JTextField tfRepo;
	public UserGUI(UserController cont, Model mod) {
		super(mod);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(750, 650));
		setTitle("User Profile");
		setResizable(false);
		this.controller = cont;
		getContentPane().setLayout(null);

		JLabel lblError = new JLabel("");
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(a-> {
			String message = controller.logOut();
			if (message.equals("")) {
				this.setVisible(false);
			} else {
				lblError.setText(message);
			}
		});
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogOut.setBounds(319, 558, 97, 40);
		getContentPane().add(btnLogOut);
		
		JButton btnFiles = new JButton("See Files");
		btnFiles.addActionListener(a-> {
			this.setVisible(false);
			controller.seeFiles();
		});
		btnFiles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFiles.setBounds(319, 505, 97, 40);
		getContentPane().add(btnFiles);
		
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblError.setForeground(Color.RED);
		lblError.setBounds(12, 467, 720, 25);
		getContentPane().add(lblError);
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBounds(70, 42, 188, 114);
		getContentPane().add(pnlButtons);
		pnlButtons.setLayout(null);

		lblUser = new JLabel();
		lblEmail = new JLabel();
		lblRepos = new JLabel();
		lblFile = new JLabel();
		
		pfCurr = new JPasswordField();
		JPanel pnlChange = new JPanel();
		pnlChange.setVisible(false);
		JLabel lblNew = new JLabel("New label");
		JLabel lblPass = new JLabel("New label");
		JButton btnChange = new JButton("Change");
		btnChange.addActionListener(a-> {
			lblError.setText("");
			String mess;
			if (passChange) {
				mess = controller.passChange(pfNew.getPassword(), pfCurr.getPassword());
			} else {
				mess = controller.emailChange(tfNew.getText(), pfCurr.getPassword());
			}
			lblError.setText(mess);
		});
		tfNew = new JTextField();
		
		JButton btnPass = new JButton("Change Password");
		btnPass.addActionListener(a->{
			pnlButtons.setVisible(false);
			pnlChange.setVisible(true);
			lblNew.setText("New Password");
			lblPass.setText("Current Password");
			btnChange.setText("Change Password");
			tfNew.setVisible(false);
			pfNew.setVisible(true);
			passChange = true;
		});
		btnPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPass.setBounds(12, 13, 176, 40);
		pnlButtons.add(btnPass);
		
		JButton btnEmail = new JButton("Change Email");
		btnEmail.addActionListener(a->{

			pnlButtons.setVisible(false);
			pnlChange.setVisible(true);
			lblNew.setText("New Email");
			lblPass.setText("Current Password");
			btnChange.setText("Change Email");
			tfNew.setVisible(true);
			pfNew.setVisible(false);
			passChange = false;
		});
		btnEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEmail.setBounds(12, 61, 176, 40);
		pnlButtons.add(btnEmail);
		
		pnlChange.setBounds(319, 42, 383, 184);
		getContentPane().add(pnlChange);
		pnlChange.setLayout(null);
		
		lblNew.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNew.setBounds(12, 13, 125, 29);
		pnlChange.add(lblNew);
		
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPass.setBounds(12, 59, 125, 29);
		pnlChange.add(lblPass);
		
		tfNew.setFont(new Font("Arial", Font.PLAIN, 15));
		tfNew.setBounds(167, 17, 187, 22);
		pnlChange.add(tfNew);
		tfNew.setColumns(10);
		
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChange.setBounds(23, 120, 156, 51);
		pnlChange.add(btnChange);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(a-> {
			pnlButtons.setVisible(true);
			pnlChange.setVisible(false);
			tfNew.setText("");
			pfNew.setText("");
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.setBounds(198, 120, 156, 51);
		pnlChange.add(btnCancel);
		
		pfCurr.setBounds(167, 63, 187, 22);
		pnlChange.add(pfCurr);
		
		pfNew = new JPasswordField();
		pfNew.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pfNew.setBounds(167, 17, 202, 22);
		pnlChange.add(pfNew);
		
		tfRepo = new JTextField();
		tfRepo.setBounds(87, 404, 171, 22);
		getContentPane().add(tfRepo);
		tfRepo.setColumns(10);
		
		JLabel lblCreate = new JLabel("Create Repository");
		lblCreate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCreate.setBounds(25, 362, 143, 29);
		getContentPane().add(lblCreate);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(12, 400, 84, 29);
		getContentPane().add(lblName);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(a-> {
			String mess = controller.createRepo(tfRepo.getText());
			lblError.setText(mess);
			updateLabels();
		});
		btnCreate.setBounds(25, 439, 97, 25);
		getContentPane().add(btnCreate);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals("userlogin")) {
			this.setVisible(true);
			updateLabels();
		}
		if (arg.equals("back")) {
			this.setVisible(true);
			updateLabels();
		}
	}
	
	public void updateLabels() {
		lblUser.setText("User " + model.getUserAcc().getUsername());
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUser.setBounds(291, 260, 200, 29);
		getContentPane().add(lblUser);
		
		lblEmail.setText("Email " + model.getUserAcc().getEmail());
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(291, 302, 200, 29);
		getContentPane().add(lblEmail);
		
		lblRepos.setText(controller.getFolders().size() + " repositories");
		lblRepos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRepos.setBounds(291, 344, 200, 29);
		getContentPane().add(lblRepos);
		
		lblFile.setText(controller.fileNr() + " files in repositories");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFile.setBounds(291, 387, 200, 29);
		getContentPane().add(lblFile);
	}
}
