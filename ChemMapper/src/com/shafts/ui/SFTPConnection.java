package com.shafts.ui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;


public class SFTPConnection implements ChangeListener{
         
	   private String host;
	   private String username;
	   private String password;
	   private int port = 2222;
	   private ChannelSftp sftp = null;
	   private Session sshSession = null;
	   JProgressBar progressbar;
	   JFrame f = null;
	   JLabel label;
	   JLabel label1;
	   JPanel panel;
	   public void thebar(){
		   Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包
	        Dimension screenSize = kit.getScreenSize();             //获取屏幕的尺寸
	        int screenWidth = screenSize.width;                     //获取屏幕的宽
	        int screenHeight = screenSize.height;                   //获取屏幕的高
	       panel = new JPanel();
	       panel.setLayout(new GridLayout(3,1,10,10));
		   f = new JFrame("下载进度");
		   Container contentPane = f.getContentPane();
		   progressbar = new JProgressBar();
		   progressbar.setOrientation(JProgressBar.HORIZONTAL);// 设置方向
		   progressbar.setMaximum(100);
		   progressbar.setMinimum(0);
		   progressbar.setValue(0);
		   progressbar.setStringPainted(true);
		   progressbar.addChangeListener(this);
		   progressbar.setPreferredSize(new Dimension(200,20));
		   //JPanel panel = new JPanel();
		   label1 = new JLabel("正在下载中，请稍后...",JLabel.CENTER);
		   label = new JLabel("",JLabel.CENTER);
		   panel.add(label1);
		   panel.add(progressbar);
		   panel.add(label);
		   contentPane.add(panel);
		   
		   f.pack();
		   f.setVisible(true);
		   f.setAlwaysOnTop(true);
		   f.setLocation(screenWidth/2-100, screenHeight/2-25);
	   }
	   
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		int value = progressbar.getValue();
		label.setText("文件已接收："+ Integer.toString(value)+"%");
	}
	   
       public SFTPConnection(){	
    	    this.host = "59.78.96.61";
      		this.username = "whwei";
      		this.password = "123456";
      		//this.port = port;
	   }
       public SFTPConnection(String host, String username, String password, int port) {
   		this.host = host;
   		this.username = username;
   		this.password = password;
   		this.port = port;
   	}

   	    public SFTPConnection(String host, String username, String password) {
   		this.host = host;
   		this.username = username;
   		this.password = password;
   	}
       /**
        * connect the server
        */
       public void connect(){
    	   
    	   try{
    		   JSch jsch = new JSch();
    		   jsch.getSession(username, host, port);
    		   sshSession = jsch.getSession(username, host, port);
    		   sshSession.setPassword(password);
    		   Properties sshConfig = new Properties();
    		   sshConfig.put("StrictHostKeyChecking", "no");
    		   sshSession.setConfig(sshConfig);
    		   sshSession.connect();
    		   Channel channel = sshSession.openChannel("sftp");
    		   channel.connect();
    		   sftp = (ChannelSftp) channel;
    		       		  		   
    	   }catch(Exception e){
    	       JOptionPane.showMessageDialog( null,"连接服务器异常！");
    	   }
    	   
       }
       /**
        * close the connection
        */
       public void disconnect(){
    	   if(this.sftp!=null){
    		   if(this.sftp.isConnected())
    			   this.sftp.disconnect();
    	   }
    	   if(this.sshSession!=null){
    		   if(this.sshSession.isConnected())
    			   this.sftp.disconnect();
    		   System.out.println("sshSession is closed already");
    	   }    		   
       }
       /**
        * check if the file exist
        * @return
        */
       public boolean isDirExist(String directory){
    	   
    	   boolean isDirExistflag = false;
    	   try{
    	        SftpATTRS sftpATTRS = sftp.lstat(directory);
    	        isDirExistflag = true;
    	        return sftpATTRS.isDir();
    	        }catch(Exception e){
    	        	if(e.getMessage().toLowerCase().equals("no such file")){
    	        		isDirExistflag = false;
    	        	}
    	        }
    	   return isDirExistflag;
       }
       
       /**
        * create a new directory if not exist
        * @param path
        */
       public void mkdirs(String path){
    	   
    	   File f = new File(path);
    	   String fs = f.getParent();
    	   f = new File(fs);
    	   if(!f.exists()){
    		   f.mkdirs();
    	   }
       }
       /**
        * download single file
        * @param remotePath
        *        服务器端路径
        * @param remoteFileName
        *        下载文件名
        * @param localPath
        *        本地存放路径
        * @param localFileName
        *        本地存放文件
        */
       public void downloadfile(String remotePath, String remoteFileName, String localPath, String localFileName){
    	   
    	   try{
    		   sftp.cd(remotePath);
    		   File file = new File(localPath + localFileName);
    		   mkdirs(localPath + localFileName);
    		   sftp.get(remoteFileName, new FileOutputStream(file));    		   
    	   }catch(FileNotFoundException e){
    		   e.printStackTrace();
    	   }
    	   catch(SftpException e){
    		   e.printStackTrace();
    	   }
       }
       
       /**
        *batch download the files
        *@param serverPath
        *       服务器端jobid路径
        *@param serverFileName
        *       result文件夹内容
        *@param localPath
        *       本地存放路径
        */
       public void batchDownLoadFile(String jobid,String localPath){
    	   
    	   String serverPath = "/home2/scbit/WebApps/FeatureAlign/jobs/" + jobid + "/";
    	   String serverFileName = "/home2/scbit/WebApps/FeatureAlign/jobs/" + jobid + "/result/";
    	   boolean ifexistdir = isDirExist(serverPath);
    	   try{
    		   int i = 1;
    		   int V;
    		   connect();
    		   if(ifexistdir){
    			   //ProgressBar Pb = new ProgressBar();
    			   thebar();
    			   Vector v = listFiles(serverFileName);
    			   if(v.size() > 0){
    				   Iterator it = v.iterator();
    				   while(it.hasNext()){
    					   LsEntry entry = (LsEntry)it.next();
    					   String filename = entry.getFilename();
    					 //  System.out.println(filename);
    					   SftpATTRS attrs = entry.getAttrs();
    					   if(!attrs.isDir()){
    						   downloadfile(serverFileName, filename, localPath, filename);
    						   i++;
    						   V = i*100 / v.size();
        					   progressbar.setValue(V);
    					   }
    					  // System.out.println("文件传输了： "+(i*100 / v.size())+"%");
    					   
    				   }
    				   //Pb.close();
    				   f.dispose();
    				   JOptionPane.showMessageDialog( null,"下载成功！解析已完成！");
    			   }
    		   } 
    		   else{
    			   JOptionPane.showMessageDialog( null,"正在计算中...");
    		   }
    	   }catch(SftpException e){
    		   e.printStackTrace();
    	   }finally{
    		   this.disconnect();
    	   }    	   
       }
       
       /**
        * 文件列表
        * @param path
        * @return
        * @throws SftpException
        */
       public Vector listFiles(String path) throws SftpException{
    	   return sftp.ls(path);
       }
       
       public String getHost(){
    	   return host;
       }
       public void setHost(String host){
    	   this.host = host;
       }
       public String getUserName(){
    	   return username;
       }
       public void setUserName(String username){
    	   this.username = username;
       }
       public String getPassword(){
    	   return password;
       }
       public void setPassword(String password){
    	   this.password = password;
       }
       public int getPort(){
    	   return port;
       }
       public void setPort(int port){
    	   this.port = port;
       }
       public ChannelSftp getSftp(){
    	   return sftp;
       } 
       public void setSftp(ChannelSftp sftp){
    	   this.sftp = sftp;
       }
       public static void main(String args[]){
    	   //SFTPConnection sftp = new SFTPConnection("59.78.96.61", "whwei", "123456");
    	   SFTPConnection sftp = new SFTPConnection();
    	   String jobid = "19895";
    	   String localPath = "D:\\" + jobid +"\\";
    	   sftp.connect();
    	   sftp.batchDownLoadFile(jobid, localPath);
    	   sftp.disconnect();
    	   System.exit(0);
       }
       
}  
