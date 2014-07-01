package com.shafts.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class getWindowsMACAddress{ 
	/**
	 * 获取Mac地址
	 */
	String mac = null; 
	public String getAddress(){
         
    BufferedReader bufferedReader = null;      
    Process process = null;      
    try {      
          /**   
           * windows下的命令，显示信息中包含有mac地址信息     
           */   
        process = Runtime.getRuntime().exec("ipconfig /all");    
        bufferedReader = new BufferedReader(new InputStreamReader(process      
                .getInputStream()));      
        String line = null;      
        int index = -1;      
        while ((line = bufferedReader.readLine()) != null) {      
               /**   
                *  寻找标示字符串[physical address]    
                */   
            index = line.toLowerCase().indexOf("物理地址");     
            if (index != -1) {    
                index = line.indexOf(":");    
                if (index != -1) {    
                       /**   
                        *   取出mac地址并去除2边空格   
                        */   
                   mac = line.substring(index + 1).trim();     
               }    
                break;      
            }    
        }    
    } catch (IOException e) {      
        e.printStackTrace();      
    }finally {      
        try {      
            if (bufferedReader != null) {      
                bufferedReader.close();      
              }      
        }catch (IOException e1) {      
            e1.printStackTrace();      
          }      
        bufferedReader = null;      
        process = null;      
    }      
  
    return mac;      
	}
	public static void main(String args[]){
		String a = new getWindowsMACAddress().getAddress();
		System.out.println(a);
	}
} 
