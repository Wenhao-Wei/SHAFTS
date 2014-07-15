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
	 *        作业存放路径
	 * @param inFilePath
	 *        input molecule
	 * @param DataBase
	 *        用户提交数据库
	 * @param outputNum
	 *        输出个数
	 * @param threshold
	 *        筛选阈值
	 */	
	public void shaftinit(String NewPath, String inFilePath, String DataBase, String outputNum, String threshold){

		 String cmd = "Cynthia.exe -q "+inFilePath+ " -t "+ DataBase +" -n "+outputNum+" -sCutoff "+threshold;
		 InputStream ins = null;
	 try {
		 Process process = Runtime.getRuntime().exec(cmd);
         ins = process.getInputStream(); //cmd 的信息	             
         BufferedReader reader = new BufferedReader(new InputStreamReader(ins));   
         String line = null;   
         while ((line = reader.readLine()) != null) {   
                System.out.println(line);  //输出 
            } 	                
         int exitValue = process.waitFor();   
         System.out.println("返回值：" + exitValue);  
         process.getOutputStream().close(); //不要忘记了一定要关
	    
         
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