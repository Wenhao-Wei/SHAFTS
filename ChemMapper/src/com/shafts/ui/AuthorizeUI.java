package com.shafts.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.*;

import com.shafts.utils.CheckUserStatus;

public class AuthorizeUI extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	static final int WIDTH = 500;
    static final int HEIGHT = 300;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel radiopanel;
    private JPanel buypanel;
    private JPanel activepanel;
    private JPanel southpanel;
    private JPanel renewpanel;
    private JPanel consumepanel;
    private JEditorPane editor;
    private JRadioButton purchase;
    private JRadioButton active;
    private JRadioButton renew;
    private JLabel label;
    private JTextField textfield;
    private JTextField phonenumber;
    private JTextField cost;
    private JTextField cost1;
    private JDialog dialog;
    private JButton prestep;
    private JButton ok;
    private JButton cancel;
    private JComboBox buyday;
    private JComboBox buyday1;
    private JLabel tel;
    private JLabel title;
    private JLabel days;
    private JLabel money;
    private JLabel tip1;
    private JLabel tip2;
    private JLabel title1;
    private boolean verifysuccess = false;
    //private JLabel cost;
    private String choose;
    private String publickey;
    private String phone = null;
    private String encrypt = null;
    private int COST;
    private int chooseday;
    private int chooseday1;
    private int update = 0;
      @SuppressWarnings("static-access")
   
	public AuthorizeUI(){
    	  super();
		  dialog = new JDialog();
		  dialog.setResizable(false);
    	  panel1 = new JPanel();
    	  panel2 = new JPanel(new GridLayout(3,3,10,10));
    	  panel3 = new JPanel();
    	  buypanel = new JPanel();
    	  activepanel = new JPanel(new GridLayout(3,1,20,50));
    	  radiopanel = new JPanel();
    	  southpanel = new JPanel();
    	  renewpanel = new JPanel(new GridLayout(3,1,20,10));
    	  consumepanel = new JPanel(new GridLayout(2,3,10,10));
    	  label = new JLabel("��������Ȩ�룺");
    	  textfield = new JTextField();
    	  JSeparator separator = new JSeparator();
    	  separator.setBackground(Color.green);
    	  separator.setSize(500, 5);
    	  activepanel.add(label);
    	  activepanel.add(textfield);
    	  activepanel.add(separator);
    	  ok = new JButton("��һ��");
    	  cancel = new JButton("ȡ��");
    	  prestep = new JButton("��һ��");
    	  purchase = new JRadioButton("����");
    	  active = new JRadioButton("����");
    	  renew = new JRadioButton("����");
    	  ButtonGroup bg = new ButtonGroup();
    	  bg.add(renew);
    	  bg.add(purchase);
    	  bg.add(active);
    	  radiopanel.add(renew);
    	  radiopanel.add(purchase);
    	  radiopanel.add(active);
    	  dialog.setModal(true);
    	  Toolkit kit = Toolkit.getDefaultToolkit();
       	  Dimension screensize = kit.getScreenSize();
       	  int width = screensize.width;
       	  int height = screensize.height;
       	  int x = (width - WIDTH)/2;
       	  int y = (height - HEIGHT)/2;
       	  dialog.setLocation(x-50, y-100);
       	  dialog.setName("��Ȩ");
       	  dialog.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
       	  dialog.setSize(WIDTH, HEIGHT);
    	  String str = "  ����Ʒ�������ݿ���ع����踶��ʹ�ã�Ϊ�˲�Ӱ����������ʹ�ã����ȼ���������������״�ʹ�ã���ѡ�񡰹���ѡ���Ի�ȡ��Ȩ�룬������ѹ�����ѡ�񡰼��ѡ�������֤��\n  �������⣬���µ磺*******\n\n\n  ���䣺784887302@qq.com";
       	  editor = new JEditorPane("text/plain",str);
       	  editor.setEditable(false);
    	  title = new JLabel("ʹ�����Э��");
       	  days = new JLabel("����������");
       	  money = new JLabel("���ѽ�");
       	  //cost = new JLabel();
       	  cost = new JTextField();
       	  cost.setEditable(false);
       	  buyday = new JComboBox();
       	  buyday.setToolTipText("��ѡ��������");
       	  buyday.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"��ѡ��������...","90", "180", "270", "360","450","540","630","720"}));
    		 buyday.addItemListener(new ItemListener(){
    			 public void itemStateChanged(ItemEvent e){         		 
                	if(e.getStateChange() == ItemEvent.SELECTED){
                		if(!buyday.getSelectedItem().equals("��ѡ��������...")){
                			choose = (String)buyday.getSelectedItem();
                		       chooseday = Integer.parseInt(choose);
                		      switch(chooseday){
                				case 90: cost.setText("45");
                					     COST = 45;
                					     break;
                				case 180: cost.setText("90");
                			     		  COST = 90;
                				case 270: cost.setText("135");
                			     		  COST = 135;
                			     		  break;
                				case 360: cost.setText("180");
                			              COST = 180;
                			              break;
                				case 450: cost.setText("225");
                			     	      COST = 225;
                			     	      break;
                				case 540: cost.setText("270");
                			     		  COST = 270;
                			     		  break;
                				case 630: cost.setText("315");
                			     		  COST = 315;
                			     		  break;
                				case 720: cost.setText("360");
                						  COST = 360;
                						  break;
                				default:  
                							
                						  break;		
                			}
                		       }
                		else {
                			chooseday = 0;
                			cost.setText("");
                		}
                	  } 
                }
           }); 
    		 
       	  tip1 = new JLabel("(��λ����)");
       	  tip2 = new JLabel("(��λ��Ԫ)");
       	  tel  = new JLabel("��ϵ��ʽ��");
       	  phonenumber = new JTextField();
       	  panel1.add(title, BorderLayout.LINE_START);
       	  panel2.add(days);
       	  panel2.add(buyday);
       	  panel2.add(tip1);
       	  panel2.add(money);
       	  panel2.add(cost);
       	  panel2.add(tip2);
       	  panel2.add(tel);
       	  panel2.add(phonenumber);
       	  panel3.add(cancel,BorderLayout.WEST);
       	  panel3.add(ok, BorderLayout.EAST);
       	  buypanel.add(panel2,BorderLayout.CENTER);
       	  //buypanel.add(panel3,BorderLayout.SOUTH);
       	  southpanel.add(radiopanel);
       	  southpanel.add(panel3);
       	  days = new JLabel("����������");
     	  money = new JLabel("���ѽ�");
     	  //cost = new JLabel();
     	  cost1 = new JTextField();
     	  cost1.setEditable(false);
     	  buyday1 = new JComboBox();
     	  buyday1.setToolTipText("��ѡ������");
     	  buyday1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"��ѡ������...","90", "180", "270", "360","450","540","630","720"}));
  		  buyday1.addItemListener(new ItemListener(){
  			 public void itemStateChanged(ItemEvent e){         		 
              	if(e.getStateChange() == ItemEvent.SELECTED){
              		if(!buyday1.getSelectedItem().equals("��ѡ������...")){
              			choose = (String)buyday1.getSelectedItem();
              		       chooseday1 = Integer.parseInt(choose);
              		      switch(chooseday){
              				case 90: cost1.setText("45");
              					     COST = 45;
              					     break;
              				case 180: cost1.setText("90");
              			     		  COST = 90;
              				case 270: cost1.setText("135");
              			     		  COST = 135;
              			     		  break;
              				case 360: cost1.setText("180");
              			              COST = 180;
              			              break;
              				case 450: cost1.setText("225");
              			     	      COST = 225;
              			     	      break;
              				case 540: cost1.setText("270");
              			     		  COST = 270;
              			     		  break;
              				case 630: cost1.setText("315");
              			     		  COST = 315;
              			     		  break;
              				case 720: cost1.setText("360");
              						  COST = 360;
              						  break;
              				default:  
              							
              						  break;		
              			}
              		       }
              		else {
              			chooseday1 = 0;
              			cost1.setText("");
              		}
              	  } 
              }
         }); 
  		 
     	  tip1 = new JLabel("(��λ����)");
     	  tip2 = new JLabel("(��λ��Ԫ)");
       	  consumepanel.add(days);
       	  consumepanel.add(buyday1);
       	  consumepanel.add(tip1);
       	  consumepanel.add(money);
     	  consumepanel.add(cost1);
     	  consumepanel.add(tip2);
       	  //editorpanel.add(editor,BorderLayout.CENTER);
     	  title1 = new JLabel("�𾴵� "+encrypt+"�û�, ��ѡ������ʹ��������");
     	  renewpanel.add(title1);
     	  renewpanel.add(consumepanel);
       	  dialog.add(panel1, BorderLayout.NORTH);
       	  dialog.add(editor,BorderLayout.CENTER);
       	  dialog.add(southpanel,BorderLayout.SOUTH);
       	  prestep.addActionListener(new ActionListener() {                         
           	public void actionPerformed(ActionEvent e) {
           		if(update == 1)
           			dialog.remove(buypanel);
           		else if(update ==2)
           			dialog.remove(activepanel);
           		else if(update == 3)
           			dialog.remove(renewpanel);
           		ok.setText("��һ��");
           		southpanel.remove(panel3);
           		southpanel.updateUI();
           		southpanel.add(radiopanel);
           		panel3.remove(prestep);
           		southpanel.add(panel3);
           		dialog.add(editor);
           		editor.updateUI();
           	}
           	});
    	  ok.addActionListener(new ActionListener() {                         
           	public void actionPerformed(ActionEvent e) {
           		String op = ok.getText();
           		switch(op){
           		case"����":	if(chooseday == 0)
           						JOptionPane.showMessageDialog( null,"��ѡ����������");
           					else {
           							String phone = phonenumber.getText();
           							if(phone.trim().equals(""))
           								JOptionPane.showMessageDialog( null,"����д��������ϵ��ʽ��");
           							else {
           									CheckUserStatus CUS = new CheckUserStatus();
           									try {
           										String userkey = CUS.buypro(chooseday, COST,phone);
           										File f = new File("USERKEY.TXT");
           										BufferedWriter output = new BufferedWriter(new FileWriter(f));
           										output.write(userkey);
           										output.close();
           										if (userkey.equals("error"))
           											JOptionPane.showMessageDialog( null,"����ʧ�ܣ����������⣬���Ժ����ԣ�");	
           										else if(userkey.equals("hasbought")){          												
           											JOptionPane.showMessageDialog( null,"���Ѿ�������������ٹ���������ͨ�����ѷ�ʽ�ӳ�ʹ������");           											
           												}
           											else {           												
           												int i = JOptionPane.showConfirmDialog( null,"���ѳɹ������˴�����"+COST+"Ԫ������Ȩ��Ϊ\n"+userkey+"\n�Ƿ�������֤��","��ʾ",JOptionPane.YES_NO_OPTION);
           												if(i == JOptionPane.OK_OPTION){
           													ok.setText("����");
           													//southpanel.remove(radiopanel);
           													southpanel.updateUI();
           													dialog.remove(buypanel);
           													//editorpanel.updateUI();
           													dialog.add(activepanel);
           													textfield.setText(userkey);
           													}
           												else{
           													JOptionPane.showMessageDialog( null,"��֤���ѱ����ڰ�װĿ¼�µ�USERKEY�ļ��У�");	
           													Jclose();
           													}
           												//Jclose();
           												}
           										} catch (Exception e1) {
           													JOptionPane.showMessageDialog( null,"�쳣,������");
           													//Jclose();
           													e1.printStackTrace();
           													}	
           									}
           					}
           					break;           		           					
           		case"��һ��":if(purchase.isSelected()){
           						update = 1;
           						ok.setText("����");          						
           						southpanel.remove(radiopanel);
           						panel3.add(prestep, BorderLayout.CENTER);
           						panel3.updateUI();
           						southpanel.add(panel3);
           						southpanel.updateUI();
           						dialog.remove(editor);
           						buypanel.updateUI();
           						dialog.add(buypanel);
           						buypanel.updateUI();
           						//dialog.
           					}
           					else if(active.isSelected()){
           						update = 2;
           						ok.setText("����");
           						southpanel.remove(radiopanel);
           						panel3.add(prestep, BorderLayout.CENTER);
           						panel3.updateUI();
           						southpanel.updateUI();
           						dialog.remove(editor);
           						//editorpanel.updateUI();
           						dialog.add(activepanel);
           						active.updateUI();
           					}
           					else if(renew.isSelected()){
           						CheckUserStatus CUS = new CheckUserStatus();
           						phone = CUS.getuserphone();          						
           						if(phone.equals("none"))
           							JOptionPane.showMessageDialog( null,"��⵽���ǹ����û�����ѡ����");           						
           						else{
           							update = 3;
           							String preStr = phone.substring(0,3);
           							String subStr = phone.substring(7);
           							encrypt = preStr+"****"+subStr;
           							ok.setText("ȷ��");
           							southpanel.remove(radiopanel);
           							panel3.add(prestep, BorderLayout.CENTER);
           							southpanel.updateUI();
           							dialog.remove(editor);
           							//editorpanel.updateUI();           							
           							dialog.add(renewpanel);
           							renewpanel.updateUI();
           						}
           					}
           					else
           						JOptionPane.showMessageDialog( null,"��ѡ�񱾲��������");
           					break;
           		case"����":	String key = textfield.getText();
           					if(key.trim().equals(""))
           						JOptionPane.showMessageDialog( null,"�����뼤���룡");
           					else{
           						CheckUserStatus CUS = new CheckUserStatus();
           						try {
									String hasverified = CUS.verify(key);
									System.out.println("*************************"+hasverified);
									if(hasverified.equals("verifiedsuccess")){
										verifysuccess = true;
										JOptionPane.showMessageDialog( null,"����ɹ���\n"+"             �뵥��ȷ������ϵͳ��");
										Jclose();
									}
									else if(hasverified.equals("hasverified"))
										JOptionPane.showMessageDialog( null,"����ͨ����֤�������ظ����");
									else if(hasverified.equals("notmatch"))
										JOptionPane.showMessageDialog( null,"��Ȩ�벻ƥ�䣬��������ȷ����Ȩ��");
									else
										JOptionPane.showMessageDialog( null,"δ֪���������ԡ�");
								} catch (Exception e1) {
									JOptionPane.showMessageDialog( null,"����ʧ�ܣ������ԡ�");
									e1.printStackTrace();
								}
           					}
           					break;
           		case"ȷ��": 	if(chooseday1 == 0)
							JOptionPane.showMessageDialog( null,"��ѡ������������");
           					else {           							
           								CheckUserStatus CUS = new CheckUserStatus();
           								try {
           									String renewsuccess = CUS.renew(COST, chooseday);
           									if(renewsuccess.equals("success")){
												JOptionPane.showMessageDialog( null,"���ѳɹ������˴�����"+COST+"Ԫ��.");          										
												Jclose();
           										}
           									else if(renewsuccess.equals("newuser")){
           										JOptionPane.showMessageDialog( null,"���û������ȹ���");
           									}
           								} catch (Exception e1) {
												JOptionPane.showMessageDialog( null,"�쳣,������");
												Jclose();
												e1.printStackTrace();
												}	
           							} 
           				}
           			}
           	});
     	  	cancel.addActionListener(new ActionListener() {                          
             	public void actionPerformed(ActionEvent e) {
             		Jclose();
             	}
     	  	}); 
    	  //return buysuccess;
     	 dialog.setVisible(true);
   	}
      /**
  	 * @return
  	 */
  	public boolean getsuccess(){
  		return verifysuccess;
  	}
     public void Jclose(){
    	 dialog.dispose();
     }
      public static void main(String args[]){
    	  new AuthorizeUI();
    	  
      }
}
