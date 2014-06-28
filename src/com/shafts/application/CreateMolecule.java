/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shafts.application;

/**
 *
 * @author chin
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.jchempaint.action.SaveAsAction;

import com.shafts.utils.JCPPanel1;
import com.shafts.utils.JmolPanel1;

import javax.swing.JFrame;
import javax.swing.JOptionPane; 
public class CreateMolecule extends JFrame {

	private static final long serialVersionUID = -3761917396818675708L;

	public static void main(String[] args) {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(CreateMolecule.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		CreateMolecule createMolecule = new CreateMolecule();
		createMolecule.setVisible(true);
		createMolecule.startPaintingThread();
	}
	
	
	
	public CreateMolecule() {
		initialize();
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				splitPane.setDividerLocation(PROPORTIONAL_LOCATION);
			}
		});

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public final void initialize() {
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jcpPanel = new JCPPanel1();
		jmolPanel = new JmolPanel1();

		setTitle(APP_NAME);
//*****************************ÐÞ¸Ä¹ý2013.11.18******************************//
		/*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
				this.getGraphicsConfiguration());
		int width = (int) (PROPORTIONAL_SIZE * (screenSize.width
				- screenInsets.left - screenInsets.right));
		int height = (int) (PROPORTIONAL_SIZE * (screenSize.height
				- screenInsets.top - screenInsets.bottom));  */
		//setBounds(0, 0, width, height); // set the specified rectangle area of a
										// component
		setBounds(0, 0, 960, 600);  
		
		setLocationRelativeTo(getOwner()); // align center

		getContentPane().add(splitPane, BorderLayout.CENTER);


		splitPane.setOneTouchExpandable(true); // enable the folding function
		splitPane.setDividerSize(SEPERATOR_WIDTH); // in pixels

		splitPane.setLeftComponent(jcpPanel);
		splitPane.setRightComponent(jmolPanel);

		// pack();
		splitPane.setDividerLocation(PROPORTIONAL_LOCATION);
	}
	
	public static String getRenderingFileName() {      //2014.1.15 delete       
		return "shafts-file-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".mol";
	}
	
	public void startPaintingThread() {
		if (rThread == null) {
			rThread = new RenderThread();
			rThread.start();
		}
	}
	
	
   class RenderThread extends Thread {      
		
		@Override
		public synchronized void start() {
			super.start();
			lastmodified = 0L;			
			file = new File(CreateMolecule.RENDER_FILE_NAME);
		}

		@Override
		public void run() {
			while(true) {
				
				if (file.exists() && file.lastModified() > lastmodified) {
					jmolPanel.viewer.openFile(CreateMolecule.RENDER_FILE_NAME);
					String bandian="dots on";
	        	    jmolPanel.viewer.evalString(bandian);            //ÐÞ¸Ä 2014.1.2
					
					
					lastmodified = file.lastModified();
				}
				
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					// ignore the exception caused by the application's close
				}
			}
		}

		@Override
		public void interrupt() {
			super.interrupt();
			if (file.exists())  file.delete();
		}

		private final int SLEEP_TIME = 500; // in milliseconds
		
		private File file;
		private long lastmodified;
	}
	
    public final static String RENDER_FILE_NAME = getRenderingFileName();
	//String RENDER_FILE_NAME = getRenderingFileName();;
	public static final String APP_NAME = "CREATE A MOLECULE";
	public static final double PROPORTIONAL_LOCATION = 0.5;
	public static final double PROPORTIONAL_SIZE = 1.0;
	public static final int SEPERATOR_WIDTH = 10;
    
	private JSplitPane splitPane;
	private JCPPanel1 jcpPanel;
	private JmolPanel1 jmolPanel;
	private RenderThread rThread;
}


