package com.shafts.ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.openbabel.*;

import com.shafts.utils.JmolPanel1;

public class FormatConv extends JFrame {

	private JPanel contentPane;

	private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox22;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormatConv frame = new FormatConv();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormatConv() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		setTitle("格式转换！");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
	}
	
	String InformatName = null;
	String OutformatName = null;
	 String Informat = null;
	 String Outformat = null;
	String Inpath = null;
	String Outpath = null;
	
	private void init(){
		
		jComboBox11 = new javax.swing.JComboBox();
        jComboBox22 = new javax.swing.JComboBox();
		
		JPanel westPane = new JPanel();
		final JmolPanel1 jmolPanel = new JmolPanel1();
		getContentPane().add(jmolPanel, BorderLayout.CENTER);
		westPane.setPreferredSize(new Dimension(250,400));
		getContentPane().add(westPane,BorderLayout.WEST);
		
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		JLabel label4 = new JLabel();
		JLabel label5 = new JLabel();
		final JTextField text1 = new JTextField();
		final JTextField text2 = new JTextField();
		//final JTextField text4 = new JTextField();
		JButton button1 =  new JButton();
		JButton button2 =  new JButton();
		JButton button3 = new JButton();
		JButton button4 = new JButton();
		JPanel jpanel1 = new JPanel();
		JPanel jpanel2 = new JPanel();
		JPanel jpanel3 = new JPanel();
		
		
		westPane.setLayout(new GridLayout(7,1,25,25));
		label1.setText("		请输入相应信息进行转换    :	");
		westPane.add(label1);
		westPane.add(text1);
		jpanel1.setLayout(new GridLayout(1,2,25,25));
		label2.setText("输入格式：");
		jpanel1.add(label2);
		//jpanel1.add(text4);
		jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"mol", "CDK", "cml", "rxn","smiles","mol2" }));
		 jComboBox11.addItemListener(new ItemListener(){
           	 public void itemStateChanged(ItemEvent e){         
                	if(e.getStateChange() == ItemEvent.SELECTED){    
                		Informat=(String)jComboBox11.getSelectedItem();   
                	 // Informat = s;            
                	  } 
                }
           });
       
		jpanel1.add(jComboBox11);
		jpanel1.add(button1);     //之所以能添加三个组件是因为gridlayout属性决定，指定行数后，列数实由行数与组件数决定
		
		
		button1.setText("浏览");
		 button1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        		JFileChooser file = new JFileChooser("E:\\MyOffice\\Eclipse\\workspace\\ChemMapper");
	        		file.showOpenDialog(null);
	        		file.setDialogTitle("请选择要输入的文件...");	 
	        	    jmolPanel.viewer.openFile(file.getSelectedFile().getAbsolutePath());
	        	    InformatName = file.getSelectedFile().getName();
	        	   // Informat = text4.getText();
	        	   
	        	    Inpath = file.getSelectedFile().getAbsolutePath();
	        	    text1.setText(file.getSelectedFile().getAbsolutePath());
	        	    System.out.println(Informat);
	        	    
	        	    System.out.println(Inpath);
	        		}
	        	}
	        );
		westPane.add(jpanel1);
		westPane.add(text2);
		jpanel2.setLayout(new GridLayout(1,2,25,25));
		jpanel2.add(label3);
		label3.setText("输出格式：");
		//final JTextField text3 = new JTextField();
		//jpanel2.add(text3);
		 jComboBox22.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"mol2", "pdb", "cdk", "cml" }));
		 jComboBox22.addItemListener(new ItemListener(){
	       	 public void itemStateChanged(ItemEvent e){         
	            	if(e.getStateChange() == ItemEvent.SELECTED){    
	            		 Outformat=(String)jComboBox22.getSelectedItem();
	            	 // Outformat = s;           
	            	  } 
	            }
	       });   
		jpanel2.add(jComboBox22);
		jpanel2.add(button2);
		
		button2.setText("保存");
		
		 button2.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        	
	        		//FileNameExtensionFilter filter=new FileNameExtensionFilter("*.txt","txt");
	        		JFileChooser fc=new JFileChooser("E:\\MyOffice\\Eclipse\\workplace\\ChemMapper");
	        		//fc.setFileFilter(filter);
	        		fc.setMultiSelectionEnabled(false);
	        		int result=fc.showSaveDialog(null);
	        		if (result==JFileChooser.APPROVE_OPTION){
	        			File file=fc.getSelectedFile();
	        			
	        			OutformatName=file.getName(); 
	        			//Outformat = text3.getText();
	        			
	        			Outpath = file.getAbsolutePath();
	        			// File file1 = new File(Outpath);            *********************************
	        		text2.setText(file.getAbsolutePath());
	        		System.out.println(Outformat);//输出流
	        		System.out.println(Outpath);
	       	    
	       	      }
	        		
	        		}
	        	}
	        );
		westPane.add(jpanel2);
		jpanel3.setLayout(new GridLayout(1,2,25,25));
		jpanel3.add(button3);
		button3.setText("化学格式查询");
		
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
        	}
        );
		
		
		jpanel3.add(button4);
		button4.setText("开始转换");
		
		 button4.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        	
	        	formatconv();
	        	//centerPane
	        	jmolPanel.removeAll();
	        	jmolPanel.viewer.openFile(Outpath);
	        	jmolPanel.updateUI();
	        		}

			
	        	}
	        );
		
		
	
		
		westPane.add(jpanel3);
		westPane.add(label4);	
		
		
	}
	
	public void formatconv(){
		 System.loadLibrary("openbabel_java");

	       // Read molecule from informat
	       OBConversion conv = new OBConversion();
	       OBMol mol = new OBMol();
	       
	       conv.SetInFormat(Informat);
	       conv.ReadFile(mol, Inpath);
	    	  
	       conv.SetOutFormat(Outformat);
	       conv.WriteFile(mol, Outpath);
	       
	       JOptionPane.showMessageDialog( null,"转换成功！");
	}
	

}
