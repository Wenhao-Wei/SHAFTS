package com.shafts.utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * 魏文浩
 * 倒计时对话框 
 * <p>2014-06-27</p>
 */
public class TimerMessageDialog extends JDialog{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/** 标签文本     */
  protected static Label localMessageLabel;
  private int WIDTH = 300;
  private int HEIGHT = 150;
  private JPanel jp;
  private JPanel jp1;
  private JLabel jb;
  private JLabel jb1;
  private JButton now;
  private JButton shutdown;
  private int i = 5;

  public TimerMessageDialog(){
	  this.setModal(true);
	  Toolkit kit = Toolkit.getDefaultToolkit();
   	  Dimension screensize = kit.getScreenSize();
   	  int width = screensize.width;
   	  int height = screensize.height;
   	  int x = (width - WIDTH)/2;
   	  int y = (height - HEIGHT)/2;
   	  this.setLocation(x-50, y-100);
	  this.setName("倒计时");
	  this.setDefaultCloseOperation(TimerMessageDialog.DISPOSE_ON_CLOSE);
	  this.setSize(WIDTH, HEIGHT);
	  initialize();
  }
  /**
   * 初始化倒计时重启
   */
 private void initialize() {
	jp = new JPanel(new BorderLayout());
	jp1 = new JPanel(new GridLayout(1,3,10,10));
	jb = new JLabel("                系统将在"+i+"秒后重新启动...");
	jb1 = new JLabel();
	now = new JButton("立即重启");
	shutdown = new JButton("关闭系统");
	new JSeparator();
	this.add(jp);
	jp.add(jb,BorderLayout.CENTER);	
	jp.add(jp1,BorderLayout.SOUTH);	
	jp1.add(jb1);
	jp1.add(now);
	jp1.add(shutdown);
	new Thread(){
		public void run(){
	while(i>0){		
		try {
			new Thread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		i = i - 1;
		jb.setText("                系统将在"+i+"秒后重新启动...");
		jp.updateUI();
		if(i == 0)
			close();
				}
			}
		}.start();
	now.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			close();			
		}
	});
	shutdown.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			dispose();			
		}
	});	
	this.setVisible(true);
 }
 /**
  * 关闭系统
  */
 private void close(){
	 this.dispose();
 }  
 public static void main(String args[]){
	 new TimerMessageDialog();
 }  
}
