package com.shafts.ui;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

public class ClientTest {
	private ClientSocket cs = null;

	//private String ip = "localhost";// 设置成服务器IP
	private String ip = "172.20.46.32";   // 172.20.46.32   59.78.96.61


	private int port = 8821;  //8821
	//private String savePath;
	//private String savePath1;
	private String sendMessage = "Windows"; //Linux  Windows
	//private String sendMessage1;// = "198951";//19895   Windows
    private String Message = null;
	public ClientTest(String Sendmessage2,String savePath) {
		try {
			if (createConnection()) {
				sendMessage(Sendmessage2);
				getMessage(savePath);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean createConnection() {
		cs = new ClientSocket(ip, port);
		try {
			cs.CreateConnection();
			System.out.print("连接服务器成功!" + "\n");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	private void sendMessage(String sendMessage1) {
		if (cs == null)			
			return;
		try {
			//savePath1 = "F:\\" + sendMessage1;
			cs.sendMessage(sendMessage1);
			cs.sendMessage(sendMessage);
		} catch (Exception e) {
			//System.out.print("发送消息失败!" + "\n");
			JOptionPane.showMessageDialog( null,"网络问题！");
		}
	}

    public void getMessage(String savePath) {
		if (cs == null){
			return;}             //0表示链接未成功或接收缓存错误
		DataInputStream inputStream = null;
		try {
			inputStream = cs.getMessageStream();
		} catch (Exception e) {
			//System.out.print("接收消息缓存错误\n");
			JOptionPane.showMessageDialog( null,"接收错误！");
			//return 0;
		}
		
		try {
			// 本地保存路径，文件名会自动从服务器端继承而来。
			
			// savePath = "F:\\";
			// savePath1 = "F:\\"+;
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;			
			Message = inputStream.readUTF();
			if(Message.equals("NFile"))              //2014.2.18  异常结果
				 JOptionPane.showMessageDialog( null,"不是有效的JobID!");
	        else if(Message.equals("NResult"))
	        	JOptionPane.showMessageDialog( null,"正在计算中，请耐心等待!");
	            else{  
	            	String savePath1 = savePath;
			           savePath += Message;			    
			           savePath1 += Message.substring(0, Message.lastIndexOf("."));
			           System.out.println("解压文件将存在：" + savePath1);
			     DataOutputStream fileOut = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(savePath)));
			     len = inputStream.readLong();           
			     System.out.println("文件的长度为:" + len + "\n");
			     System.out.println("开始接收文件!" + "\n");
			     while (true) {
				     int read = 0;
				     if (inputStream != null) {
					     read = inputStream.read(buf);
				         }
				     passedlen += read;
				    if (read == -1) {
					     break;
				        }
				       // 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
				       System.out.println("文件接收了" + (passedlen * 100 % len) + "%\n");
				       fileOut.write(buf, 0, read);
			          }
			System.out.println("接收完成，文件存为" + savePath + "\n");
			fileOut.close();			
			ZipManager manager = new ZipManager();
			try {
			    manager.releaseZipToFile(savePath,savePath1);
				  // manager.createZip(filePath, filePath+".zip");
			    File file = new File(savePath);
				file.delete();
			   } catch (Exception e) {
			   // e.printStackTrace();
			   }			 
			JOptionPane.showMessageDialog( null,"下载成功！解析已完成！");
		  }
		} 
		catch (Exception e) {}
	}
      /*public static void main(String arg[]) {
		String sendMessage1 = "19895";
		new ClientTest(sendMessage1).getMessage();
	  } */  
}
