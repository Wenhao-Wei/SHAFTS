package com.shafts.ui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.shafts.database.*;

public class loginUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginUI frame = new loginUI();
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
	public loginUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 304, 183);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("\u7528\u6237\u540D");
		lblName.setBounds(31, 21, 43, 24);
		contentPane.add(lblName);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(86, 23, 132, 21);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblKey = new JLabel("\u5BC6\u7801");
		lblKey.setBounds(43, 65, 31, 15);
		contentPane.add(lblKey);
		
		JButton btnLogin = new JButton("\u767B\u9646");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserTable a=new UserTable();
				boolean xx=a.verify(txtUserName.getText(),passwordField.getPassword().toString());
				if(xx) JOptionPane.showMessageDialog( null , "登陆成功" ,"xx" , JOptionPane.ERROR_MESSAGE) ;
				else JOptionPane.showMessageDialog( null , "用户名或密码错误" ,"xxxx" , JOptionPane.ERROR_MESSAGE) ;
			}
		});
		btnLogin.setBounds(10, 111, 68, 23);
		contentPane.add(btnLogin);
		
		JButton btnReg = new JButton("\u6CE8\u518C");
		btnReg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RegisterUI reg=new RegisterUI();
				reg.setVisible(true);
				
			}
		});
		btnReg.setBounds(88, 111, 68, 23);
		contentPane.add(btnReg);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(86, 62, 132, 21);
		contentPane.add(passwordField);
		
		JButton button = new JButton("\u5FD8\u8BB0\u5BC6\u7801\uFF1F");
		button.setBounds(186, 111, 102, 23);
		contentPane.add(button);
	}
}
