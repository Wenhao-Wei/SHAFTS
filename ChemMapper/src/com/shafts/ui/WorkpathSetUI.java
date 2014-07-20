package com.shafts.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyleConstants;

import com.shafts.utils.PropertyConfig;

/**
 * this class is for set your job storing path
 * 
 * @author Wenhao Wei 2014-07-11
 *
 */

public class WorkpathSetUI extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 480;
	private final int HEIGHT = 300;
	private JEditorPane description;
	private JLabel title;
	private JLabel workspace;
	private JTextField field;
	private JRadioButton rButton;
	private JButton browse;
	private JButton OK;
	private JButton Cancel;
	private JPanel childPanel;
	private JPanel south;
	private JPanel center;
	private JPanel buttonField;
	private String defaultpath = null;

	public WorkpathSetUI() {
		super();
		setModal(true);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		int width = screensize.width;
		int height = screensize.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		setLocation(x, y);
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		centerpanel center = new centerpanel();
		center.setBorder(new EmptyBorder(20, 0, 0, 0));
		south = new JPanel(new GridLayout(2, 1, 10, 10));
		rButton = new JRadioButton(
				"Use this as the default and don't ask again");
		buttonField = new JPanel(new GridLayout(1, 2, 10, 10));
		buttonField.setBorder(new EmptyBorder(0, 0, 10, 10));
		Cancel = new JButton("Cancel");
		OK = new JButton("OK");
		buttonField.add(Cancel);
		buttonField.add(OK);
		childPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		south.add(rButton);

		// childPanel.add(new JLabel());
		childPanel.add(new JLabel());
		childPanel.add(buttonField);
		south.add(rButton);
		south.add(childPanel);
		String str = "Select a workspace \n \nSHAFTS stores your job in a folder called workspace \nPlease set your workplace folder";
		description = new JEditorPane("text/plain", str);
		description.setEditable(false);
		add(description, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);

		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// dispose();
				System.exit(0);
			}
		});
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String flag = "NO";
				String path = field.getText();
				if (rButton.isSelected())
					flag = "YES";
				else
					flag = "NO";
				PropertyConfig PC = new PropertyConfig();
				PC.createProperties(flag, path);
				dispose();
			}
		});
		setVisible(true);
	}

	class centerpanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void add(Component c, GridBagConstraints constraints, int x,
				int y, int w, int h) {
			constraints.gridx = x;
			constraints.gridy = y;
			constraints.gridwidth = w;
			constraints.gridheight = h;
			add(c, constraints);
		}

		public centerpanel() {
			workspace = new JLabel("Workspace:   ");
			field = new JTextField(25);
			PropertyConfig pc = new PropertyConfig();
			ArrayList<String> a = pc.readProperties();
			if (a == null) {
				defaultpath = System.getProperty("user.dir");
				field.setText(defaultpath + "\\workspace");
			} else {
				ArrayList<String> a1 = pc.readProperties();
				defaultpath = a1.get(1);
				field.setText(defaultpath);
			}
			browse = new JButton("Browse");
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.weightx = 3;
			constraints.weighty = 3;
			add(workspace, constraints, 0, 0, 1, 1);
			add(field, constraints, 1, 0, 1, 1);
			add(browse, constraints, 2, 0, 1, 1);
			browse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String workpath = null;
					JFileChooser fc = new JFileChooser(
							"D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setMultiSelectionEnabled(false);
					int result = fc.showSaveDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						workpath = file.getAbsolutePath();
						workpath = workpath + "\\workspace";
						field.setText(workpath);
					}

				}
			});
		}
	}

	public static void main(String args[]) {
		new WorkpathSetUI();
	}

}
