package com.shafts.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.shafts.utils.JmolPanel1;

import javax.swing.border.EmptyBorder;

public class Viewin3D extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Viewin3D frame = new Viewin3D();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Viewin3D() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		init();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	
	private void init(){
		JPanel northPane = new JPanel();
		final JmolPanel1 jmolPanel = new JmolPanel1();
		northPane.setPreferredSize(new Dimension(450,40));
		northPane.setLayout(new FlowLayout());
		getContentPane().add(northPane,BorderLayout.NORTH);
		JButton jbutton = new JButton();
		jbutton.setText("		打开文件...		");
		northPane.add(jbutton);

		getContentPane().add(jmolPanel, BorderLayout.CENTER);
		
		jbutton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		JFileChooser file = new JFileChooser("E:\\MyOffice\\Eclipse\\workplace\\ChemMapper");
        		file.showOpenDialog(null);
        		file.setDialogTitle("请选择要打开的文件...");
        	    jmolPanel.viewer.openFile(file.getSelectedFile().getAbsolutePath());
        	    String bandian="dots on";
        	    jmolPanel.viewer.evalString(bandian);
  
        		}
        	}
        );
	}

}
