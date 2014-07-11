package com.shafts.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyleConstants;

import com.shafts.utils.PropertyConfig;
/**
 * this class is for set your job storing path 
 * @author Wenhao Wei
 * 2014-07-11
 *
 */

public class WorkpathSetUI extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 650;
	private final int HEIGHT = 350;
	private JEditorPane description;
	private JLabel title;
	private JLabel workspace;
	private JTextField field;
	private JRadioButton rButton;
	private JButton browse;
	private JButton OK;
	private JButton Cancel;
	private JPanel north;
	private JPanel south;
	private JPanel center;
	private JPanel buttonField;
	private String defaultpath = null;
	
	public WorkpathSetUI(){
		super();
		setModal(true);
  	  	Toolkit kit = Toolkit.getDefaultToolkit();
     	Dimension screensize = kit.getScreenSize();
     	int width = screensize.width;
     	int height = screensize.height;
     	int x = (width - WIDTH)/2;
     	int y = (height - HEIGHT)/2;
     	setLocation(x-50, y-100);
     	setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
     	setSize(WIDTH, HEIGHT);
     	centerpanel center= new centerpanel();
     	add(center);
     	setVisible(true); 
     	//setLayout(new GridLayout(3,1,10,10));
     	/*	north = new JPanel(new GridLayout(1,2));     	
     	//title = new JLabel("Select a workspace");
     	//title.setBackground(Color.WHITE);
     	String str = "Select a workspace \n \nSHAFTS stores your job in a folder called workspace \nPlease set your workplace folder";
     	description = new JEditorPane("text/plain",str);	
     	description.setEditable(false);
     	workspace = new JLabel("Workspace:");
     	workspace.setBounds(0, 0, 15, 5);
     	field = new JTextField(25);
     	field.setText(defaultpath + "\\workspace");
     	browse = new JButton("Browse");
     	north.add(field);
     	north.add(browse);
     	center = new JPanel(new GridLayout(3,1,10,10));
     	//centerpanel east= new centerpanel();
     	center.add(workspace);
     	
     	center.add(north);
     	
     	south = new JPanel(new GridLayout(1,2,10,10));
     	rButton = new JRadioButton("Use this as the default and don't ask again");
     	center.add(rButton);
     	buttonField = new JPanel(new GridLayout(1,2,10,10));
     	buttonField.setBorder(new EmptyBorder(10, 10, 10, 10)); 
     	Cancel = new JButton("Cancel");
     	OK = new JButton("     OK     ");
     	buttonField.add(Cancel);
     	buttonField.add(OK);
     	//south.add(rButton);
     	//south.add(new JLabel());
     	south.add(new JLabel());
     	south.add(buttonField);
     	add(description,BorderLayout.NORTH);
     	add(center,BorderLayout.CENTER);
     	add(south,BorderLayout.SOUTH);
     	setVisible(true);     	
     	Cancel.addActionListener(new ActionListener(){
     		public void actionPerformed(ActionEvent e){
     			dispose();
     		}
     	});
     	OK.addActionListener(new ActionListener(){
     		public void actionPerformed(ActionEvent e){
     			String flag = "NO";
     			String path = field.getText();
     			if(rButton.isSelected())
     				flag = "YES";
     			else flag = "NO";
     			PropertyConfig PC = new PropertyConfig();
     			PC.createProperties(flag, path);     			
     		}
     	});
     	workspace = new JLabel("Workspace:");
     	defaultpath = System.getProperty("user.dir");
     	field = new JTextField(25);
     	field.setText(defaultpath + "workspace");
     	browse = new JButton("Browse");
     	String str = "Select a workspace \n \nSHAFTS stores your job in a folder called workspace \nPlease set your workplace folder";
     	description = new JEditorPane("text/plain",str);	
     	description.setEditable(false);
     	center = new JPanel(new GridLayout(1,3,10,10));
     	south = new JPanel(new GridLayout(2,2,10,10));
     	rButton = new JRadioButton("Use this as the default and don't ask again");
     	buttonField = new JPanel(new GridLayout(1,2,10,10));
     	Cancel = new JButton("Cancel");
     	OK = new JButton("OK");
     	buttonField.add(Cancel);
     	buttonField.add(OK);
     	center.add(workspace);
     	center.add(field);
     	center.add(browse);
     	south.add(rButton);
     	south.add(new JLabel());
     	south.add(new JLabel());
     	south.add(buttonField);
     	add(description,BorderLayout.NORTH);
     	add(center,BorderLayout.CENTER);
     	add(south,BorderLayout.SOUTH);
     	setVisible(true);
     	browse.addActionListener(new ActionListener(){
     		public void actionPerformed(ActionEvent e){
     			String workpath = null;
     			JFileChooser fc=new JFileChooser("D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
    			fc.setMultiSelectionEnabled(false);
    			int result=fc.showSaveDialog(null);
    			if (result==JFileChooser.APPROVE_OPTION){
    				File file=fc.getSelectedFile();
    				workpath = file.getAbsolutePath();
    			}
    			workpath = workpath + "workspace";
    			field.setText(workpath);
     		}
     	});
     	Cancel.addActionListener(new ActionListener(){
     		public void actionPerformed(ActionEvent e){
     			dispose();
     		}
     	});
     	OK.addActionListener(new ActionListener(){
     		public void actionPerformed(ActionEvent e){
     			String flag = "NO";
     			String path = field.getText();
     			if(rButton.isSelected())
     				flag = "YES";
     			else flag = "NO";
     			PropertyConfig PC = new PropertyConfig();
     			PC.createProperties(flag, path);
     			dispose();
     		}
     	});*/
     	
	}
	class centerpanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h){
			constraints.gridx = x;
			constraints.gridy = y;
			constraints.gridwidth = w;
			constraints.gridheight = h;
			add(c,constraints);
		}
		public centerpanel(){
			String str = "Select a workspace \n \nSHAFTS stores your job in a folder called workspace \nPlease set your workplace folder";
	     	description = new JEditorPane("text/plain",str);	
	     	description.setEditable(false);
	     	workspace = new JLabel("Workspace:");
	     	//workspace.setBounds(0, 0, 15, 5);
	     	field = new JTextField();
	     	field.setText(defaultpath + "\\workspace");
	     	//browse = new JButton("Browse");
			//workspace = new JLabel("Workspace");
	     	defaultpath = System.getProperty("user.dir");
	     	field = new JTextField();
	     	field.setText(defaultpath + "workspace");
	     	rButton = new JRadioButton("Use this as the default and don't ask again");
	     	browse = new JButton("Browse");
	     	Cancel = new JButton("Cancel");
	     	OK = new JButton("OK");
			GridBagConstraints constraints = new GridBagConstraints();
	     	constraints.fill = GridBagConstraints.NONE;
	     	constraints.anchor = GridBagConstraints.WEST;
	     	constraints.weightx = 4;
	     	constraints.weighty = 4;
	     	add(description, constraints,0,0,4,1);
	     	add(workspace, constraints,0,1,1,1);
	     	add(field, constraints,1,1,2,1);
	     	add(browse, constraints,3,1,1,1);
	     	add(rButton, constraints,0,2,4,1);
	     	add(Cancel, constraints,2,3,1,1);
	     	add(OK, constraints,3,3,1,1);
	     	browse.addActionListener(new ActionListener(){
	     		public void actionPerformed(ActionEvent e){
	     			String workpath = null;
	     			JFileChooser fc=new JFileChooser("D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
	    			fc.setMultiSelectionEnabled(false);
	    			int result=fc.showSaveDialog(null);
	    			if (result==JFileChooser.APPROVE_OPTION){
	    				File file=fc.getSelectedFile();
	    				workpath = file.getAbsolutePath();
	    			}
	    			workpath = workpath + "workspace";
	    			field.setText(workpath);
	     		}
	     	});
	     	Cancel.addActionListener(new ActionListener(){
	     		public void actionPerformed(ActionEvent e){
	     			dispose();
	     		}
	     	});
	     	OK.addActionListener(new ActionListener(){
	     		public void actionPerformed(ActionEvent e){
	     			String flag = "NO";
	     			String path = field.getText();
	     			if(rButton.isSelected())
	     				flag = "YES";
	     			else flag = "NO";
	     			PropertyConfig PC = new PropertyConfig();
	     			PC.createProperties(flag, path);     			
	     		}
	     	});
		}
	}
	public static void main(String args[]){
		new WorkpathSetUI();
	}

}
