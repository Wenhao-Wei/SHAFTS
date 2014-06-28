package com.shafts.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.shafts.utils.JCPPanel1;

public class JcpUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JcpUI frame = new JcpUI();
					frame.setVisible(true);
					System.out.println(JcpUI.DD_FILE_NAME);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JcpUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JCPPanel1 jcpPanel2 = new JCPPanel1();
		contentPane.add(jcpPanel2);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	/**
	 * return 2D molecule name
	 */
	public static String get2DFileName()
	{
		return "shafts-file-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".mol";
	}
	public final static String DD_FILE_NAME = get2DFileName();

}
