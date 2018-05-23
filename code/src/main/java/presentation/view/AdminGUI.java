package presentation.view;

import java.util.Observable;

import presentation.Model;
import presentation.controller.AdminController;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.Dimension;
import javax.swing.JLabel;

import business.dto.LogDTO;
import business.dto.UserDTO;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class AdminGUI extends View{

	private static final long serialVersionUID = 1L;
	
	private AdminController controller;
	
	private JComboBox<UserDTO> cbSelect;
	
	private JLabel lblUsername;
	private JLabel lblEmail;
	private JLabel lblStatus;
	private JLabel lblRepos;
	private JLabel lblFiles;
	private JScrollPane scrollPane;
	private JList<LogDTO> list;
	private DefaultListModel<LogDTO> lmodel;
	private JButton btnBlock;
	
	public AdminGUI(AdminController cont, Model mod) {
		super(mod);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(600, 600));
		setMinimumSize(new Dimension(600, 600));
		setTitle("Admin Profile");
		setResizable(false);
		this.controller = cont;
		getContentPane().setLayout(null);

		JLabel lblError = new JLabel("");
		JButton btnBack = new JButton("Log Out");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.addActionListener(a->{
			String message = controller.logOut();
			if (message.equals("")) {
				this.setVisible(false);
			} else {
				lblError.setText(message);
			}
		});
		btnBack.setBounds(233, 512, 106, 40);
		getContentPane().add(btnBack);
		
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblError.setBounds(12, 474, 720, 25);
		getContentPane().add(lblError);
		
		JLabel lblSelect = new JLabel("Select User");
		lblSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSelect.setBounds(12, 13, 130, 32);
		getContentPane().add(lblSelect);
		
		cbSelect = new JComboBox<>();
		cbSelect.setBounds(12, 52, 130, 22);
		getContentPane().add(cbSelect);
		
		lblUsername = new JLabel("");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(240, 29, 157, 16);
		getContentPane().add(lblUsername);
		
		lblEmail = new JLabel("");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(233, 55, 200, 16);
		getContentPane().add(lblEmail);
		
		lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStatus.setBounds(425, 39, 157, 16);
		getContentPane().add(lblStatus);
		
		lblRepos = new JLabel("");
		lblRepos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRepos.setBounds(12, 113, 157, 16);
		getContentPane().add(lblRepos);
		
		lblFiles = new JLabel("");
		lblFiles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFiles.setBounds(276, 113, 157, 16);
		getContentPane().add(lblFiles);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 217, 381, 224);
		getContentPane().add(scrollPane);
		
		lmodel = new DefaultListModel<>();
		list = new JList<>(lmodel);
		scrollPane.setViewportView(list);
		
		btnBlock = new JButton("Block User");
		btnBlock.addActionListener(a-> {
			String mess = controller.blockUser();
			lblError.setText(mess);
			updateInfo();
		});
		btnBlock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBlock.setBounds(437, 301, 145, 25);
		getContentPane().add(btnBlock);
	}
	
	@Override
	public void update(Observable obs, Object arg) {
		if (arg.equals("adminlogin")) {
			this.setVisible(true);
			updateCbUsers();
		}
	}
	
	private void updateCbUsers() {
		cbSelect.removeAllItems();
		for (UserDTO user : controller.getUsers()) {
			cbSelect.addItem(user);
		}
		cbSelect.addItemListener(e-> {
		       if (e.getStateChange() == ItemEvent.SELECTED) {
		          UserDTO item = (UserDTO)e.getItem();
		          model.currentUser(item);
		          updateInfo();
		       }
		});
	}
	
	public void updateInfo() {
		UserDTO user = model.getUserAcc();
		btnBlock.setText(user.getBlocked()?"Unblock":"Block");
		lblUsername.setText("User " + user.getUsername());
		lblEmail.setText("Email " + user.getEmail());
		lblStatus.setText("Status: " + (user.getBlocked()?"blocked":"unblocked"));
		lblRepos.setText(controller.getFolders().size() + " repositories");
		lblFiles.setText(controller.fileNr() + " files in repositories");
		lmodel.clear();
		for (LogDTO log : controller.getLogs()) {
			lmodel.addElement(log);
		}
	}
}
