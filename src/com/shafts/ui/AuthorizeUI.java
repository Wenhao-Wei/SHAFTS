package com.shafts.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import com.shafts.utils.CheckUserStatus;

public class AuthorizeUI extends JDialog{
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 300;
    static final int HEIGHT = 150;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JButton ok;
    private JButton cancel;
    private JComboBox buyday;
    private JLabel title;
    private JLabel days;
    private JLabel money;
    private JLabel tip1;
    private JLabel tip2;
    private boolean buysuccess = false;
    private JLabel cost;
    private String choose;
    private int COST;
    private int chooseday;   
      @SuppressWarnings("static-access")
	public AuthorizeUI(){
    	  panel1 = new JPanel();
    	  panel2 = new JPanel(new GridLayout(2,3,10,0));
    	  panel3 = new JPanel();
    	  ok = new JButton("����");
    	  cancel = new JButton("ȡ��");
    	  this.setModal(true);
    	  Toolkit kit = Toolkit.getDefaultToolkit();
       	  Dimension screensize = kit.getScreenSize();
       	  int width = screensize.width;
       	  int height = screensize.height;
       	  int x = (width - WIDTH)/2;
       	  int y = (height - HEIGHT)/2;
       	  this.setLocation(x-50, y-100);
    	  this.setName("����");
    	  this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    	  this.setSize(WIDTH, HEIGHT);
       	  title = new JLabel("���ã���ӭ����");
       	  days = new JLabel("����������");
       	  money = new JLabel("���ѽ�");
       	  cost = new JLabel();
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
       	  panel1.add(title, BorderLayout.LINE_START);
       	  panel2.add(days);
       	  panel2.add(buyday);
       	  panel2.add(tip1);
       	  panel2.add(money);
       	  panel2.add(cost);
       	  panel2.add(tip2);
       	  panel3.add(cancel,BorderLayout.WEST);
       	  panel3.add(ok, BorderLayout.CENTER);      	  
    	  this.add(panel1, BorderLayout.NORTH);
    	  this.add(panel2,BorderLayout.CENTER);
    	  this.add(panel3,BorderLayout.SOUTH);
    	  ok.addActionListener(new ActionListener() {                         
           	public void actionPerformed(ActionEvent e) {
           		if(chooseday == 0)
           			JOptionPane.showMessageDialog( null,"��ѡ����������");
           		else{
           			CheckUserStatus CUS = new CheckUserStatus();
           			try {
  						int hasbought = CUS.buypro(chooseday, COST);
  						if(hasbought == 1){
  							JOptionPane.showMessageDialog( null,"����ɹ������˴�����"+COST+"Ԫ��������������Ϊ"+chooseday+"�죡\n"+"             �뵥��ȷ������ϵͳ��");
  							buysuccess = true;
  							Jclose();
  						}
  						else {
  							JOptionPane.showMessageDialog( null,"����ʧ�ܣ�");
  							Jclose();
  						}
  					} catch (Exception e1) {
  						// TODO Auto-generated catch block
  						JOptionPane.showMessageDialog( null,"�쳣,������");
  						Jclose();
  						e1.printStackTrace();
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
     	 this.setVisible(true);
   	}
      /**
  	 * @return
  	 */
  	public boolean getsuccess(){
  		return buysuccess;
  	}
     public void Jclose(){
    	 this.dispose();
     }
      public static void main(String args[]){
    	  new AuthorizeUI();
    	  
      }
}
