package com.shafts.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;

import com.shafts.application.FormatConv;
import com.shafts.utils.WaitGif;
/**
 * Format conversion application
 * @author Wenhao Wei
 * 2014-07-06
 */
public class FormatConvUI extends JFrame {

	private final int WIDTH = 350;
	private final int HEIGHT = 240;
	private JComboBox ComboBox;
	private JTextField text1;
	private String InformatName = null;
	private String Outformat = null;
	private String Inpath = null;
	private String Outpath = null;
	private JSeparator line;
	//private JDialog dialog;
	private JFrame frame;
	private JPanel north;
	private JPanel center;
	private JPanel south;
	private JPanel addsouth;
	private JPanel contentPane;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private WaitGif WG;
	//private JPanel Layout;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new FormatConvUI();
	}

	public FormatConvUI() {
		init();
	}	
	/**
	 * init the dialog
	 */
	private void init(){
		//dialog = new JDialog();
		frame = new JFrame();
		frame.setName("FormatConv");		
		frame.setResizable(false);
		//frame.setModal(true);
		//Layout = new JPanel();
		label2 = new JLabel();
		label2.setText("Format Conversion");
		label3 = new JLabel("Please choose a file:");
  	  	Toolkit kit = Toolkit.getDefaultToolkit();
     	Dimension screensize = kit.getScreenSize();
     	int width = screensize.width;
     	int height = screensize.height;
     	int x = (width - WIDTH)/2;
     	int y = (height - HEIGHT)/2;
     	frame.setLocation(x-50, y-100);    	
     	frame.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
     	frame.setSize(WIDTH, HEIGHT);
     	ComboBox = new JComboBox();		
		north  = new JPanel();
		center = new JPanel(new GridLayout(3,2,30,10));
		south  = new JPanel(new GridLayout(2,1));
		addsouth = new JPanel(new GridLayout(1,4,5,0));
		contentPane = new JPanel();
		line   = new JSeparator();
		label1 = new JLabel();
		text1 = new JTextField();
		button1 =  new JButton();
		button2 =  new JButton();
		button3 = new JButton();
		button4 = new JButton("Cancel");
		north.add(label2,BorderLayout.CENTER);
		center.add(label3);
		JLabel temp1 = new JLabel("");
		JLabel temp2 = new JLabel("");
		
		//panel2.add(line1);
		center.add(temp1);
		label1.setText("Output format:");
		label1.setToolTipText("Please choose the output format");
		
		south.add(line);
		addsouth.add(button3);
		addsouth.add(temp2);
		addsouth.add(button4);
		addsouth.add(button2);
		south.add(addsouth);
		//panel4.add(line2,BorderLayout.NORTH);
		ComboBox.setToolTipText("Please choose the output format");
		ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Choose...","mol2", "pdb", "cdk", "cml" }));
		ComboBox.addItemListener(new ItemListener(){
       	 public void itemStateChanged(ItemEvent e){         
            if(e.getStateChange() == ItemEvent.SELECTED){    
            	Outformat = (String)ComboBox.getSelectedItem();
            	 //Outformat = s;
            	 //System.out.println(Outformat);             
            } 
          }
       });
		
		/*ComboBox.setModel((new DefaultComboBoxModel(new String[] {"Choose...","mol2", "pdb", "cdk", "cml" }));
		ComboBox.addItemListener(new ItemListener(){
           	 public void itemStateChanged(ItemEvent e){         
                	if(e.getStateChange() == ItemEvent.SELECTED){
                		
                		Outformat = (String)ComboBox.getSelectedItem();
                		if(Outformat == null || Outformat.equals("Choose..."));
                			Outformat = null;
                	 // Informat = s;            
                	  } 
                }
           });*/
		center.add(text1);
		center.add(button1);
		center.add(label1);
		center.add(ComboBox);	
		button1.setText("Open");
		button1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {	        		
	        		JFileChooser file = new JFileChooser("..");
	        		file.showOpenDialog(null);
	        		file.setDialogTitle("Please choose the input file...");	 
	        	    InformatName = file.getSelectedFile().getName();
	        	   // Informat = text4.getText();
	        	   
	        	    Inpath = file.getSelectedFile().getAbsolutePath();
	        	    text1.setText(InformatName);
	        	    //System.out.println(Informat);
	        	    
	        	    //System.out.println(Inpath);
	        		}
	        	}
	        );		
		button2.setText("Convert");		
		button2.addActionListener(new ActionListener() {
	        	@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
	        		if(text1.getText().equals("")){
	        				JOptionPane.showMessageDialog( null,"Please choose a file first£¡");
	        				}
	        		else if(Outformat == null)
	        				JOptionPane.showMessageDialog( null,"Please choose the output format£¡");
	        		else{
	        			JFileChooser fc=new JFileChooser("E:\\MyOffice\\Eclipse\\workplace\\ChemMapper");
	        			fc.setMultiSelectionEnabled(false);
	        			int result=fc.showSaveDialog(null);
	        			if (result==JFileChooser.APPROVE_OPTION){
	        				File file=fc.getSelectedFile();        			
	        				Outpath = file.getAbsolutePath();
	        				//System.out.println(Outpath);
	        				// File file1 = new File(Outpath);            *********************************
	        				FormatConv convert = new FormatConv();
	        				convert.formatconv(Inpath, Outpath, Outformat);
	        				/*if(convFlag)
	        					JOptionPane.showConfirmDialog(null, "Success! Would you wan to show it now?", "Tips", JOptionPane.YES_NO_OPTION);
	        				else
	        					JOptionPane.showMessageDialog( null,"Failed£¬please retry£¡");
	        				//System.out.println(Outformat);//Êä³öÁ÷
	        				//System.out.println(Outpath);*/
	       	    
	       	      			}
	        			}
	        		
	        		}
	        	});
		button3.setText("Help?");
		button3.setToolTipText("Know more chemical format");
		button3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Desktop desktop = Desktop.getDesktop();  
        		try {
					desktop.browse(new URI("http://openbabel.org/docs/2.3.0/FileFormats/Overview.html"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
        		
        		}
        	});	
		button4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        			frame.dispose();
        		}
        	});
		//dialog.add(panel1,BorderLayout.NORTH);
		contentPane.add(north, BorderLayout.NORTH);
		contentPane.add(center,BorderLayout.CENTER);
		//dialog.add(panel3);
		contentPane.add(south,BorderLayout.SOUTH);
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		
	}
	
}
