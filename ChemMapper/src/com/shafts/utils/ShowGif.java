package com.shafts.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.*;
public class ShowGif extends JDialog {
	public ShowGif() {
		Toolkit kit = Toolkit.getDefaultToolkit();
     	Dimension screensize = kit.getScreenSize();
		int width = screensize.width;
     	int height = screensize.height;
     	int x = (width - 300)/2;
     	int y = (height - 50)/2;
     	setLocation(x-50, y-100);
     	
		//setLocationRelativeTo(null);
		//setBackground(color);
		setSize(300, 50);
		init();
		
	}
	public void init(){
		Color color = new Color(0.9f,0.7f,0.8f,0.5f);		
		//setResizable(false);
		Color color1 = new Color(0.9f,0.7f,0.9f,0.5f);
		//getContentPane().setLayout(null);
		JPanel panel = new ImagePanel();
		panel.setBackground(color1);
		JPanel panel2 = new JPanel();
		JLabel lable = new JLabel("Please Waiting!");
		panel2.setBackground(color);
		panel2.add(lable);
		panel.setBounds(0, 20, 300, 10);
		getContentPane().add(panel2, BorderLayout.NORTH);
		getContentPane().add(panel,BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		ShowGif dialog = new ShowGif();
		dialog.setUndecorated(true);
		dialog.setVisible(true);
     	//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	class ImagePanel extends JPanel {
		public void paint(Graphics g) {
			super.paint(g);
			// ImageIcon icon = new ImageIcon("D:\\1.jpg");
			String path = System.getProperty("user.dir") + "\\Pictures\\004.gif";
			ImageIcon icon = new ImageIcon(path);
			g.drawImage(icon.getImage(), 0, 0, 300, 10, this);
		}
	}
	public void close(){
		dispose();
	}
} 
