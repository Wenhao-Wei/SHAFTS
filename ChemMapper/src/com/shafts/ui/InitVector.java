package com.shafts.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.*;

public class InitVector {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector data;
	public Vector columnNames;
	JScrollPane jscrollpane;

	/**
	 * read the .list file and return the data
	 * 
	 * @param path
	 */
	public Vector getdata(String path, String path1) {
		data = new Vector();
		int i = 1;
		int flag = 0;
		int line = 50;
		if (path == null) {// initial the table
			for (int n = 0; n < line; n++) {
				Vector rowVector = new Vector();
				
				//rowVector.add("");
				rowVector.add("");
				rowVector.add("");
				rowVector.add("");
				rowVector.add("");
				rowVector.add("");
				rowVector.add(false);
				data.add(rowVector);
			}
		} else {
			File file = new File(path);// ��result.list�ļ�
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			String s;
			if (!file.exists()) {
				for (int n = 0; n < line; n++) {
					Vector rowVector = new Vector();
					
					//rowVector.add("");
					rowVector.add("");
					rowVector.add("");
					rowVector.add("");
					rowVector.add("");
					rowVector.add("");
					rowVector.add(false);
					data.add(rowVector);
				}
			} else {
				try {
					Vector rowVector1 = new Vector();
					rowVector1.add("1");
					rowVector1.add("InputFile");
					rowVector1.add("Full");
					rowVector1.add("Full");
					rowVector1.add("Full");
					//rowVector.add(s1[5]);
					rowVector1.add(true);
					data.add(rowVector1);
					fileReader = new FileReader(file);
					bufferedReader = new BufferedReader(fileReader);
					while (bufferedReader.readLine() != null)
						// ��Ϊreadlineÿ�ζ�һ�е�����������ͳ������
						line++;
					fileReader = new FileReader(file);
					bufferedReader = new BufferedReader(fileReader);
					while ((s = bufferedReader.readLine()) != null) {
						// System.out.println(s);
						if (i != 1) {
							String[] s1 = s.split("\\s+");
							Vector rowVector = new Vector();
							
							rowVector.add(s1[0]);
							rowVector.add(s1[1]);
							rowVector.add(s1[2]);
							rowVector.add(s1[3]);
							rowVector.add(s1[4]);
							//rowVector.add(s1[5]);
							rowVector.add(false);
							data.add(rowVector);
						}
						i++;
						flag++;
					}

					if (flag == 1) {
						JOptionPane.showMessageDialog(null,
								"��ƥ����������ļ��鿴��Ӧ��Ϣ��");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	/**
	 * ��ʼ�������б�ע�͵���
	 * 
	 * @return
	 * 
	 */
	public Vector getcolumn() {
		columnNames = new Vector();
		//columnNames.add("Query");  //****Query
		columnNames.add("Rank");
		columnNames.add("Name");
		columnNames.add("HybridScore");
		columnNames.add("ShapeScore");
		columnNames.add("FeatureScore");
		columnNames.add("ShowAll");
		return columnNames;
	}

	public static void main(String args[]) {
		Vector V = new InitVector()
				.getdata("E:\\MyOffice\\Eclipse\\workplace\\ChemMapper\\workhome\\localwork\\Job9\\Result.list","E:\\MyOffice\\Eclipse\\workplace\\ChemMapper\\workhome\\localwork\\Job9\\Input.mol2");
		// String[] v = (String[]) V.toArray();
		for (int i = 0; i < V.size(); i++) {
			System.out.println(V.elementAt(i));
		}
	}
}
