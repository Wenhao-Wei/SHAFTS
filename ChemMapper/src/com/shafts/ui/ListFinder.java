package com.shafts.ui;

import java.io.File;

public class ListFinder {
	public ListFinder(String path){
		GetList(path);
	}
	/**
	 * find the file of "result.list"
	 * @param path
	 * @return
	 */
	public String GetList(String path){
		File file = new File(path);
		String filename;
		File tempfile;
		String name = null;
		if(file.exists()){
		for(int i=0;i<file.list().length;i++){
			filename = file.list()[i];
			tempfile = new File(filename);
			if(tempfile.isDirectory()){}
			else{
				String fileType = filename.substring(filename.lastIndexOf(".")+1);
				if(fileType.equals("list")){
					name = filename;
				}
			}
		 }
		}
		return name;
	}
	public static void main(String[] args){
		String path = "C://Users//xiaobao//Desktop//19895//19895//result";
		String name = new ListFinder(path).GetList(path);
		System.out.println(name);
	}

}
