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
    private JEditorPane editor;
    private JRadioButton purchase;
    private JRadioButton active;
    private JLabel label;
    private JTextField textfield;
    private JTextField phonenumber;
    private JTextField cost;
    private JDialog dialog;
    //private JButton next;
    private JButton ok;
    private JButton cancel;
    private JComboBox buyday;
    private JLabel tel;
    private JLabel title;
    private JLabel days;
    private JLabel money;
    private JLabel tip1;
    private JLabel tip2;
    private boolean verifysuccess = false;
    //private JLabel cost;
    private String choose;
    private String publickey;
    private int COST;
    private int chooseday;   
      @SuppressWarnings("static-access")
    public void setJdialog(JDialog jd){
    	  this.dialog = jd;
      }
	public AuthorizeUI(){
		  dialog = new JDialog();
    	  panel1 = new JPanel();
    	  panel2 = new JPanel(new GridLayout(3,3,10,10));
    	  panel3 = new JPanel();
    	  buypanel = new JPanel();
    	  activepanel = new JPanel(new GridLayout(3,1,20,50));
    	  radiopanel = new JPanel();
    	  southpanel = new JPanel();
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
    	  purchase = new JRadioButton("购买");
    	  active = new JRadioButton("激活");
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
       	  panel3.add(ok, BorderLayout.CENTER);
       	  buypanel.add(panel2,BorderLayout.CENTER);
       	  //buypanel.add(panel3,BorderLayout.SOUTH);
       	  southpanel.add(radiopanel, BorderLayout.CENTER);
       	  southpanel.add(panel3, BorderLayout.SOUTH);
       	  //editorpanel.add(editor,BorderLayout.CENTER);
       	  dialog.add(panel1, BorderLayout.NORTH);
       	  dialog.add(editor,BorderLayout.CENTER);
       	  dialog.add(southpanel,BorderLayout.SOUTH);
    	  ok.addActionListener(new ActionListener() {                         
           	public void actionPerformed(ActionEvent e) {
           		String op = ok.getText();
           		switch(op){
           		case"购买":	if(chooseday == 0)
           						JOptionPane.showMessageDialog( null,"请选择购买天数！");
           					else {
           							String phone = phonenumber.getText();
           							System.out.println(phone);
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
           										if(userkey != null){
           											//JOptionPane.showMessageDialog( null,"购买成功！您此次消费"+COST+"元整，可以用天数为"+chooseday+"天！\n"+"             请单击确定重启系统！");
           											int i = JOptionPane.showConfirmDialog( null,"付费成功！您此次消费"+COST+"元整，授权码为\n"+userkey+"\n是否立即验证？");
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
           											//buysuccess = true;
           											
           										}
           										else {
           												JOptionPane.showMessageDialog( null,"待处理...");
           												Jclose();
           												}
           										} catch (Exception e1) {
           													JOptionPane.showMessageDialog( null,"异常,请重试");
           													Jclose();
           													e1.printStackTrace();
           													}	
           									}
           					}
           					break;
           		case"下一步":if(purchase.isSelected()){
           						ok.setText("购买");          						
           						southpanel.remove(radiopanel);
           						southpanel.updateUI();
           						dialog.remove(editor);           						
           						dialog.add(buypanel);
           						//dialog.
           					}
           					else if(active.isSelected()){
           						ok.setText("激活");
           						southpanel.remove(radiopanel);
           						southpanel.updateUI();
           						dialog.remove(editor);
           						//editorpanel.updateUI();
           						dialog.add(activepanel);
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
									boolean hasverified = CUS.verify(key);
									if(hasverified){
										verifysuccess = true;
										JOptionPane.showMessageDialog( null,"激活成功！\n"+"             请单击确定重启系统！");
										Jclose();
									}
								} catch (Exception e1) {
									JOptionPane.showMessageDialog( null,"激活失败！请重试。");
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
