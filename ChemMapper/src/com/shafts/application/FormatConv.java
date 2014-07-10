package com.shafts.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.openbabel.OBConversion;
import org.openbabel.OBMol;

import com.shafts.utils.ShowGif;
import com.shafts.utils.WaitGif;


/**
 * Format conversion
 * @author Wenhao Wei
 * 2014-07-06
 */
public class FormatConv extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean convFlag = false;
	private File inMol;
	private String inMolName;
	private String inFormat;
	private ShowGif gif;
	/**
	 * convert
	 * @param inFilePath
	 * 			the file path of the input molecule
	 * @param outFilePath
	 * 			the file path of the output molecule
	 * @param outFormat
	 * 			the format of the output molecule
	 * @return
	 * 		  	return the flag if the conversion has succeeded
	 */
	public void formatconv(final String inFilePath,final String outFilePath,final String outFormat){
			//gifThread = new WaitGif();
			new Thread(){
				public void run(){
			gif = new ShowGif();
			}
			}.start();
			//System.out.println("*****************"+outFormat);
			new Thread(){
				public void run(){
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			convFlag = false;
			inMol = new File(inFilePath);
			inMolName = inMol.getName();
			inFormat = inMolName.substring(inMolName.lastIndexOf(".")+1);	
			// Read molecule from informat
			System.loadLibrary("openbabel_java");
			OBConversion conv = new OBConversion();
			OBMol mol = new OBMol(); 
			try{	       
				 conv.SetInFormat(inFormat);
				 conv.ReadFile(mol, inFilePath);	  
				 conv.SetOutFormat(outFormat);
				 conv.WriteFile(mol, outFilePath);
				 convFlag = true;
				 //JOptionPane.showMessageDialog( null,"Convert Success£¡");
				 }catch(Exception e){
				   	e.printStackTrace();
				   	int i = JOptionPane.showConfirmDialog( null,"Please install the Openbabel first£¡\n Install now?","Tips",JOptionPane.YES_NO_OPTION);
				   	if(i == JOptionPane.OK_OPTION){
				   		Desktop desktop = Desktop.getDesktop();  
				        try {
							desktop.browse(new URI("http://openbabel.org/wiki/Main_Page"));
								} catch (IOException e1) {
										e1.printStackTrace();
									} catch (URISyntaxException e1) {
										e1.printStackTrace();
									} 
				   			}				   				
				   		}	       	       
			        // conv.WriteString(mol);
					
				   	if(convFlag){
				   		gif.close();
				   		System.out.println(outFilePath);
    					int i = JOptionPane.showConfirmDialog(null, "Success! Would you wan to show it now?", "Tips", JOptionPane.YES_NO_OPTION);
    					if(i == JOptionPane.OK_OPTION)
							try {
								Runtime.getRuntime().exec("explorer.exe /select," + outFilePath);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				   	}
    				else{
    					gif.close();
    					JOptionPane.showMessageDialog( null,"Failed£¬please retry£¡");
    					
    				}
			}
			}.start();
				}
	public static void main(String args[]){
		
	}
}
