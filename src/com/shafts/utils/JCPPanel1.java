package com.shafts.utils;


import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.jchempaint.JChemPaintPanel;

public class JCPPanel1 extends JChemPaintPanel{
	
	private static final long serialVersionUID = -5511237816515523494L;

	public static void main(String[] args) {
        
		JCPPanel1 jcpPanel = new JCPPanel1();
		
		JFrame frame = new JFrame();
		//frame.setPreferredSize(new Dimension(800, 494));    //1.618
		
		jcpPanel.updateStatusBar();
        frame.add(jcpPanel);
        frame.pack();
        
        Point point = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getCenterPoint();
        int w2 = (frame.getWidth() / 2);
        int h2 = (frame.getHeight() / 2);
        frame.setLocation(point.x - w2, point.y - h2);
        
        frame.setVisible(true);
	}
	
	public JCPPanel1() {
		super(emptyModel(), GUI_APPLICATION, debug, null, new ArrayList<String>());
	}
		
	public static IChemModel emptyModel() {
        IChemModel chemModel = DefaultChemObjectBuilder.getInstance().newInstance(IChemModel.class);
        chemModel.setMoleculeSet(chemModel.getBuilder().newInstance(IMoleculeSet.class));
        chemModel.getMoleculeSet().addAtomContainer(
                chemModel.getBuilder().newInstance(IMolecule.class));
        return chemModel;
    }
	
	
	private static final String GUI_APPLICATION = "application";
	private static boolean debug = false;
}


