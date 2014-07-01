package com.shafts.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Component;

import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.JButton;

import com.shafts.database.UserTable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.util.Dictionary;
import java.util.Hashtable;

public class RegisterUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtMail;
	private JPasswordField password;
	private JPasswordField rePassword;
	private JTextField txtAff;
	private JTextField txtOrg;
	private JTextField txtContect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterUI frame = new RegisterUI();
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
	public RegisterUI() {
		setResizable(false);
		final Hashtable<JTextField,JLabel> xx=new Hashtable<JTextField,JLabel>();
		setTitle("\u7528\u6237\u6CE8\u518C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JPanel panelNecessary = new JPanel();
		panelNecessary.setBorder(new TitledBorder(null, "\u5FC5\u586B\u9879", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelNecessary.setBounds(10, 10, 414, 202);
		contentPane.add(panelNecessary);
		panelNecessary.setLayout(null);
		
		final JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 22, 394, 157);
		panelNecessary.add(panel_1);
		
		JLabel lblUserName = new JLabel("\u7528\u6237\u540D");
		lblUserName.setAlignmentY(Component.TOP_ALIGNMENT);
		lblUserName.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblUserName.setBounds(10, 10, 69, 26);
		
		
		txtUserName = new JTextField();
		
		txtUserName.setToolTipText("\u53EF\u4EE5\u5305\u542B\u6570\u5B57\uFF0C\u5927\u5C0F\u5199\u82F1\u6587\u5B57\u6BCD\uFF0C\u652F\u6301\u90AE\u7BB1\u683C\u5F0F");
		
		txtUserName.setBounds(79, 10, 123, 26);
		txtUserName.setColumns(10);
		
		JLabel lblMail = new JLabel("\u6CE8\u518C\u90AE\u7BB1");
		lblMail.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblMail.setBounds(10, 46, 69, 26);
		
		txtMail = new JTextField();
		txtMail.setToolTipText("\u7528\u4E8E\u627E\u56DE\u5BC6\u7801\uFF0C\u4FE1\u606F\u63A8\u9001");
		txtMail.setBounds(79, 47, 123, 26);
		txtMail.setColumns(10);
		
		JLabel lblKey = new JLabel("\u5BC6\u7801");
		lblKey.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblKey.setBounds(10, 82, 69, 26);
		
		JLabel lblKeyConfirm = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		lblKeyConfirm.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblKeyConfirm.setBounds(10, 118, 69, 26);
		panel_1.setLayout(null);
		panel_1.add(lblUserName);
		panel_1.add(txtUserName);
		panel_1.add(lblMail);
		panel_1.add(txtMail);
		panel_1.add(lblKey);
		panel_1.add(lblKeyConfirm);
		
		password = new JPasswordField();
		password.setBounds(79, 85, 123, 21);
		panel_1.add(password);
		
		rePassword = new JPasswordField();
		rePassword.setBounds(79, 121, 123, 21);
		panel_1.add(rePassword);
		
		JLabel lblWarn_1 = new JLabel("\u7528\u6237\u540D\u4E0D\u5408\u6CD5");
		lblWarn_1.setVisible(false);
		lblWarn_1.setBounds(212, 16, 97, 15);
		panel_1.add(lblWarn_1);
		
		JLabel lblWarn_2 = new JLabel("\u90AE\u7BB1\u4E0D\u5408\u6CD5");
		lblWarn_2.setVisible(false);
		lblWarn_2.setBounds(212, 52, 97, 15);
		panel_1.add(lblWarn_2);
		
		JLabel lblWarn_3 = new JLabel("\u5BC6\u7801\u4E0D\u80FD\u4E3A\u7A7A");
		lblWarn_3.setVisible(false);
		lblWarn_3.setBounds(212, 88, 97, 15);
		panel_1.add(lblWarn_3);
		
		JLabel lblWarn_4 = new JLabel("\u4E24\u6B21\u5BC6\u7801\u8F93\u5165\u4E0D\u4E00\u81F4");
		lblWarn_4.setVisible(false);
		lblWarn_4.setBounds(212, 124, 123, 15);
		panel_1.add(lblWarn_4);
		
		JPanel panelSelectable = new JPanel();
		panelSelectable.setLayout(null);
		panelSelectable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u9009\u586B\u9879", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSelectable.setBounds(10, 222, 414, 202);
		contentPane.add(panelSelectable);
		
		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(10, 22, 394, 157);
		panelSelectable.add(panel_2);
		
		JLabel lblAff = new JLabel("\u6240\u5C5E\u5355\u4F4D");
		lblAff.setAlignmentY(0.0f);
		lblAff.setAlignmentX(1.0f);
		lblAff.setBounds(10, 10, 69, 26);
		panel_2.add(lblAff);
		
		txtAff = new JTextField();
		txtAff.setColumns(10);
		txtAff.setBounds(79, 10, 123, 26);
		panel_2.add(txtAff);
		
		JLabel lblOrg = new JLabel("\u6240\u5C5E\u7FA4\u4F53");
		lblOrg.setAlignmentX(1.0f);
		lblOrg.setBounds(10, 46, 69, 26);
		panel_2.add(lblOrg);
		
		txtOrg = new JTextField();
		txtOrg.setColumns(10);
		txtOrg.setBounds(79, 47, 123, 26);
		panel_2.add(txtOrg);
		
		JLabel lblContact = new JLabel("\u8054\u7CFB\u65B9\u5F0F");
		lblContact.setAlignmentX(1.0f);
		lblContact.setBounds(10, 85, 69, 26);
		panel_2.add(lblContact);
		
		txtContect = new JTextField();
		txtContect.setColumns(10);
		txtContect.setBounds(79, 86, 123, 26);
		panel_2.add(txtContect);
		
		JButton btnSubmit = new JButton("\u63D0\u4EA4");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] sub={txtUserName.getText(),txtMail.getText()};
				UserTable a=new UserTable();
				if(true
						//Âú×ãÌõ¼þ
						)
				a.add(txtUserName.getText(), password.getPassword().toString(),
						txtMail.getText(), txtAff.getText(), txtOrg.getText(), 
						txtContect.getText());
			}
		});
		btnSubmit.setBounds(94, 434, 93, 23);
		contentPane.add(btnSubmit);
		JButton btnRe = new JButton("\u91CD\u586B");
		btnRe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer count = panel_1.getComponentCount();
				for (int i = 0; i < count; i++) {
					Component comp = panel_1.getComponent(i);
					
					if(comp instanceof JTextField){
						((JTextField) comp).setText("");
					}
					if(comp instanceof JPasswordField){
						((JPasswordField) comp).setText("");
					}
				}
				count = panel_2.getComponentCount();
				for (int i = 0; i < count; i++) {
					Component comp = panel_2.getComponent(i);
					
					if(comp instanceof JTextField){
						((JTextField) comp).setText("");
					}
					if(comp instanceof JPasswordField){
						((JPasswordField) comp).setText("");
					}
				}
			}
		});
		btnRe.setBounds(247, 434, 93, 23);
		contentPane.add(btnRe);
		
		xx.put(txtUserName, lblWarn_1);
		xx.put(txtMail, lblWarn_2);
		xx.put(password, lblWarn_3);
		xx.put(rePassword, lblWarn_4);
		InputMethodListener warn=new InputMethodListener() {
			public void inputMethodTextChanged(InputMethodEvent e) {
				if(false){
				xx.get(e.getSource()).setVisible(true);
				}
				else{
					xx.get(e.getSource()).setVisible(false);
				}
			}
			public void caretPositionChanged(InputMethodEvent e) {
			}
		};
		txtUserName.addInputMethodListener(warn);
		txtMail.addInputMethodListener(warn);
		password.addInputMethodListener(warn);
		rePassword.addInputMethodListener(warn);
	}
}
