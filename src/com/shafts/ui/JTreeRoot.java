package com.shafts.ui;
import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeRoot extends JFrame {

	 private static final long serialVersionUID = 1L;
private DefaultMutableTreeNode node = null;
String st = "";
String treepath = "";
public JTreeRoot() {

JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        node = new DefaultMutableTreeNode("Root");
        final JTree tree = new JTree(node);
        scrollPane.setViewportView(tree);
        loadingTree(node);
        setSize(650, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setVisible(true);


        tree.addTreeSelectionListener(new TreeSelectionListener(){

			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				// TODO Auto-generated method stub
				javax.swing.tree.DefaultMutableTreeNode selectedNode =
						  (javax.swing.tree.DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			
				 if (selectedNode==null)
		             return;
		     st = selectedNode.toString();
		     if(treepath=="Root"){
		    	// treepath = " ";
		     }
		     else{
		     if (selectedNode.isRoot()){
		    	 st = st+"\\";
		    	 System.out.println(st);
		     }
		     if (selectedNode.isLeaf()){
		    	 
		    	 System.out.println(st);
		     }
		     else
		    	 st = st+"\\";
		     
		     
		    		 treepath = treepath +st;
		    		
		     System.out.println(treepath);
			}
			}
			
        	
        });
        
        
        
    }

private void loadingTree(DefaultMutableTreeNode root) {
	File file = new File("E:\\MyOffice\\Eclipse");
    File[] fs = file.listFiles();
        DefaultMutableTreeNode node = null;
        for (int i = 0; i < fs.length; i++) {
            node = new DefaultMutableTreeNode(fs[i].getPath());
            root.add(node);
            loadingTree(fs[i], node);
        }
    }

private void loadingTree(File root, DefaultMutableTreeNode node) {
        File[] files = root.listFiles();
        DefaultMutableTreeNode subNode = null;
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getPath());
            subNode = new DefaultMutableTreeNode(files[i].getName());
            node.add(subNode);
            if (files[i].isDirectory()) {
                loadingTree(files[i], subNode);
            }
        }

}

public static void main(String[] args) {
        new JTreeRoot();
    }

}