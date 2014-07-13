package com.shafts.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LaunchAnimation extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path = null;
	public LaunchAnimation(){
		Toolkit kit = Toolkit.getDefaultToolkit();
     	Dimension screensize = kit.getScreenSize();
		int width = screensize.width;
     	int height = screensize.height;
     	int x = (width - 462)/2;
     	int y = (height - 462)/2;
     	setLocation(x, y);
     	
		setSize(462, 462);
		//setResizable(false);
		
		init();
	}
	public void init(){
		path = System.getProperty("user.dir") + "\\Pictures";
		//Color color1 = new Color(0.9f,0.7f,0.9f,0.5f);
		//getContentPane().setLayout(null);
		JPanel panel = new animationPanel();
		//panel.setBackground(color1);
		JPanel panel2 = new JPanel();
		panel.setBorder(new EmptyBorder(0,0,5,0));
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(path + "\\Launch.jpg"));
		//panel2.setBackground(color);
		panel2.add(label);
		//panel.setBounds(0, 20, 462, 18);
		getContentPane().add(panel2, BorderLayout.CENTER);
		getContentPane().add(panel,BorderLayout.SOUTH);
	}
/*	class picturePanel extends JPanel{

		/**
		 * 
		 
		private static final long serialVersionUID = 1L;
		private JLabel label;
		public void paint(Graphics g) {
			super.paint(g);
			// ImageIcon icon = new ImageIcon("D:\\1.jpg");
			//String path = System.getProperty("user.dir") + "\\Pictures\\Launch.jpg";
			ImageIcon icon = new ImageIcon(path + "\\Launch.jpg");
		
	}*/
	class animationPanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public void paint(Graphics g) {
			super.paint(g);
			// ImageIcon icon = new ImageIcon("D:\\1.jpg");
			//String path = System.getProperty("user.dir") + "\\Pictures\\005.gif";
			ImageIcon icon = new ImageIcon(path + "\\005.gif");
			g.drawImage(icon.getImage(), 0, 0, 462, 20, this);
		}
		
	}
	public void close(){
		dispose();
	}
	public static void main(String args[]){
		LaunchAnimation la = new LaunchAnimation();
		la.setUndecorated(true);
		la.setVisible(true);
     	//Color color = new Color(0.9f,0.7f,0.8f,0.5f);
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setLocationRelativeTo(null);
		//setBackground(color);
	} 

}
