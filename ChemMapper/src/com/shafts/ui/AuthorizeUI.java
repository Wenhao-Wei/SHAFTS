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
    	  label = new JLabel("请输入授权码：");
    	  textfield = new JTextField();
    	  JSeparator separator = new JSeparator();
    	  separator.setBackground(Color.green);
    	  separator.setSize(500, 5);
    	  activepanel.add(label);
    	  activepanel.add(textfield);
    	  activepanel.add(separator);
    	  ok = new JButton("下一步");
    	  cancel = new JButton("取消");
    	  prestep = new JButton("上一步");
    	  purchase = new JRadioButton("购买");
    	  active = new JRadioButton("激活");
    	  renew = new JRadioButton("续费");
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
       	  dialog.setName("授权");
       	  dialog.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
       	  dialog.setSize(WIDTH, HEIGHT);
    	  String str = "  本产品网络数据库相关功能需付费使用，为了不影响您的正常使用，请先激活本软件。如果您是首次使用，请选择“购买”选项以获取授权码，如果您已购买，请选择“激活”选项进行验证。\n  如遇问题，请致电：*******\n\n\n  邮箱：784887302@qq.com";
       	  editor = new JEditorPane("text/plain",str);
       	  editor.setEditable(false);
    	  title = new JLabel("使用许可协议");
       	  days = new JLabel("购买天数：");
       	  money = new JLabel("付费金额：");
       	  //cost = new JLabel();
       	  cost = new JTextField();
       	  cost.setEditable(false);
       	  buyday = new JComboBox();
       	  buyday.setToolTipText("请选择购买天数");
       	  buyday.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"请选择购买天数...","90", "180", "270", "360","450","540","630","720"}));
    		 buyday.addItemListener(new ItemListener(){
    			 public void itemStateChanged(ItemEvent e){         		 
                	if(e.getStateChange() == ItemEvent.SELECTED){
                		if(!buyday.getSelectedItem().equals("请选择购买天数...")){
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
    		 
       	  tip1 = new JLabel("(单位：天)");
       	  tip2 = new JLabel("(单位：元)");
       	  tel  = new JLabel("联系方式：");
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
       	  days = new JLabel("购买天数：");
     	  money = new JLabel("付费金额：");
     	  //cost = new JLabel();
     	  cost1 = new JTextField();
     	  cost1.setEditable(false);
     	  buyday1 = new JComboBox();
     	  buyday1.setToolTipText("请选择续期");
     	  buyday1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"请选择续期...","90", "180", "270", "360","450","540","630","720"}));
  		  buyday1.addItemListener(new ItemListener(){
  			 public void itemStateChanged(ItemEvent e){         		 
              	if(e.getStateChange() == ItemEvent.SELECTED){
              		if(!buyday1.getSelectedItem().equals("请选择续期...")){
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
  		 
     	  tip1 = new JLabel("(单位：天)");
     	  tip2 = new JLabel("(单位：元)");
       	  consumepanel.add(days);
       	  consumepanel.add(buyday1);
       	  consumepanel.add(tip1);
       	  consumepanel.add(money);
     	  consumepanel.add(cost1);
     	  consumepanel.add(tip2);
       	  //editorpanel.add(editor,BorderLayout.CENTER);
     	  title1 = new JLabel("尊敬的 "+encrypt+"用户, 请选择续费使用天数：");
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
           		ok.setText("下一步");
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
           		case"购买":	if(chooseday == 0)
           						JOptionPane.showMessageDialog( null,"请选择购买天数！");
           					else {
           							String phone = phonenumber.getText();
           							if(phone.trim().equals(""))
           								JOptionPane.showMessageDialog( null,"请填写您个人联系方式！");
           							else {
           									CheckUserStatus CUS = new CheckUserStatus();
           									try {
           										String userkey = CUS.buypro(chooseday, COST,phone);
           										File f = new File("USERKEY.TXT");
           										BufferedWriter output = new BufferedWriter(new FileWriter(f));
           										output.write(userkey);
           										output.close();
           										if (userkey.equals("error"))
           											JOptionPane.showMessageDialog( null,"购买失败，服务器问题，请稍后再试！");	
           										else if(userkey.equals("hasbought")){          												
           											JOptionPane.showMessageDialog( null,"您已经购买过，无需再购买，您可以通过续费方式延长使用期限");           											
           												}
           											else {           												
           												int i = JOptionPane.showConfirmDialog( null,"付费成功！您此次消费"+COST+"元整，授权码为\n"+userkey+"\n是否立即验证？","提示",JOptionPane.YES_NO_OPTION);
           												if(i == JOptionPane.OK_OPTION){
           													ok.setText("激活");
           													//southpanel.remove(radiopanel);
           													southpanel.updateUI();
           													dialog.remove(buypanel);
           													//editorpanel.updateUI();
           													dialog.add(activepanel);
           													textfield.setText(userkey);
           													}
           												else{
           													JOptionPane.showMessageDialog( null,"验证码已保存在安装目录下的USERKEY文件中！");	
           													Jclose();
           													}
           												//Jclose();
           												}
           										} catch (Exception e1) {
           													JOptionPane.showMessageDialog( null,"异常,请重试");
           													//Jclose();
           													e1.printStackTrace();
           													}	
           									}
           					}
           					break;           		           					
           		case"下一步":if(purchase.isSelected()){
           						update = 1;
           						ok.setText("购买");          						
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
           						ok.setText("激活");
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
           							JOptionPane.showMessageDialog( null,"检测到您非购买用户，请选择够买！");           						
           						else{
           							update = 3;
           							String preStr = phone.substring(0,3);
           							String subStr = phone.substring(7);
           							encrypt = preStr+"****"+subStr;
           							ok.setText("确定");
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
           						JOptionPane.showMessageDialog( null,"请选择本步骤操作！");
           					break;
           		case"激活":	String key = textfield.getText();
           					if(key.trim().equals(""))
           						JOptionPane.showMessageDialog( null,"请输入激活码！");
           					else{
           						CheckUserStatus CUS = new CheckUserStatus();
           						try {
									String hasverified = CUS.verify(key);
									System.out.println("*************************"+hasverified);
									if(hasverified.equals("verifiedsuccess")){
										verifysuccess = true;
										JOptionPane.showMessageDialog( null,"激活成功！\n"+"             请单击确定重启系统！");
										Jclose();
									}
									else if(hasverified.equals("hasverified"))
										JOptionPane.showMessageDialog( null,"您已通过验证，无需重复激活！");
									else if(hasverified.equals("notmatch"))
										JOptionPane.showMessageDialog( null,"授权码不匹配，请输入正确的授权码");
									else
										JOptionPane.showMessageDialog( null,"未知错误！请重试。");
								} catch (Exception e1) {
									JOptionPane.showMessageDialog( null,"激活失败！请重试。");
									e1.printStackTrace();
								}
           					}
           					break;
           		case"确定": 	if(chooseday1 == 0)
							JOptionPane.showMessageDialog( null,"请选择续费天数！");
           					else {           							
           								CheckUserStatus CUS = new CheckUserStatus();
           								try {
           									String renewsuccess = CUS.renew(COST, chooseday);
           									if(renewsuccess.equals("success")){
												JOptionPane.showMessageDialog( null,"续费成功！您此次消费"+COST+"元整.");          										
												Jclose();
           										}
           									else if(renewsuccess.equals("newuser")){
           										JOptionPane.showMessageDialog( null,"新用户！请先购买");
           									}
           								} catch (Exception e1) {
												JOptionPane.showMessageDialog( null,"异常,请重试");
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
