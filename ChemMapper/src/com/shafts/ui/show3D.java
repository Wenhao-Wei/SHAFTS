package com.shafts.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.shafts.utils.JmolPanel1;

import javax.swing.border.EmptyBorder;

public class show3D extends JFrame {
	
	 private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					show3D frame = new show3D();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public show3D(String name, String namepath) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 250, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		init(name,namepath);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}

	private void init(String name, String namepath) {
		// TODO Auto-generated method stub
		JPanel northPane = new JPanel();
		getContentPane().add(northPane,BorderLayout.NORTH);
		JLabel label = new JLabel();
		label.setText("蛋白质分子"+name+"三维立体结构");
		northPane.add(label);
		northPane.setPreferredSize(new Dimension(450 ,50));
		JmolPanel1 jmolPanel = new JmolPanel1();
		getContentPane().add(jmolPanel, BorderLayout.CENTER);
		jmolPanel.viewer.openFile(namepath+"\\"+name+".mol2");
		String bandian="dots on";
	    jmolPanel.viewer.evalString(bandian);
	}
	


}
