package com.shafts.ui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CheckTableModle extends DefaultTableModel {

    public CheckTableModle(Vector data, Vector columnNames) {
        super(data, columnNames);
    }
    public boolean isCellEditable(int row,int column){         //设置第一列（复选框）可编辑
        if(column == 0){
           return true;
        }else{
           return false;
        }
    }
    // /**
    // * 根据类型返回显示空间
    // * 布尔类型返回显示checkbox
    // */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public void selectAllOrNull(boolean value) {
        for (int i = 0; i < getRowCount(); i++) {
            this.setValueAt(value, i, 0);
        }
    }


}

