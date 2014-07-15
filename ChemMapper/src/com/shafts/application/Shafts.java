/*
 * the shafts
 */
package com.shafts.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import com.shafts.ui.InitVector;

/**
 * 
 * @author baoabo
 *
 */
public class Shafts{
	/**
	 * 
	 * @param NewPath
	 *        ��ҵ���·��
	 * @param inFilePath
	 *        input molecule
	 * @param DataBase
	 *        �û��ύ���ݿ�
	 * @param outputNum
	 *        �������
	 * @param threshold
	 *        ɸѡ��ֵ
	 */	
	public void shaftinit(String NewPath, String inFilePath, String DataBase, String outputNum, String threshold){

		 String cmd = "Cynthia.exe -q "+inFilePath+ " -t "+ DataBase +" -n "+outputNum+" -sCutoff "+threshold;
		 InputStream ins = null;
	 try {
		 Process process = Runtime.getRuntime().exec(cmd);
         ins = process.getInputStream(); //cmd ����Ϣ	             
         BufferedReader reader = new BufferedReader(new InputStreamReader(ins));   
         String line = null;   
         while ((line = reader.readLine()) != null) {   
                System.out.println(line);  //��� 
            } 	                
         int exitValue = process.waitFor();   
         System.out.println("����ֵ��" + exitValue);  
         process.getOutputStream().close(); //��Ҫ������һ��Ҫ��
	    
         
    } catch (Exception e) {            
        e.printStackTrace();
    }
	 try{
	    int i = 1;
	    String j = i + "";
	    workPath = NewPath + j;
        File F1 = new File(workPath);
	    while(F1.exists()){
	    	i++;
	    	j = i + "";
	    	workPath = NewPath + j;
	    	F1 = new File(workPath);
	    }
	  F1.mkdir();			    	            
      File F2 = new File("Result.list");
      FileInputStream  input  =  new  FileInputStream(F2);  
      FileOutputStream  output  =  new  FileOutputStream(workPath  +  "/" + F2.getName().toString());  
      byte[]  b  =  new  byte[1024  *  5];  
      int  len;  
      while  (  (len  =  input.read(b))  !=  -1)  {  
          output.write(b,  0,  len);  
      }  
      output.flush();  
      output.close();  
      input.close();
      F2.delete();
      LocalworkID = "Job"+ j;
	 }catch(IOException ioe){ioe.printStackTrace();}
   }
	/**
	 * @return
	 */
	public String getworkid(){
		return LocalworkID;
	}
	/**	 
	 * @return
	 */
	public Vector getdata(){
		String path = workPath + "\\Result.list";
		IV = new InitVector();
		workdata = IV.getdata(path);
		return workdata;
	}
	private String LocalworkID;
	private String workPath;
	private InitVector IV;
	private Vector workdata;
}