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
    	  purchase = new JRadioButton("����");
    	  active = new JRadioButton("����");
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
           		case"����":	if(chooseday == 0)
           						JOptionPane.showMessageDialog( null,"��ѡ����������");
           					else {
           							String phone = phonenumber.getText();
           							System.out.println(phone);
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
           										if(userkey != null){
           											//JOptionPane.showMessageDialog( null,"����ɹ������˴�����"+COST+"Ԫ��������������Ϊ"+chooseday+"�죡\n"+"             �뵥��ȷ������ϵͳ��");
           											int i = JOptionPane.showConfirmDialog( null,"���ѳɹ������˴�����"+COST+"Ԫ������Ȩ��Ϊ\n"+userkey+"\n�Ƿ�������֤��");
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
           											//buysuccess = true;
           											
           										}
           										else {
           												JOptionPane.showMessageDialog( null,"������...");
           												Jclose();
           												}
           										} catch (Exception e1) {
           													JOptionPane.showMessageDialog( null,"�쳣,������");
           													Jclose();
           													e1.printStackTrace();
           													}	
           									}
           					}
           					break;
           		case"��һ��":if(purchase.isSelected()){
           						ok.setText("����");          						
           						southpanel.remove(radiopanel);
           						southpanel.updateUI();
           						dialog.remove(editor);           						
           						dialog.add(buypanel);
           						//dialog.
           					}
           					else if(active.isSelected()){
           						ok.setText("����");
           						southpanel.remove(radiopanel);
           						southpanel.updateUI();
           						dialog.remove(editor);
           						//editorpanel.updateUI();
           						dialog.add(activepanel);
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
									boolean hasverified = CUS.verify(key);
									if(hasverified){
										verifysuccess = true;
										JOptionPane.showMessageDialog( null,"����ɹ���\n"+"             �뵥��ȷ������ϵͳ��");
										Jclose();
									}
								} catch (Exception e1) {
									JOptionPane.showMessageDialog( null,"����ʧ�ܣ������ԡ�");
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
