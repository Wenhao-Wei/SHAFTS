package com.shafts.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class DataBaseChooser extends JPanel {
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static String inFormat = null;
	public static String outFormat = null;
	public static String inFilePath = null;         //打开文件的绝对路径
	public static String outFilePath = null;       //选择数据库的绝对路径
	public static String fileName = null;
	public static String DataBase = null;
	public static String show3Dname = null;
	public static String Number = null;
	public static String Range = null;

	/*public DataBaseChooser(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		JFrame frame = new JFrame();
		frame.setSize(300,300);
		setTitle("数据库选择");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		//setContentPane(contentPane);
		//this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setSize(600, 400);
		init();
		
	}
	private void init(){
		
		
	}*/
	public static void start(){
		
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
	public static void main(String[] args) {
		/**
		 * 
		//MainUI mu = new MainUI();
		//String filepath = mu.inFilePath;*/
		JPanel panel1;
		 JPanel panel2;
		 JPanel panel3;
		 JPanel panel4;
		 JPanel panel5;
		 JPanel panel6;
		 JPanel tp1;
		 JPanel tp2;
		 JPanel contentPane;
		 JPanel centerJPanel;
		 JComboBox jComboBox11;
	     JComboBox jComboBox22;
		 JTabbedPane jTabbedPane1;
		 JTabbedPane jTabbedPane2;
		  final JTextField jTextField1;
		  final JTextField jTextField2;
		  final JTextField jTextField3;
		  final JTextField jTextField4;
		  final JTextField jTextField5;
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(400,400));
		jTabbedPane1 = new JTabbedPane(JTabbedPane.TOP);
		
		//this.inFilePath=filepath;
		centerJPanel = new JPanel();
		//centerJPanel.setPreferredSize(new Dimension(600,400));
		centerJPanel.setLayout(new BorderLayout());
		centerJPanel.add(jTabbedPane1);
		frame.getContentPane().add(centerJPanel,BorderLayout.CENTER);
		frame.setVisible(true);
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel1.setLayout(new GridLayout(2,1,10,10));
		panel2.setLayout(new GridLayout(2,2,30,30));
		panel3.setLayout(new GridLayout(3,2,30,30));
		tp1 = new JPanel();
		tp2 = new JPanel();
		JLabel jLabel1 = new JLabel();
		JLabel jLabel2 = new JLabel();
		JLabel jLabel3 = new JLabel();
		JLabel jLabel4 = new JLabel();
		JLabel jLabel5 = new JLabel();
		JButton button1 = new JButton();
		JButton button2 = new JButton();
		JButton button3 = new JButton();
		JButton button4 = new JButton();
		JButton button5 = new JButton();
		 jTextField1 = new javax.swing.JTextField();
		 jTextField2 = new javax.swing.JTextField();
		 jTextField3 = new javax.swing.JTextField();
		 jTextField4 = new javax.swing.JTextField();
		 jTextField5 = new javax.swing.JTextField();
		jLabel1.setText("请输入筛选结果个数(<=1000):");
		jLabel2.setText("请输入分子阈值(0~2):");
		panel2.add(jLabel1);
		panel2.add(jTextField1);
		panel2.add(jLabel2);
		panel2.add(jTextField2);
		jLabel3.setText("选择本地数据库文件：");
		jLabel4.setText(" ");
		button1.setText("打开文件");
		button2.setText("开始对比");
		panel3.add(jLabel3);
		panel3.add(jLabel4);
		panel3.add(jTextField3);    //显示选择的本地数据库文件
		panel3.add(button1);
		panel3.add(jLabel4);
		panel3.add(button2);
		panel1.add(panel2);
		panel1.add(panel3);
		
		
		tp1.add(panel1);
		jTabbedPane1.add(" 本地数据库 ",tp1);
		button1.addActionListener(new ActionListener() {       //打开数据库文件
			public void actionPerformed(ActionEvent e){
				JFileChooser file = new JFileChooser("E:\\MyOffice\\Eclipse\\workplace\\ChemMapper");
        		file.showOpenDialog(null);
        		outFilePath=file.getSelectedFile().getAbsolutePath();
        		File file1=new File(outFilePath);
                jTextField3.setText(file1.getName());                //显示选择的数据库名
                DataBase = outFilePath;
                System.out.println(inFormat);
				
			}
		});
		button2.addActionListener(new java.awt.event.ActionListener(){       //开始对比
			public void actionPerformed(java.awt.event.ActionEvent evt){
				start();
            	Number = jTextField1.getText();
            	Range = jTextField2.getText();
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
}
