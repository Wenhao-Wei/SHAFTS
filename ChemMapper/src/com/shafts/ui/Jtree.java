package com.shafts.ui;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;


 
public class Jtree extends JFrame {
    private JTree jtr;
    private static DefaultMutableTreeNode root;
    private static final long serialVersionUID = 1L;
 
    public Jtree() {
         
        //getContentPane().setBackground(Color.gray);
    	setBounds(50, 50, 300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File file = new File("E:\\");
        File[] fs = file.listFiles();
        System.out.println(fs.length);
         
        root = new DefaultMutableTreeNode(file);
        init();
         
        search(file, root);
         
        this.setVisible(true);
        
        
    }
    String st = "";
    String treepath = null;
    public void init() {
        jtr = new JTree(root);
        new JScrollPane(jtr);
        add(jtr);
  
        jtr.addTreeSelectionListener(new TreeSelectionListener(){

			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				// TODO Auto-generated method stub
				javax.swing.tree.DefaultMutableTreeNode selectedNode =
						  (javax.swing.tree.DefaultMutableTreeNode) jtr.getLastSelectedPathComponent();
			
				 if (selectedNode==null)
		             return;
		     st = selectedNode.toString();
		     if(treepath==null){
		    	 treepath = "E:\\3DMV\\";
		     }
		     else{
		     if (selectedNode.isRoot()){
		    	 st = st+"\\";
		    	 //System.out.println(st);
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
 
    public static void search(File file, DefaultMutableTreeNode node) {
        File[] f = file.listFiles();
        if(f == null)
        	return;
        for (int i = 0; i < f.length; i++){
            DefaultMutableTreeNode nodes = new DefaultMutableTreeNode(
                    f[i].getName());
            node.add(nodes);
            if (f[i].isDirectory()) {
                search(f[i], nodes);
            }
            else{
                return;
            }
        }
    }
    public static void main(String args[]){
        new Jtree();
    }
    
    
 
    
    
    
    
}