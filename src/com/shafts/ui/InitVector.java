package com.shafts.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.*;

public class InitVector{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector data;
	public Vector columnNames;
	JScrollPane jscrollpane;	
	/**
	 * 读取.list文件并返回data
	 * @param path
	 */
	public Vector getdata(String path){
		//data.removeAllElements();
		File file=new File(path);//找result.list文件
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String s;
		data = new Vector();
		int i = 1; 
		int flag = 0;
		int line=100;
		if(!file.exists() || path == null){
		  System.out.println("找不到.list文件");
		  }
		else{
		   try {
		    fileReader=new FileReader(file);
		    bufferedReader=new BufferedReader(fileReader);
		    while(bufferedReader.readLine()!=null)                     //因为readline每次读一行到缓冲区，故统计行数
		    	line++;
		    fileReader=new FileReader(file);
		    bufferedReader=new BufferedReader(fileReader);
		    while((s=bufferedReader.readLine())!=null){	
		    	//System.out.println(s);
		    	if(i != 1){
		    	String[] s1=s.split("\\s+");
		    	Vector rowVector = new Vector();
		    	rowVector.add(false);
		    	rowVector.add(s1[0]);
		    	rowVector.add(s1[1]);
		    	rowVector.add(s1[2]);
		    	rowVector.add(s1[3]);
		    	rowVector.add(s1[4]);
		    	rowVector.add(s1[5]);
		    	data.add(rowVector);
		    	}
		    	i++;
		    	flag++;		    	
		        }
		    
		    if(flag == 1){
		    	JOptionPane.showMessageDialog( null,"无匹配结果，请打开文件查看相应信息！");
		    }
		    } catch (Exception e) {
		    e.printStackTrace();
		 }		    
	  }
		return data;
	}
	/**
	 * 初始化不带靶标注释的列
	 * @return 
	 * 
	 */
	public Vector getcolumn(){
 		 columnNames = new Vector();
 	     columnNames.add("select");
 	     columnNames.add("Rank");
 	     columnNames.add("Name");
 	     columnNames.add("HybridScore");
 	     columnNames.add("ShapeScore");
 	     columnNames.add("FeatureScore");
 	     columnNames.add("Query");
 	     return columnNames;
    }
	public static void main(String args[]){
		Vector V = new InitVector().getdata("E:\\MyOffice\\Eclipse\\workplace\\ChemMapper\\workhome\\localwork\\Job9\\Result.list");
		//String[] v = (String[]) V.toArray();
		for(int i = 0; i<V.size(); i++){
			System.out.println(V.elementAt(i));
		}
	}
}
