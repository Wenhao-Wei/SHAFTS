package com.shafts.ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class similarityStart extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					similarityStart frame = new similarityStart();
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
	public similarityStart() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		setTitle("相似性比对！");
	}
	
	String FileName = null;
	String Number = null;
	String Range = null;
	String inFilePath;
	public void init(){
		
		
		
		JPanel jpanel1 = new JPanel();
		JPanel jpanel2 = new JPanel();
		JPanel jpanel3 = new JPanel();
		JPanel jpanel4 = new JPanel();
		jpanel1.setLayout(new GridLayout(5,1,25,25));
		jpanel2.setLayout(new GridLayout(1,2,20,20));
		jpanel3.setLayout(new GridLayout(1,2,20,20));
		jpanel4.setLayout(new GridLayout(1,2,20,20));
		final JTextField text = new JTextField();
		final JTextField text2 = new JTextField();
		final JTextField text3 = new JTextField();
		JLabel label =  new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 =  new JLabel();
		JButton button = new JButton();
		JButton button2 = new JButton();
		
		getContentPane().add(jpanel1);
		jpanel1.add(text);
		
		jpanel1.add(jpanel2);
		jpanel2.add(label);
		jpanel2.add(button);
		button.setText("选择文件");
		button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	

        		JFileChooser file = new JFileChooser("E:\\MyOffice\\Eclipse\\workplace\\ChemMapper");
        		file.showOpenDialog(null);
        		file.setDialogTitle("请选择要打开的文件...");
        		
        	   
        	    FileName = file.getName();
        	    text.setText(file.getSelectedFile().getAbsolutePath());
            	inFilePath = file.getSelectedFile().getAbsolutePath();
            	
            }
        });
		
		
		
		
		jpanel1.add(jpanel3);
		jpanel3.add(label2);
		label2.setText("输入筛选结果个数(<=1000) ： ");
		jpanel3.add(text2);
		
		jpanel1.add(jpanel4);
		jpanel4.add(label3);
		label3.setText("输入分子阈值(范围0~2) ： ");
		jpanel4.add(text3);
		
		jpanel1.add(button2);
		button2.setText("SHAFTS 查找分子相似性！");
		button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	start();
            	Number = text2.getText();
            	Range = text3.getText();
            	Desktop desktop = Desktop.getDesktop();  
            	try {
					desktop.open(new File("E:\\MyOffice\\Eclipse\\workplace\\ChemMapper\\Result.list"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            }
        });
		
		
	
		

	}
	public void start(){
		
		 InputStream ins = null;
		 String cmd = "E:\\MyOffice\\Eclipse\\workplace\\ChemMapper\\Cynthia.exe -q "+inFilePath+ " -t "+"E:\\MyOffice\\Eclipse\\workplace\\ChemMapper\\TEST.mol2";
	        try {
	       
	            Process process = Runtime.getRuntime().exec(cmd);
	             ins = process.getInputStream(); //cmd 的信息
	             
	             BufferedReader reader = new BufferedReader(new InputStreamReader(ins));   
	                String line = null;   
	                while ((line = reader.readLine()) != null) {   
	                    System.out.println(line);  //输出 
	                } 
	                
	                int exitValue = process.waitFor();   
	                System.out.println("返回值：" + exitValue);  
	                process.getOutputStream().close(); //不要忘记了一定要关
	                
	        } catch (Exception e) {
	            
	            e.printStackTrace();
	        } 
	}
	
}
