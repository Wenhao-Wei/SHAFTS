package com.shafts.utils;

import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;

public class MyCellRenderer extends DefaultTableCellRenderer {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	       boolean hasFocus, int row, int column) {
	     super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	     setHorizontalAlignment(SwingConstants.CENTER);
	     return this;
	   }
	 }