package com.shafts.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Export the result
 * 
 * @author Wenhao Wei 2014-07-08
 */
public class ExportXlsUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 420;
	private final int HEIGHT = 200;
	private JRadioButton rButton1;
	private JRadioButton rButton2;
	private JTextField StartLine;
	private JTextField EndLine;
	private JPanel contentPane;
	private JPanel north;
	private JPanel center;
	private JPanel south;
	private JLabel Startlabel;
	private JLabel Endlabel;
	private JLabel Tip1;
	private JLabel Tip2;
	private JButton OK;
	private JButton CANCEL;

	/**
	 * Export the data from the table
	 * 
	 * @param table
	 */
	public ExportXlsUI(final JTable table) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 100, WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		int width = screensize.width;
		int height = screensize.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		setLocation(x - 50, y - 100);
		contentPane = new JPanel();
		setTitle("Export:");
		this.setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		north = new JPanel(new GridLayout(2, 1, 10, 10));
		center = new JPanel(new GridLayout(2, 3, 10, 10));
		south = new JPanel();

		rButton1 = new JRadioButton("Completely Export");
		rButton2 = new JRadioButton("Custom Export (Based on the rank)");
		ButtonGroup bg = new ButtonGroup();
		bg.add(rButton1);
		bg.add(rButton2);
		north.add(rButton1);
		north.add(rButton2);
		Tip1 = new JLabel("(Point Here!)");
		Tip2 = new JLabel("(Point Here!)");
		Startlabel = new JLabel("Start Piont:");
		Tip1.setToolTipText("The number should be greater than 0");
		Endlabel = new JLabel("End Point:");
		Tip2.setToolTipText("The number should be greater than the starting point");
		StartLine = new JTextField("0");
		// 限制只能输入数字
		StartLine.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {
				} else {
					e.consume(); // 屏蔽掉非法输入
				}
			}
		});
		EndLine = new JTextField("0");
		// 限制只能输入数字
		EndLine.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

				} else {
					e.consume(); // 屏蔽掉非法输入
				}
			}
		});
		Startlabel.setEnabled(false);
		StartLine.setEditable(false);
		Endlabel.setEnabled(false);
		EndLine.setEditable(false);
		center.add(Startlabel);
		center.add(StartLine);
		center.add(Tip1);
		center.add(Endlabel);
		center.add(EndLine);
		center.add(Tip2);
		rButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Startlabel.setEnabled(true);
				StartLine.setEditable(true);
				Endlabel.setEnabled(true);
				EndLine.setEditable(true);
				center.updateUI();
			}
		});
		rButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Startlabel.setEnabled(false);
				StartLine.setEditable(false);
				Endlabel.setEnabled(false);
				EndLine.setEditable(false);
				center.updateUI();
			}
		});
		OK = new JButton("Export");
		CANCEL = new JButton("Cancel");
		south.add(CANCEL, BorderLayout.WEST);
		south.add(OK, BorderLayout.EAST);
		CANCEL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rButton1.isSelected())
					exportXls(table, 1, 1000);
				else if (rButton2.isSelected()) {
					int i = Integer.parseInt(StartLine.getText());
					int j = Integer.parseInt(EndLine.getText());
					if (i == 0 || i > j) {
						if (i == 0)
							JOptionPane.showMessageDialog(null,
									"The start number can't be 0 ！");
						else
							JOptionPane
									.showMessageDialog(null,
											"Input illegal! The start number should be smaller than the end number!");
					} else {
						exportXls(table, i, j);
					}
				}
			}
		});
		contentPane.add(north, BorderLayout.NORTH);
		contentPane.add(center, BorderLayout.CENTER);
		contentPane.add(south, BorderLayout.SOUTH);
		setVisible(true);
	}

	/**
	 * Realize export the result from the table
	 * 
	 * @param table
	 * @param i
	 *            Start position
	 * @param j
	 *            End position
	 */
	protected void exportXls(JTable table, int i, int j) {
		String outFilePath = null;
		String excelFileName = null;
		boolean ExportFlag = false;
		String temp = (String) table.getValueAt(0, 1);
		if (table.getRowCount() == 1 || temp.equals(""))
			JOptionPane.showMessageDialog(null,
					"There is no any data in the form！");
		else {
			JFileChooser fc = new JFileChooser(
					"D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
			fc.setMultiSelectionEnabled(false);
			int result = fc.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				outFilePath = file.getAbsolutePath();
			}
			excelFileName = outFilePath + ".xls";
			try {
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "分子相似性对比结果");
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
				HSSFRow row = sheet.createRow(0);
				HSSFCell cell1 = row.createCell(0); // 第一列
				HSSFCell cell2 = row.createCell(1);
				HSSFCell cell3 = row.createCell(2);
				HSSFCell cell4 = row.createCell(3);
				HSSFCell cell5 = row.createCell(4);
				HSSFCell cell6 = row.createCell(5);
				// 定义单元格为字符串类型
				cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1.setCellValue("Rank");
				cell2.setCellValue("Name");
				cell3.setCellValue("HybridScore");
				cell4.setCellValue("ShapeScore");
				cell5.setCellValue("FeatureScore");
				cell6.setCellValue("Query");

				int Row = 1;
				int RowCount = table.getRowCount();
				if (j >= RowCount)
					j = RowCount;

				int k; // k为列
				while (i <= j) { // i为表的行数 第一行为表头
					row = sheet.createRow(Row);
					for (k = 1; k < 7; k++) {
						HSSFCell cell = row.createCell(k - 1);
						String s = (String) table.getValueAt(i - 1, k);
						// 设置单元格格式
						cell.setCellStyle(cellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(s);
					}
					Row++;
					i++;
				}
				// 建立一个文件输出流
				FileOutputStream fOut = new FileOutputStream(excelFileName);
				// 把相应的Excel工作薄存盘
				workbook.write(fOut);
				// 操作结束，关闭文件
				fOut.flush();
				fOut.close();
				ExportFlag = true;
			} catch (Throwable t) {
				t.printStackTrace();
				JOptionPane.showMessageDialog(null, "Failed！");
			}
			if (ExportFlag)
				JOptionPane.showMessageDialog(null, "Export Success！");
		}

	}

	public static void main(String args[]) {
		JTable t = new JTable();
		new ExportXlsUI(t);
	}
}
