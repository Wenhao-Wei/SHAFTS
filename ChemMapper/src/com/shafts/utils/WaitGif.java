package com.shafts.utils;
/*
 * Donttai.java
 *
 * Created on __DATE__, __TIME__
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * create a loading animating picture
 * @author  Wenhao Wei
 */
public class WaitGif extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;  //="E:\\Animation\\012_p";
	private final int WIDTH = 200;
	private final int HEIGHT = 50;
	private int idx = 1;
	private JLabel label;
	private JPanel panel;
	private JFrame frame;
    public WaitGif()
    {	
    	//frame = new JFrame();
    	path = System.getProperty("user.dir") + "\\Pictures\\004.gif";
    	Toolkit kit = Toolkit.getDefaultToolkit();
     	Dimension screensize = kit.getScreenSize();
     	int width = screensize.width;
     	int height = screensize.height;
     	int x = (width - WIDTH)/2;
     	int y = (height - HEIGHT)/2;
     	setLocation(x, y);
     	setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
     	setSize(WIDTH, HEIGHT);
        //com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.1f);
    	//setResizable(false);
     	setUndecorated(true);
    	//setEnabled(false);
        Container  cp  = getContentPane();
        Color c = new Color(0.0f,0.0f,0.0f,0.5f);
        setBackground(c);
        //Container cp = this.getContentPane();
        //((JPanel)cp).setOpaque(true);
    	//getContentPane().setLayout(null);
    	label = new JLabel("Converting!");
    	panel = new ImagePanel();
    	panel.setBounds(10, 30, 120, 21);
    	add(label,BorderLayout.NORTH);
    	add(panel,BorderLayout.CENTER);
    	setVisible(true);
    	((ImagePanel)panel).run();
    }
  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
       WaitGif wa = new WaitGif();       
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wa.closeFrame();               
    }
    class ImagePanel extends JPanel implements Runnable{
    	public void paint(Graphics g)
        {
            ImageObserver imageObserver = new ImageObserver()
            {

                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y,
                        int width, int height)
                {
                    // TODO Auto-generated method stub
                    return false;
                }
            };
            try
            {
                String temp = "";
                if (idx <= 4)
                    temp = path + idx + ".jpg";
                g.drawImage(ImageIO.read(new File(temp)), 4, 8, 190, 14,
                        imageObserver);
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }    	    	
    	   @Override
    	    public void run()
    	    {
    	        // TODO Auto-generated method stub
    	        while (true)
    	        {
    	            repaint();
    	            this.updateUI();
    	            if (idx < 4)
    	                idx++;
    	            else
    	                idx = 1;
    	            try
    	            {
    	                Thread.sleep(100);
    	            }
    	            catch (InterruptedException e)
    	            {
    	                // TODO Auto-generated catch block
    	                e.printStackTrace();
    	            }
    	        }
    	    }
    	 }
    public void closeFrame(){
    	//((ImagePanel)panel).interrupt;
    	dispose();
    }
}
