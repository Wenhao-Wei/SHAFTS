package com.shafts.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ProgressBar implements ChangeListener{
	   
	   JProgressBar progressbar;
	   JPanel panel;
	   JFrame f ;
	   JLabel label;
	   JButton button;
	   public ProgressBar(){
		
		   f = new JFrame("下载进度");
		   button = new JButton("开始");
		   Container contentPane = f.getContentPane();
		   progressbar = new JProgressBar();
		   progressbar.setOrientation(JProgressBar.HORIZONTAL);// 设置方向
		   progressbar.setMaximum(100);
		   progressbar.setMinimum(0);
		   progressbar.setValue(0);
		   progressbar.setStringPainted(true);
		   progressbar.addChangeListener(this);
		   progressbar.setPreferredSize(new Dimension(200,30));
		   panel = new JPanel();
		   panel.setLayout(new GridLayout(3,1,10,10));
		   label = new JLabel("",JLabel.CENTER);
		   contentPane.add(panel);
		   panel.add(button);
		   panel.add(progressbar);
		   panel.add(label);
		   
		   f.pack();
		   f.setVisible(true);
		   f.addWindowListener(new WindowAdapter(){
			   
			   public void windowClosing(WindowEvent e){
				   System.exit(0);
			   }
		   });
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent Event){
			int value = 0;
			while(value<10000){
				value++;
				progressbar.setValue(value);
			    
			}
			//f.dispose();
		  }
		});
		
		
	}
	   
	 public void close(){
		 System.exit(0);
	 }  
	 
	@Override
	
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		int value = progressbar.getValue();
		label.setText("文件已接收："+ Integer.toString(value)+"%");
	}
	public static void main(String args[]){
		new ProgressBar();
		   
	}
}
