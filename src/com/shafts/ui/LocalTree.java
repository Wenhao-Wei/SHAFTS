package com.shafts.ui;

import java.awt.Dimension;
import java.io.File;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
/**
 * 设计本地作业树
 * @author baoabo
 *
 */

public class LocalTree extends JTree{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultMutableTreeNode root;
	public LocalTree(){
		this.root = new DefaultMutableTreeNode("本地任务");
	}
	/**
	 * 初始化作业
	 */
	public void initwork(){
		String localworkdir = "E:\\MyOffice\\Eclipse\\workplace\\ChemMapper\\workhome\\localwork\\";
		File f1 = new File(localworkdir);
		File[] listdir1 = f1.listFiles();
		for(int i = 0; i<listdir1.length; i++){
			String nodename = listdir1[i].getName();
			addlocalnode(nodename);
		}
		this.setEditable(true);
		this.updateUI();		
	}
	/**
	 * 添加本地node
	 * @param node
	 * 
	 */
	public void addlocalnode(String node){
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(node);
		root.add(node1);
		this.scrollPathToVisible(new TreePath(node1.getPath()));
		this.updateUI();
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalTree lt = new LocalTree();
		lt.initwork();
		//jp.addlocalnode(new DefaultMutableTreeNode("12345"));
		//jp.addnetnode(new DefaultMutableTreeNode("54321"));
		JScrollPane jsp = new JScrollPane();
		jsp.add(lt);
		JFrame jf = new JFrame("TreeTest");
		jf.setLocation(600, 300);
		jf.setPreferredSize(new Dimension(300,500));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setContentPane(jsp);
		jf.pack();
	}

}
