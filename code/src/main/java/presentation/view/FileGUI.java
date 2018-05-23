package presentation.view;

import java.util.List;
import java.util.Observable;
import presentation.Model;
import presentation.controller.FileController;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.io.File;
import java.nio.file.Path;
import java.awt.Dimension;
import javax.swing.JLabel;

import business.dto.FileDescriptionDTO;
import business.dto.FolderDTO;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.JList;

public class FileGUI extends View{

	private static final long serialVersionUID = 1L;

	private FileController controller;
	
	private JComboBox<FolderDTO> cbRepo;
	private JLabel lblRepoName;
	private JLabel lblRepoSize;
	private JLabel lblRepoFiles;
	private JLabel lblError;
	private JLabel lblRename;
	private JTextField tfRename;
	private JButton btnChangeName;
	private JList<FileDescriptionDTO> list;
	private DefaultListModel<FileDescriptionDTO> lmodel;
	private JScrollPane scrollPane;
	private JButton btnAdd;
	private JButton btnDl;
	private JButton btnDel;
	private JLabel lblNewLabel;
	private JTextField tfFind;
	private JButton btnFind;
	
	public FileGUI(FileController cont, Model mod) {
		super(mod);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(700, 600));
		setTitle("File Management");
		setResizable(false);
		this.controller = cont;
		getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Back to Profile");
		btnBack.addActionListener(a-> {
			this.setVisible(false);
			controller.back();
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.setBounds(249, 514, 146, 38);
		getContentPane().add(btnBack);
		
		JLabel lblSelect = new JLabel("Select Repository");
		lblSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSelect.setBounds(12, 13, 146, 22);
		getContentPane().add(lblSelect);
		
		
		lblRepoName = new JLabel("Repository");
		lblRepoName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRepoName.setBounds(329, 17, 241, 16);
		getContentPane().add(lblRepoName);
		
		lblRepoSize = new JLabel("");
		lblRepoSize.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRepoSize.setBounds(329, 46, 260, 16);
		getContentPane().add(lblRepoSize);
		
		lblRepoFiles = new JLabel("");
		lblRepoFiles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRepoFiles.setBounds(329, 76, 260, 16);
		getContentPane().add(lblRepoFiles);
		
		JButton btnDelRepo = new JButton("Delete Repository");
		btnDelRepo.addActionListener(a-> {
			lblError.setText("");
			String mess = controller.delRepo();
			lblError.setText(mess);
			updateCbFolder();
		});
		btnDelRepo.setBounds(12, 138, 189, 25);
		getContentPane().add(btnDelRepo);
		
		lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblError.setForeground(Color.RED);
		lblError.setBounds(12, 484, 670, 25);
		getContentPane().add(lblError);
		
		lblRename = new JLabel("Rename Repository");
		lblRename.setBounds(12, 187, 165, 16);
		getContentPane().add(lblRename);
		
		tfRename = new JTextField();
		tfRename.setBounds(22, 216, 116, 22);
		getContentPane().add(tfRename);
		tfRename.setColumns(10);
		
		btnChangeName = new JButton("Change name");
		btnChangeName.addActionListener(a-> {
			String mess = controller.renameRepo(tfRename.getText());
			updateCbFolder();
			lblError.setText(mess);
		});
		btnChangeName.setBounds(12, 253, 189, 25);
		getContentPane().add(btnChangeName);
		
		lmodel = new DefaultListModel<>();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(301, 138, 381, 224);
		getContentPane().add(scrollPane);
		list = new JList<>(lmodel);
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnAdd = new JButton("Add File");
		btnAdd.addActionListener(a->{
			lblError.setText("");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fileChooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
			  File file = fileChooser.getSelectedFile();
			  String mess = controller.addFile(file);
			  lblError.setText(mess);
			  updateInfo();
			}
		});
		btnAdd.setBounds(22, 346, 97, 25);
		getContentPane().add(btnAdd);
		
		btnDl = new JButton("Download");
		btnDl.addActionListener(a-> {
			lblError.setText("");
			FileDescriptionDTO file = list.getSelectedValue();
			if (file == null) {
				lblError.setText("No file selected");
			} else {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fileChooser.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
				  Path path = fileChooser.getSelectedFile().toPath();
					String mess = controller.dlFile(file, path);
					lblError.setText(mess);
				}
			}
		});
		btnDl.setBounds(22, 384, 97, 25);
		getContentPane().add(btnDl);
		
		btnDel = new JButton("Remove File");
		btnDel.addActionListener(a-> {lblError.setText("");
		FileDescriptionDTO file = list.getSelectedValue();
		if (file == null) {
			lblError.setText("No file selected");
		} else {
			String mess = controller.remFile(file);
			lblError.setText(mess);
			updateInfo();
		}
		});
		btnDel.setBounds(22, 422, 136, 25);
		getContentPane().add(btnDel);
		
		lblNewLabel = new JLabel("Find file:");
		lblNewLabel.setBounds(301, 426, 69, 16);
		getContentPane().add(lblNewLabel);
		
		tfFind = new JTextField();
		tfFind.setBounds(363, 423, 153, 22);
		getContentPane().add(tfFind);
		tfFind.setColumns(10);
		
		btnFind = new JButton("Search");
		btnFind.addActionListener(a->{
			List<FileDescriptionDTO> files = controller.search(tfFind.getText());
			updateTable(files);
		});
		btnFind.setBounds(551, 422, 97, 25);
		getContentPane().add(btnFind);
		
		cbRepo = new JComboBox<>();
		cbRepo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cbRepo.setBounds(12, 42, 169, 22);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals("seefiles")) {
			this.setVisible(true);
			updateCbFolder();
		}
	}
	
	public void updateCbFolder() {
		
		cbRepo.removeAllItems();
		for (FolderDTO f : controller.getFolders()) {
			cbRepo.addItem(f);
		}
		model.currentFolder((FolderDTO)cbRepo.getSelectedItem());
		cbRepo.addItemListener(e-> {
			       if (e.getStateChange() == ItemEvent.SELECTED) {
			          FolderDTO item = (FolderDTO)e.getItem();
			          model.currentFolder(item);
			          updateInfo();
			       }
		});
		getContentPane().add(cbRepo);
		updateInfo();
	}
	
	private String format(Long size) {
    	if (size < 1024) return size + " Bytes";
    	if (size < 1024*1024) return size/1024 + "KB";
    	return size/1024/2014+"MB";
    }
	public void updateInfo() {
		FolderDTO currFolder = model.getFolder();
		if (currFolder != null) {
			lblRepoName.setText(currFolder.getName());
			lblRepoSize.setText(format(controller.folderSize(currFolder)) + " out of " + format(currFolder.getMaxSize()) + " used.");
			lblRepoFiles.setText(controller.fileNr() + " files.");
			updateTable(controller.getFileDescriptions(currFolder));
		}
	}
	
	public void updateTable(List<FileDescriptionDTO> files) {
		lmodel.clear();
		for (FileDescriptionDTO f : files) {
			lmodel.addElement(f);
		}
	}
}
