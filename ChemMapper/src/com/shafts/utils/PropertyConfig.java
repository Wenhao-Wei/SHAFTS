package com.shafts.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
 
public class PropertyConfig extends Properties{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Properties prop;
	public void createProperties(String value1, String value2){
		//prop = new Properties();
		put("usedefault",value1);
		put("workpath", value2);	
	    try {
	    	//保存
		    FileOutputStream out = new FileOutputStream("userinfo.properties");
		    //为properties添加注释
			store(out, "This file saved the user workpath config.");
			out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	}
	public ArrayList<String> readProperties(){
		ArrayList<String> array = new ArrayList<String>();
		String usedefault = null;
		String workpath = null;
		 try {			    			    
			    //读取
			    FileInputStream in = new FileInputStream("userinfo.properties");
			    load(in);
			    usedefault = getProperty("usedefault");
			    workpath = getProperty("workpath");
			    array.add(usedefault);
			    array.add(workpath);
			    in.close();	
			    return array;
			   } catch (FileNotFoundException e) {
				   e.printStackTrace();
				   return null;
			   
			   } catch (IOException e) {
			    e.printStackTrace();
			    return null;
			   }
		 
	}
	public void updateProperties(String usedefault,String workpath){
		this.createProperties(usedefault, workpath);
		//setProperty("usedefault",usedefault);
		//setProperty("localworkpath",localworkpath);
		//setProperty("networkpath", networkpath);
		//store(out1, "Update '"+ KeyName +"'value");	    	  
  }
  public static void main(String args[]){
	  
	  PropertyConfig properties = new PropertyConfig();
	  properties.createProperties("NO","weiwenhao");
	  try {		    		    
		    //读取
		    FileInputStream in = new FileInputStream("userinfo.properties");
		    properties.load(in);
		    System.out.println(properties.getProperty("usedefault"));
		    System.out.println(properties.getProperty("workpath"));
		    properties.updateProperties("NO","fs");
		    in.close();
		    FileInputStream in2 = new FileInputStream("userinfo.properties");
		    properties.load(in2);
		    System.out.println("******************AfterUpdate******************");
		    System.out.println(properties.getProperty("usedefault"));
		    System.out.println(properties.getProperty("workpath"));
		    in2.close();	
		    
		   } catch (FileNotFoundException e) {
		    e.printStackTrace();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
  }
 }
