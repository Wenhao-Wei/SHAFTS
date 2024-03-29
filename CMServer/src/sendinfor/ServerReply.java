package sendinfor;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.swing.JOptionPane;

import databaseinfor.IsAuthorized;

public class ServerReply {
	private int port = 8821;
	private DataInputStream inputStreamReader = null;
	private int message;
	private int flag = 0;
	private int days = 0;
	private String mac;
	
    void start(){
    	Socket s = null;
    	
    	try{
    		ServerSocket ss = new ServerSocket(port);
    		while(true){
    			s = ss.accept();
    			inputStreamReader = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    			message = inputStreamReader.readInt();
    			mac = inputStreamReader.readUTF();
    			switch(message){
    				//判断首次登陆及许可是否到期
    				case 1:	IsAuthorized IA = new IsAuthorized();
    						flag = IA.isauthorized(mac);
    						DataOutputStream ps = new DataOutputStream(s.getOutputStream());
    						ps.writeInt(flag);
    						ps.flush();
    						s.close();
    						break;
    				//返回剩余天数
    				case 2:	IsAuthorized IA1 = new IsAuthorized();
    						days = IA1.getleftdays(mac);
    						if(days != -1){
    							DataOutputStream ps1 = new DataOutputStream(s.getOutputStream());
    							ps1.writeInt(days);
    							ps1.flush();
    							s.close();
    						}
    						break;
    				//购买
    				case 3:	int days = inputStreamReader.readInt();
    						int money = inputStreamReader.readInt();
    						String phonenumber = inputStreamReader.readUTF();
    						IsAuthorized IA2 = new IsAuthorized();
    						String publickey = IA2.buypro(money,days,mac,phonenumber);
    						DataOutputStream ps2 = new DataOutputStream(s.getOutputStream());
							ps2.writeUTF(publickey);
							ps2.flush();
							s.close();
							break;
					//验证
    				case 4: 
							String userkey = inputStreamReader.readUTF();
							IsAuthorized IA3 = new IsAuthorized();
							String hasvarified = IA3.verify(userkey,mac);
							DataOutputStream ps3 = new DataOutputStream(s.getOutputStream());
							ps3.writeUTF(hasvarified);
							ps3.flush();
							s.close();
							break;
					//续费
    				case 5:	int days1 = inputStreamReader.readInt();
    						int money1 = inputStreamReader.readInt();
							IsAuthorized IA4 = new IsAuthorized();
							String renewsuc = IA4.renew(money1, days1, mac);
							DataOutputStream ps4 = new DataOutputStream(s.getOutputStream());
							ps4.writeUTF(renewsuc);
							ps4.flush();
							s.close();
							break;
    				case 6:	IsAuthorized IA5 = new IsAuthorized();
							String phone = IA5.getuserphone(mac);
							if(phone == null)
								phone = "none";
							DataOutputStream ps5 = new DataOutputStream(s.getOutputStream());
							ps5.writeUTF(phone);
							ps5.flush();
							s.close();
							break;
    				   		
    			}    			
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "服务器启动失败！");
    	}
    }
    public static void main(String args[]){
    	new ServerReply().start();
    }
}
