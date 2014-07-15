package com.shafts.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.birosoft.liquid.LiquidLookAndFeel;
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
					
					//JFrame.setDefaultLookAndFeelDecorated(true);
					// JDialog.setDefaultLookAndFeelDecorated(true);
						//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
						//LiquidLookAndFeel.setLiquidDecorations(true, "mac");
						// LiquidLookAndFeel.setLiquidDecorations(true);					 
					 JcpUI frame = new JcpUI();
					 //frame.setUndecorated(true);
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
		setBounds(100, 100,680, 500);//800£¬600
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
