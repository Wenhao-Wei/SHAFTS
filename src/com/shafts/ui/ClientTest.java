package com.shafts.ui;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

public class ClientTest {
	private ClientSocket cs = null;

	//private String ip = "localhost";// ���óɷ�����IP
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
			System.out.print("���ӷ������ɹ�!" + "\n");
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
			//System.out.print("������Ϣʧ��!" + "\n");
			JOptionPane.showMessageDialog( null,"�������⣡");
		}
	}

    public void getMessage(String savePath) {
		if (cs == null){
			return;}             //0��ʾ����δ�ɹ�����ջ������
		DataInputStream inputStream = null;
		try {
			inputStream = cs.getMessageStream();
		} catch (Exception e) {
			//System.out.print("������Ϣ�������\n");
			JOptionPane.showMessageDialog( null,"���մ���");
			//return 0;
		}
		
		try {
			// ���ر���·�����ļ������Զ��ӷ������˼̳ж�����
			
			// savePath = "F:\\";
			// savePath1 = "F:\\"+;
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;			
			Message = inputStream.readUTF();
			if(Message.equals("NFile"))              //2014.2.18  �쳣���
				 JOptionPane.showMessageDialog( null,"������Ч��JobID!");
	        else if(Message.equals("NResult"))
	        	JOptionPane.showMessageDialog( null,"���ڼ����У������ĵȴ�!");
	            else{  
	            	String savePath1 = savePath;
			           savePath += Message;			    
			           savePath1 += Message.substring(0, Message.lastIndexOf("."));
			           System.out.println("��ѹ�ļ������ڣ�" + savePath1);
			     DataOutputStream fileOut = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(savePath)));
			     len = inputStream.readLong();           
			     System.out.println("�ļ��ĳ���Ϊ:" + len + "\n");
			     System.out.println("��ʼ�����ļ�!" + "\n");
			     while (true) {
				     int read = 0;
				     if (inputStream != null) {
					     read = inputStream.read(buf);
				         }
				     passedlen += read;
				    if (read == -1) {
					     break;
				        }
				       // �����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
				       System.out.println("�ļ�������" + (passedlen * 100 % len) + "%\n");
				       fileOut.write(buf, 0, read);
			          }
			System.out.println("������ɣ��ļ���Ϊ" + savePath + "\n");
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
			JOptionPane.showMessageDialog( null,"���سɹ�����������ɣ�");
		  }
		} 
		catch (Exception e) {}
	}
      /*public static void main(String arg[]) {
		String sendMessage1 = "19895";
		new ClientTest(sendMessage1).getMessage();
	  } */  
}
