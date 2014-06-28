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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.util.Dictionary;
import java.util.Hashtable;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class UserDataConfig extends JFrame {

	private JPanel contentPane;
	private JPasswordField password;
	private JPasswordField rePassword;
	private JTextField txtNickname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserDataConfig frame = new UserDataConfig();
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
	public UserDataConfig() {
		setResizable(false);
		final Hashtable<JTextField,JLabel> xx=new Hashtable<JTextField,JLabel>();
		setTitle("\u8D44\u6599\u4FEE\u6539");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JPanel panel_1 = new JPanel();
		panel_1.setVisible(false);
		panel_1.setBounds(10, 71, 394, 85);
		contentPane.add(panel_1);
		
		JLabel lblKey = new JLabel("\u5BC6\u7801");
		lblKey.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblKey.setBounds(10, 10, 69, 26);
		
		JLabel lblKeyConfirm = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		lblKeyConfirm.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblKeyConfirm.setBounds(10, 46, 69, 26);
		panel_1.setLayout(null);
		panel_1.add(lblKey);
		panel_1.add(lblKeyConfirm);
		
		password = new JPasswordField();
		password.setBounds(79, 13, 123, 21);
		panel_1.add(password);
		
		rePassword = new JPasswordField();
		rePassword.setBounds(79, 49, 123, 21);
		panel_1.add(rePassword);
		
		JLabel lblWarn_3 = new JLabel("\u5BC6\u7801\u4E0D\u80FD\u4E3A\u7A7A");
		lblWarn_3.setVisible(false);
		lblWarn_3.setBounds(212, 16, 97, 15);
		panel_1.add(lblWarn_3);
		
		JLabel lblWarn_4 = new JLabel("\u4E24\u6B21\u5BC6\u7801\u8F93\u5165\u4E0D\u4E00\u81F4");
		lblWarn_4.setVisible(false);
		lblWarn_4.setBounds(212, 52, 123, 15);
		panel_1.add(lblWarn_4);
		
		JButton btnSubmit = new JButton("\u63D0\u4EA4");
		btnSubmit.setBounds(158, 183, 93, 23);
		contentPane.add(btnSubmit);
		xx.put(password, lblWarn_3);
		xx.put(rePassword, lblWarn_4);
		
		JLabel lblNickname = new JLabel("\u6635\u79F0");
		lblNickname.setAlignmentY(0.0f);
		lblNickname.setAlignmentX(1.0f);
		lblNickname.setBounds(20, 30, 69, 26);
		contentPane.add(lblNickname);
		
		txtNickname = new JTextField();
		txtNickname.setColumns(10);
		txtNickname.setBounds(89, 30, 123, 26);
		contentPane.add(txtNickname);
		
		JCheckBox checkBox = new JCheckBox("\u4FEE\u6539\u5BC6\u7801");
		checkBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_1.setVisible(((JCheckBox)e.getSource()).isSelected());
			}
		});
		
		checkBox.setBounds(243, 32, 103, 23);
		contentPane.add(checkBox);
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
		password.addInputMethodListener(warn);
		rePassword.addInputMethodListener(warn);
	}
	}


