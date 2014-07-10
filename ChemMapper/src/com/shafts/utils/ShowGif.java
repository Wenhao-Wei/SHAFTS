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
     	setUndecorated(true);
     	Color color = new Color(0.9f,0.7f,0.8f,0.5f);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setLocationRelativeTo(null);
		//setBackground(color);
		setSize(300, 50);
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
		setVisible(true);
	}
	public static void main(String[] args) {
		new ShowGif();
	}
	class ImagePanel extends JPanel {
		public void paint(Graphics g) {
			super.paint(g);
			// ImageIcon icon = new ImageIcon("D:\\1.jpg");
			ImageIcon icon = new ImageIcon("E:\\004.gif");
			g.drawImage(icon.getImage(), 0, 0, 300, 10, this);
		}
	}
	public void close(){
		dispose();
	}
} 
