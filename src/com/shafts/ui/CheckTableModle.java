package com.shafts.ui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CheckTableModle extends DefaultTableModel {

    public CheckTableModle(Vector data, Vector columnNames) {
        super(data, columnNames);
    }
    public boolean isCellEditable(int row,int column){         //���õ�һ�У���ѡ�򣩿ɱ༭
        if(column == 0){
           return true;
        }else{
           return false;
        }
    }
    // /**
    // * �������ͷ�����ʾ�ռ�
    // * �������ͷ�����ʾcheckbox
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

