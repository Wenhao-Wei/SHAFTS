package com.shafts.ui;

import java.awt.*; //******

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.shafts.application.CreateMolecule;
import com.shafts.application.Shafts;
import com.shafts.utils.CheckUserStatus;
//import com.shafts.application.CreateMolecule.RenderThread;
import com.shafts.utils.JmolPanel1;
import com.shafts.utils.MyCellRenderer;
import com.shafts.utils.PropertyConfig;
import com.shafts.utils.TimerMessageDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	static LaunchAnimation LA;

	public static void main(String[] args) {

		new Thread() {
			public void run() {
				LA = new LaunchAnimation();
				LA.setUndecorated(true);
				LA.setVisible(true);
			}
		}.start();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { // 设置外观
					JFrame.setDefaultLookAndFeelDecorated(true);
					// JDialog.setDefaultLookAndFeelDecorated(true);

					// UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
					// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					UIManager
							.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
					// LiquidLookAndFeel.setLiquidDecorations(true, "mac");
					LiquidLookAndFeel.setLiquidDecorations(true);
					MainUI frame = new MainUI();
					frame.setVisible(true);
					LA.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 设置本地作业右键菜单
	 */

	private void setlocalpop() {
		pm1 = new JPopupMenu();
		JMenuItem menuitem1 = new JMenuItem();
		JMenuItem menuitem2 = new JMenuItem();
		JMenuItem menuitem3 = new JMenuItem();
		menuitem1.setText("Rename");
		menuitem1.addActionListener(new ActionListener() { // 菜单1的事件监听
					public void actionPerformed(ActionEvent e) {
						// 菜单事件函数
						nowNode = (DefaultMutableTreeNode) LocalTree
								.getLastSelectedPathComponent();
						oldName = nowNode.toString();
						LocalTree.startEditingAtPath(LocalTree
								.getSelectionPath());
						IsRename = 1;
						// String newName=nowNode.toString();
						// File file = new File(localworkPath + newName);
						// renameFile(oldName,file,localworkPath);
						// TreePath path = LocalTree.getSelectionPath();
						// LocalTree.startEditingAtPath(path);
					}
				});
		menuitem2.setText("Open In Explorer ");
		menuitem2.addActionListener(new ActionListener() { // 菜单2的事件监听
					public void actionPerformed(ActionEvent e) {
						// 菜单事件函数
						try {
							String filepath = localworkPath + nodeName;
							// System.out.println(filePath);
							Runtime.getRuntime().exec(
									"explorer.exe /select," + filepath);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
		menuitem3.setText("Delete");
		menuitem3.addActionListener(new ActionListener() { // 菜单3的事件监听
					public void actionPerformed(ActionEvent e) {
						// 菜单事件函数
						String filepath = localworkPath + nodeName;
						// System.out.println(filepath);
						int i = JOptionPane
								.showConfirmDialog(
										null,
										"Cannot be retrieved after deletion!Are you sure to delete it？",
										"Delete", JOptionPane.YES_NO_OPTION);
						if (i == JOptionPane.OK_OPTION) {
							File file = new File(filepath);
							try {
								if (file.isFile()) {
									file.delete();
								}
								if (file.isDirectory()) {
									File files[] = file.listFiles();
									for (int j = 0; j < files.length; j++)
										files[j].delete();
									file.delete();
									nodeName = null;
								}

							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Failed！");
							}
							dellocalnode((DefaultMutableTreeNode) treenode);
						}
					}
				});
		pm1.add(menuitem1);
		pm1.add(menuitem2);
		pm1.add(menuitem3);
	}

	/**
	 * 设置网络作业模块右键菜单、鼠标事件
	 */
	private void setnetpop() {
		pm2 = new JPopupMenu();
		JMenuItem menuitem1 = new JMenuItem();
		JMenuItem menuitem2 = new JMenuItem();
		JMenuItem menuitem3 = new JMenuItem();
		menuitem1.setText("Rename");
		menuitem1.addActionListener(new ActionListener() { // 菜单1的事件监听
					public void actionPerformed(ActionEvent e) {
						// 菜单事件函数
						nowNode = (DefaultMutableTreeNode) NetTree
								.getLastSelectedPathComponent();
						oldName = nowNode.toString();
						NetTree.startEditingAtPath(NetTree.getSelectionPath());
						IsRename = 1;
						// String newName=nowNode.toString();
						// File file = new File(networkPath + newName);
						// renameFile(oldName,file,networkPath);
						// TreePath path = NetTree.getSelectionPath();
						// NetTree.startEditingAtPath(path);
					}
				});
		menuitem2.setText("Open In Explorer");
		menuitem2.addActionListener(new ActionListener() { // 菜单1的事件监听
					public void actionPerformed(ActionEvent e) {
						// 菜单事件函数
						try {
							String filepath = networkPath + nodeName;
							// System.out.println(filePath);
							Runtime.getRuntime().exec(
									"explorer.exe /select," + filepath);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
		menuitem3.setText("Delete");
		menuitem3.addActionListener(new ActionListener() { // 菜单1的事件监听
					public void actionPerformed(ActionEvent e) {
						// 菜单事件函数
						String filepath = networkPath + nodeName;
						// System.out.println(filepath);
						int i = JOptionPane
								.showConfirmDialog(
										null,
										"Cannot be retrieved after deletion!Are you sure to delete it？",
										"Delete", JOptionPane.YES_NO_OPTION);
						if (i == JOptionPane.OK_OPTION) {
							File file = new File(filepath);
							try {
								if (file.isFile()) {
									file.delete();
								}
								if (file.isDirectory()) {
									File files[] = file.listFiles();
									for (int j = 0; j < files.length; j++)
										files[j].delete();
									file.delete();
									nodeName = null;
								}
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Failed！");
							}
							delnetnode((DefaultMutableTreeNode) treenode);

						}
					}
				});
		pm2.add(menuitem1);
		pm2.add(menuitem2);
		pm2.add(menuitem3);
	}

	/**
	 * 重命名文件夹
	 * 
	 * @param oldname
	 * @param newname
	 * @param Path
	 */
	public void renameFile(String oldname, File newname, String Path) {
		File file = new File(Path + oldname);
		file.renameTo(newname);
	}

	/**
	 * Create the frame.
	 */

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
		CreateMolecule createMolecule = new CreateMolecule();
		createMolecule.setVisible(true);
		createMolecule.startPaintingThread();

	}

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		System.exit(0);
	}

	String inFormat = null;
	String outFormat = null;
	String inFilePath = null;
	String outFilePath = null;
	String fileName = null;
	String DataBase = null;
	String show3Dname = null;
	String Filepath = null;
	String ResultPath = null;
	String model1;
	String model2;
	int model;

	/*
	 * //格式转换 public void formatconv() { System.loadLibrary("openbabel_java");
	 * // Read molecule from informat OBConversion conv = new OBConversion();
	 * OBMol mol = new OBMol(); try{
	 * 
	 * conv.SetInFormat(inFormat); conv.ReadFile(mol, inFilePath);
	 * conv.SetOutFormat(outFormat); conv.WriteFile(mol, outFilePath);
	 * JOptionPane.showMessageDialog( null,"Convert Success！"); }catch(Exception
	 * e){ JOptionPane.showMessageDialog( null,"Failed，please retry！"); }
	 * 
	 * // conv.WriteString(mol); }
	 */

	/**
	 * 初始化作业树
	 *
	 */
	public void initwork() {
		LocalTree.removeAll();
		NetTree.removeAll();
		// String localworkdir =
		// "D:\\MyOffice\\Github\\SHAFTS\\ChemMapper\\workhome\\localwork\\";
		// String networkdir =
		// "D:\\MyOffice\\Github\\SHAFTS\\ChemMapper\\workhome\\network\\";
		File f1 = new File(localworkPath);
		File f2 = new File(networkPath);
		if (!f1.exists())
			f1.mkdir();
		if (!f2.exists())
			f2.mkdir();
		File[] listdir1 = f1.listFiles();
		File[] listdir2 = f2.listFiles();
		if (listdir1 != null) {
			for (int i = 0; i < listdir1.length; i++) {
				String nodename = listdir1[i].getName();
				addlocalnode(nodename);
			}
		}
		if (listdir2 != null) {
			for (int i = 0; i < listdir2.length; i++) {
				String nodename = listdir2[i].getName();
				addnetnode(nodename);
			}
		}
		LocalTree.setEditable(true);
		NetTree.setEditable(true);
		LocalTree.updateUI();
		NetTree.updateUI();
	}

	/**
	 * 添加本地node
	 * 
	 * @param node
	 * 
	 */
	public void addlocalnode(String node) {
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(node);
		root1.add(node1);
		LocalTree.scrollPathToVisible(new TreePath(node1.getPath()));
		LocalTree.updateUI();

	}

	/**
	 * 添加网络node
	 * 
	 * @param node
	 * 
	 */
	public void addnetnode(String node) {
		// String path =
		// "D:\\MyOffice\\Github\\SHAFTS\\ChemMapper\\workhome\\network\\" +
		// node;
		String path = networkPath + node;
		File file = new File(path);
		file.mkdir();
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(node);
		root2.add(node1);
		NetTree.scrollPathToVisible(new TreePath(node1.getPath()));
		NetTree.updateUI();

	}

	/**
	 * 删除本地node
	 * 
	 * @param path
	 */
	public void dellocalnode(DefaultMutableTreeNode node) {
		DefaultTreeModel model = (DefaultTreeModel) LocalTree.getModel();
		model.removeNodeFromParent(node);

	}

	/**
	 * 删除网络node
	 * 
	 * @param path
	 */
	public void delnetnode(DefaultMutableTreeNode node) {
		DefaultTreeModel model = (DefaultTreeModel) NetTree.getModel();
		model.removeNodeFromParent(node);

	}

	private void initComponents() {

		eastJPanel = new JPanel();
		westJPanel = new JPanel();
		northJPanel = new JPanel();
		southJPanel = new JPanel();
		centerJPanel = new JPanel();
		eastJPanel.setPreferredSize(new Dimension(270, 370));
		westJPanel.setPreferredSize(new Dimension(270, 370));
		northJPanel.setPreferredSize(new Dimension(1200, 50));
		southJPanel.setPreferredSize(new Dimension(1200, 200));
		getContentPane().add(eastJPanel, BorderLayout.EAST);
		getContentPane().add(westJPanel, BorderLayout.WEST);
		getContentPane().add(northJPanel, BorderLayout.NORTH);
		getContentPane().add(southJPanel, BorderLayout.SOUTH);
		getContentPane().add(centerJPanel, BorderLayout.CENTER);

		/**
		 * 鼠标事件，显示分子在主界面
		 * 
		 * @author baoabo
		 *
		 */
		
	/*	class ShowMol extends MouseAdapter { 
			public void mouseClicked(MouseEvent e) { 
				if (e.getButton() == MouseEvent.BUTTON1) {// 单击鼠标左键 
					if (e.getClickCount() == 1 && jTable1.getSelectedColumn() == 5) { 
						String stringName[] = new String[20]; 
						int colummCount = jTable1.getModel().getColumnCount();// 列数
						int rowCount = jTable1.getModel().getRowCount();// 行数 
						for (int i = 1; i < colummCount; i++)
							stringName[i] = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), i) .toString(); 
						show3Dname = stringName[2]; 
						String path1 = FilePath1 + show3Dname + ".mol2"; 
						File mol2file = new File(path1); 
						if (!mol2file.exists()) { 
							show3Dname = stringName[1]; 
							}
						boolean bl = (boolean)jTable1.getModel().getValueAt( jTable1.getSelectedRow(), 0); 
						String path2 = FilePath1 + show3Dname + ".mol2"; 
						if (bl == false) {
							jTable1.getModel().setValueAt(true, jTable1.getSelectedRow(), 0);
							flag++; 
							ifshow.put(show3Dname, new Integer(flag)); 
							String controller1 = "load APPEND " + "\"" + path2 + "\"" + " ;frame*"; 
							String controller2 = "dots on"; 
							jmolPanel2.viewer.evalString(controller1);
							jmolPanel2.viewer.evalString(controller2); 
							} 
						else {
							jTable1.getModel().setValueAt(false, jTable1.getSelectedRow(), 0);
							int a = (int) ifshow.get(show3Dname); String b = a + ".1"; 
							String controller = "zap " + b; 
							jmolPanel2.viewer.evalString(controller);
							ifshow.remove(show3Dname); 
							if (ifshow.isEmpty()) flag = 0; 
							} 
						} 
					} 
				} 
			}*/
		class ShowAll extends MouseAdapter{
			public void mouseClicked(MouseEvent e) { 
				if (e.getClickCount() > 0) {
                    //获得选中列
                	String stringName1[] = new String[20];
                	int rowCount = jTable1.getModel().getRowCount();// 行数
                    int selectColumn = header.columnAtPoint(e.getPoint());
                    if (selectColumn == 5) {
                    	boolean selectAll = (boolean)jTable1.getModel().getValueAt( 1, 5);
                    	if(selectAll){
                    		flag = 0;
							ifshow.clear();
							String controller = "zap all"; 
							jmolPanel2.viewer.evalString(controller);
							InputFilepath = FilePath1 + "Input.mol2";
							String controller1 = "load APPEND " + "\"" + InputFilepath
									+ "\"" + " ;frame*" + " ;dots on";
							jmolPanel2.viewer.evalString(controller1);
							flag++;
							ifshow.put("Input", new Integer(flag));
							for (int i = 1; i < rowCount; i++){
								for (int j = 1; j < jTable1.getModel().getColumnCount(); j++)
									stringName1[j] = jTable1.getModel().getValueAt(i, j) .toString(); 
									show3Dname = stringName1[1]; 
									String path3 = FilePath1 + show3Dname + ".mol2"; 
									File mol2file2 = new File(path3); 
									if (!mol2file2.exists()) 
										show3Dname = stringName1[0];
										flag++; 
										ifshow.put(show3Dname, new Integer(flag)); 
										String controller2 = "load APPEND " + "\"" + path3 + "\"" + " ;frame*" + " ;dots on"; 
										//String controller2 = ""; 
										jmolPanel2.viewer.evalString(controller2);
									
								}
                    		}
                    	else{
                    		flag = 0;
							ifshow.clear();
							String controller = "zap all"; 
							jmolPanel2.viewer.evalString(controller);
                    	}
                    }
                }
				}
		}
		class ShowMol extends MouseAdapter { 
			public void mouseClicked(MouseEvent e) { 
				if (e.getButton() == MouseEvent.BUTTON1) {// 单击鼠标左键 
					String stringName[] = new String[20]; 
					int colummCount = jTable1.getModel().getColumnCount();// 列数					 
					for (int i = 1; i < colummCount; i++)
						stringName[i] = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), i) .toString(); 
					show3Dname = stringName[1]; 
					String path1 = FilePath1 + show3Dname + ".mol2"; 
					File mol2file = new File(path1); 
					if (!mol2file.exists())  
						show3Dname = stringName[0];						
					boolean bl = (boolean)jTable1.getModel().getValueAt( jTable1.getSelectedRow(), 5); 
					String path2 = FilePath1 + show3Dname + ".mol2"; 					                  		                    
					if (e.getClickCount() == 1 && jTable1.getSelectedColumn() == 5) { 						
						if (bl) {
							//jTable1.getModel().setValueAt(true, jTable1.getSelectedRow(), 5);
							if(jTable1.getSelectedRow() == 0){
									String controller1 = "load APPEND " + "\"" + InputFilepath
											+ "\"" + " ;frame*" + " ;dots on";
									jmolPanel2.viewer.evalString(controller1);
									int newflag = 0;
									Iterator iter = ifshow.entrySet().iterator();
									while(iter.hasNext()){
										 Map.Entry entry = (Map.Entry)iter.next(); //得到这个序列的映射项，就是set中的类型，HashMap都是Map.Entry类型（详情见map接口声明）
									     String key = (String)entry.getKey(); //获得key
									     int value = (int)entry.getValue(); //获得value，都要强制转换一下
									     if(newflag < value)
									    	 newflag = value;
									}
									newflag = newflag+1;
									ifshow.put("Input", new Integer(newflag));
								}
							else{	
									int newflag = 0;
									Iterator iter = ifshow.entrySet().iterator();
									while(iter.hasNext()){
										 Map.Entry entry = (Map.Entry)iter.next(); //得到这个序列的映射项，就是set中的类型，HashMap都是Map.Entry类型（详情见map接口声明）
									     String key = (String)entry.getKey(); //获得key
									     int value = (int)entry.getValue(); //获得value，都要强制转换一下
									     if(newflag < value)
									    	 newflag = value;
									}
									newflag = newflag+1;
								ifshow.put(show3Dname, new Integer(newflag)); 
								String controller1 = "load APPEND " + "\"" + path2 + "\"" + " ;frame*" + " ;dots on"; 
								//String controller2 = ""; 
								jmolPanel2.viewer.evalString(controller1);
								//jmolPanel2.viewer.evalString(controller2); 
								} 
							}
						else {
							if(jTable1.getSelectedRow() == 0){
									int a = (int) ifshow.get("Input"); 
									String b = a + ".1";
									String controller = "zap " + b; 
									jmolPanel2.viewer.evalString(controller);
									ifshow.remove("Input"); 
									if (ifshow.isEmpty()) 
										flag = 0; 
								}
								//jTable1.getModel().setValueAt(false, jTable1.getSelectedRow(), 5);
								else{
									int a = (int) ifshow.get(show3Dname); 
									String b = a + ".1"; 
									String controller = "zap " + b; 
									jmolPanel2.viewer.evalString(controller);
									ifshow.remove(show3Dname); 
									if (ifshow.isEmpty()) 
										flag = 0; 
								}
							} 
						} 
					} 
				} 
			}
		 

		/**
		 * 2D编辑对应的三维显示
		 * 
		 * @author baobao
		 *
		 */
		class TwoDThread extends Thread {
			private long lastmodified1 = 0;
			private final int SLEEP_TIME = 500;

			@SuppressWarnings("static-access")
			public void run() {
				file3 = new File(createMolecule.RENDER_FILE_NAME);
				String filePath = file3.getAbsolutePath();
				System.out.println(filePath);
				jTextField1.setText(filePath);
				while (true) {
					if (file3.exists() && file3.lastModified() > lastmodified1) {
						jmolPanel2.viewer.openFile(filePath);// CreateMolecule.RENDER_FILE_NAME
						String bandian = "dots on";
						jmolPanel2.viewer.evalString(bandian); // 修改 2014.1.2
						lastmodified1 = file3.lastModified();
					}

					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
						// ignore the exception caused by the application's
						// close
					}
				}
			}

			@Override
			public void interrupt() {
				super.interrupt();
				if (file3.exists())
					file3.delete();
			}
		}
		/**
		 * 网络作业事件
		 * 
		 * @author baobao
		 *
		 */
		class NetworkMouseHandle extends MouseAdapter { // 处理mouse点选事件
														// String nodeName =
														// null;
			boolean F = true;

			public void mousePressed(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					JTree NetTree = (JTree) e.getSource();
					int rowLocation = NetTree.getRowForLocation(e.getX(),
							e.getY());
					TreePath treepath = NetTree.getPathForRow(rowLocation);
					if (treepath == null) {
						if (IsRename == 1) {
							NetTree.stopEditing();
							String newName = nowNode.toString();
							File file = new File(networkPath + newName);
							renameFile(oldName, file, networkPath);
							IsRename = 0;
						}
					} else {
						treenode = (TreeNode) treepath.getLastPathComponent();
						nodeName = treenode.toString();
					}
				}
				if (e.isMetaDown()) {
					JTree NetTree = (JTree) e.getSource();
					int rowLocation = NetTree.getRowForLocation(e.getX(),
							e.getY());
					TreePath treepath = NetTree.getPathForRow(rowLocation);
					if (treepath == null) {
						F = false;
					}
					// System.out.println(flag);
					if (nodeName == null || nodeName == "NetWork")
						F = false;
					if (F) {
						setnetpop();
						pm2.show(NetTree, e.getX(), e.getY());
					}
				}
				if (e.getClickCount() == 2 && F) {
					new Thread() {
						public void run() {
							try {
								JTree tree = (JTree) e.getSource();
								int rowLocation = tree.getRowForLocation(
										e.getX(), e.getY());
								TreePath treepath = tree
										.getPathForRow(rowLocation);
								TreeNode treenode = (TreeNode) treepath
										.getLastPathComponent();
								nodeName = treenode.toString();
							} catch (NullPointerException ne) {
							}
							if (!nodeName.equals("NetWork")) {
								FilePath1 = networkPath + nodeName + "\\";
								String name = new ListFinder(FilePath1)
										.GetList(FilePath1);
								if (name == null) {
									SFTPConnection sftp = new SFTPConnection();
									sftp.connect();
									sftp.batchDownLoadFile(nodeName, FilePath1);
									sftp.disconnect();
								}
								southJPanel.removeAll();
								// southJPanel.updateUI();
								String path = FilePath1 + name;
								data = IV.getdata(path,FilePath1);
								Vector columnNames = IV.getcolumn();
								jTable1 = new JTable();
								jTable1.setDefaultRenderer(String.class,
										new MyCellRenderer());
								CheckTableModle tableModel = new CheckTableModle(
										data, columnNames);
								jTable1.setModel(tableModel);
								jTable1.getTableHeader().setDefaultRenderer(
										new CheckHeaderCellRenderer(jTable1));
								jTable1.addMouseListener(new ShowMol());
								header = jTable1.getTableHeader();
								header.addMouseListener(new ShowAll());
								JScrollPane jScrollPane1 = new JScrollPane(
										jTable1);
								southJPanel.add(jScrollPane1);
								southJPanel.updateUI();
								jmolPanel2.viewer.evalString("zap all");
								flag = 0;
								// ifshow.clear();
								ifshow = new Hashtable<String, Integer>();
								flag++;
								ifshow.put("Input", new Integer(flag));
								InputFilepath = FilePath1 + "Input.mol2";
								String controller1 = "load APPEND " + "\"" + InputFilepath
										+ "\"" + " ;frame*" + " ;dots on";
								jmolPanel2.viewer.evalString(controller1);
							}
						}
					}.start();
				}
			}
		}
		;
		/**
		 * 本地作业事件
		 * 
		 * @author baoabo
		 *
		 */
		class LocalworkMouseHandle extends MouseAdapter { // 处理mouse点选事件
			boolean F = false;

			public void mousePressed(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					F = true;
					JTree localTree = (JTree) e.getSource();
					int rowLocation = localTree.getRowForLocation(e.getX(),
							e.getY());
					TreePath treepath = localTree.getPathForRow(rowLocation);
					if (treepath == null) {
						if (IsRename == 1) {
							LocalTree.stopEditing();
							String newName = nowNode.toString();
							File file = new File(localworkPath + newName);
							renameFile(oldName, file, localworkPath);
							IsRename = 0;
						}
					} else {
						treenode = (TreeNode) treepath.getLastPathComponent();
						nodeName = treenode.toString();
						// System.out.println(nodeName);
					}
				}
				if (e.isMetaDown()) {
					JTree localTree = (JTree) e.getSource();
					int rowLocation = localTree.getRowForLocation(e.getX(),
							e.getY());
					TreePath treepath = localTree.getPathForRow(rowLocation);
					if (treepath == null) {
						F = false;
					}
					// System.out.println(flag);
					if (nodeName == null || nodeName == "LocalJob")
						F = false;
					if (F) {
						setlocalpop();
						pm1.show(LocalTree, e.getX(), e.getY());
					}
				}
				if (e.getClickCount() == 2 && F) {
					new Thread() {
						public void run() {
							try {
								JTree tree = (JTree) e.getSource();
								int rowLocation = tree.getRowForLocation(
										e.getX(), e.getY());
								TreePath treepath = tree
										.getPathForRow(rowLocation);
								TreeNode treenode = (TreeNode) treepath
										.getLastPathComponent();
								nodeName = treenode.toString();
							} catch (NullPointerException ne) {
							}
							if (!nodeName.equals("LocalJob")) {
								FilePath1 = localworkPath + nodeName + "\\";
								String name = new ListFinder(FilePath1)
										.GetList(FilePath1);
								String path = FilePath1 + name;
								data = IV.getdata(path,null);
								Vector columnNames = IV.getcolumn();
								southJPanel.removeAll();
								jTable1 = new JTable();
								jTable1.setDefaultRenderer(String.class,
										new MyCellRenderer());
								CheckTableModle tableModel = new CheckTableModle(
										data, columnNames);
								jTable1.setModel(tableModel);
								jTable1.getTableHeader().setDefaultRenderer(
										new CheckHeaderCellRenderer(jTable1));
								jTable1.addMouseListener(new ShowMol());
								JScrollPane jScrollPane1 = new JScrollPane(
										jTable1);
								southJPanel.add(jScrollPane1);
								southJPanel.updateUI();
								flag = 0;
								// ifshow.clear();
								ifshow = new Hashtable<String, Integer>();
							}
						}
					}.start();
				}
			}
		}
		;

		/**
		 * northpanel
		 */
		northJPanel.setLayout(new GridLayout(2, 1));
		jMenuBar1 = new JMenuBar();
		jMenu1 = new JMenu();
		jMenu2 = new JMenu();
		jMenu3 = new JMenu();
		jMenu4 = new JMenu();
		jMenu5 = new JMenu();
		jMenu6 = new JMenu();
		jMenu7 = new JMenu();
		jMenu8 = new JMenu();
		jMenu9 = new JMenu();
		jmenuitem = new JMenuItem();
		jMenu1.setText("  		File			");
		jMenuBar1.add(jMenu1);
		jMenu2.setText("  		Tools 		");
		jMenuBar1.add(jMenu2);
		jMenu3.setText("  		Molecule 		");
		jMenuBar1.add(jMenu3);
		jMenu4.setText("  		Setting  		");
		jMenuBar1.add(jMenu4);
		jMenu5.setText("  		Help  		");
		jMenuBar1.add(jMenu5);
		jMenu6.setText("  		About...  		");
		jMenuBar1.add(jMenu6);
		for (int i = 1; i < 10; i++) {
			jmenu = new JMenu("                            ");
			jmenu.setEnabled(false);
			jMenuBar1.add(jmenu);
		}
		switch (IsUse) {
		case 0:
			jmenuitem.setText("Active please");
			break;
		case 1:
			jmenuitem.setText("Welcome！");
			break;
		case 2:
			jmenuitem.setText("Overdue");
			break;
		case 3:
			jmenuitem.setText("Server 	Error！");
			break;
		default:
			jmenuitem.setText("Off line！");
			break;
		}
		jMenuBar1.add(jmenuitem);
		// System.out.println("{}{}{}}{}{{}{{}{}"+jmenuitem.getText());
		jmenuitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jmenuitem.getText().equals("Active please")
						|| jmenuitem.getText().equals("Overdue")) {
					AuthorizeUI IAI = new AuthorizeUI();
					boolean success = IAI.getsuccess();
					if (success) {
						new TimerMessageDialog();
						Reboot();
						// jmenuitem.setText("欢迎使用！");
						// jMenuBar1.updateUI();
					}
				} else if (jmenuitem.getText().equals("Welcome！")) {
					CheckUserStatus CUS = new CheckUserStatus();
					int days = CUS.getdays();
					int i = JOptionPane
							.showConfirmDialog(
									null,
									"Dear user! You can still enjoy the SHAFTS for "
											+ days
											+ " days！\n            Do you want to extend the use date？");
					if (i == JOptionPane.OK_OPTION) {
						AuthorizeUI IAI = new AuthorizeUI();
					}
				} else if (jmenuitem.getText().equals("Off line！")
						|| jmenuitem.getText().equals("Server Error！")) {
					CheckUserStatus CUS = new CheckUserStatus();
					IsUse = CUS.getuserstatus();
					if (IsUse == 0)
						jmenuitem.setText("Active please!");
					else if (IsUse == 1)
						jmenuitem.setText("Welcome！");
					else if (IsUse == 2)
						jmenuitem.setText("Overdue");
					else
						JOptionPane.showMessageDialog(null,
								"Sorry! The server is under maintenance！");
				}
			}
		});
		jMenu7.setText("  Surface  ");
		jMenu8.setText("  Background  ");
		jMenu9.setText("  Spin  ");
		jMenu3.add(jMenu7);
		jMenu3.add(jMenu8);
		jMenu3.add(jMenu9);
		jMenuItem1 = new JMenuItem();
		jMenuItem2 = new JMenuItem();
		jMenuItem3 = new JMenuItem();
		jMenuItem4 = new JMenuItem();
		jMenuItem5 = new JMenuItem();
		jMenuItem6 = new JMenuItem();
		jMenuItem7 = new JMenuItem();
		jMenuItem8 = new JMenuItem();
		jMenuItem9 = new JMenuItem();
		jMenuItem10 = new JMenuItem();
		jMenuItem11 = new JMenuItem();
		jMenuItem12 = new JMenuItem();
		jMenuItem13 = new JMenuItem();
		jMenuItem14 = new JMenuItem();
		jMenuItem15 = new JMenuItem();
		jMenuItem16 = new JMenuItem(); // jmol设置
		jMenuItem17 = new JMenuItem();
		jMenuItem18 = new JMenuItem();
		jMenuItem19 = new JMenuItem();
		jMenuItem20 = new JMenuItem();
		jMenuItem21 = new JMenuItem();
		jMenuItem22 = new JMenuItem();
		jMenuItem23 = new JMenuItem();
		jMenuItem24 = new JMenuItem();
		jMenuItem25 = new JMenuItem();
		jMenuItem26 = new JMenuItem();
		jMenuItem27 = new JMenuItem();
		jMenuItem28 = new JMenuItem();
		jMenuItem29 = new JMenuItem();
		jMenuItem30 = new JMenuItem();
		jMenuItem31 = new JMenuItem();
		jMenuItem32 = new JMenuItem();
		jMenuItem33 = new JMenuItem();
		jMenuItem34 = new JMenuItem();
		jMenuItem35 = new JMenuItem();
		jMenuItem36 = new JMenuItem();
		jMenuItem37 = new JMenuItem();

		jMenuItem1.setText("	Open File...	");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JFileChooser file = new JFileChooser(
						"D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
				file.showOpenDialog(null);
				file.setDialogTitle("Please choose the input file...");
				inFilePath = file.getSelectedFile().getAbsolutePath();
				jmolPanel2.viewer.openFile(inFilePath);
				String bandian = "dots on";
				jmolPanel2.viewer.evalString(bandian);
				File file11 = new File(inFilePath);
				jtextField3.setText(file11.getName());
				// jTextField1.setText(inFilePath);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem3.setText("	New Create	");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jcpui = new JcpUI();
				jcpui.setVisible(true);
				TwoDThread TThread = new TwoDThread();
				TThread.start();
			}
		});
		jMenu1.add(jMenuItem3);

		jMenuItem2.setText("	Exit		");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuItem4.setText("	Formate Convert	");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				FormatConvUI formatconv = new FormatConvUI();
				// formatconv.setVisible(true);//格式转换
			}
		});
		jMenu2.add(jMenuItem4);

		/*
		 * jMenuItem5.setText("	need remove...	");
		 * jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent evt) {
		 * 
		 * //similarityStart start = new similarityStart();
		 * //start.setVisible(true); } }); jMenu2.add(jMenuItem5);
		 */

		jMenuItem6.setText("	Set Your Workspace...	 ");
		jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});
		jMenu4.add(jMenuItem6);

		jMenuItem7.setText("	Check your info...	 ");
		jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				// Viewin3D viewer = new Viewin3D();
				// viewer.setVisible(true);
			}
		});
		jMenu4.add(jMenuItem7);

		jMenuItem9.setText("	SHAFTS Help	 ");
		jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(new File("Readme.txt"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jMenu5.add(jMenuItem9);

		jMenuItem10.setText("	Userconfig	 ");
		jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if (inFilePath == null)
					JOptionPane.showMessageDialog(null, "请先打开文件！");
				else {
					String gif = "write image '?FILEROOT?.gif'";
					jmolPanel2.viewer.evalString(gif);
				}
			}
		});
		jMenu5.add(jMenuItem10);

		jMenuItem11.setText("	About US...	 ");
		jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(new File("SoftwareInfo.txt"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jMenu6.add(jMenuItem11);

		jMenuItem12.setText("	导出jpg图像	 ");
		jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if (inFilePath == null)
					JOptionPane.showMessageDialog(null, "请先打开文件！");
				else {
					String jpg = "write image '?FILEROOT?.jpg'";
					jmolPanel2.viewer.evalString(jpg);
				}
			}
		});
		jMenu5.add(jMenuItem12);

		jMenuItem13.setText("	导出png图像	 ");
		jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if (inFilePath == null)
					JOptionPane.showMessageDialog(null, "请先打开文件！");
				else {
					String png = "write image '?FILEROOT?.png'";
					jmolPanel2.viewer.evalString(png);
				}
			}
		});
		jMenu5.add(jMenuItem13);
		/**
		 * *******************set the molecule
		 * surface************************************ 2014-07-14
		 **/
		jMenuItem26.setText("	Dot Surface	");
		jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "dots on";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu7.add(jMenuItem26);

		jMenuItem27.setText("	van der Waals Surface	");
		jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "isosurface delete resolution 0 solvent 0 translucent";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu7.add(jMenuItem27);

		jMenuItem28.setText("	Molecular Surface	");
		jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "isosurface delete resolution 0 molecular translucent";
				jmolPanel2.viewer.evalStringQuiet(controller);
			}
		});
		jMenu7.add(jMenuItem28);

		jMenuItem29.setText("	Solvent Surface(1.4-Angstrom probe)	");
		jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "isosurface delete resolution 0 solvent 1.4 translucent";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu7.add(jMenuItem29);

		jMenuItem30
				.setText("	 Solvent-Accessible Surface(VDW + 1.4 Angstrom)	");
		jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "isosurface delete resolution 0 sasurface 1.4 translucent";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu7.add(jMenuItem30);

		jMenuItem31.setText("	Molcular Electrostatic Potential	");
		jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "isosurface delete resolution 0 vdw color range all map MEP translucent";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu7.add(jMenuItem31);

		jMenuItem32.setText("	Make Opaque	");
		jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "mo opaque;isosurface opaque";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu7.add(jMenuItem32);

		jMenuItem33.setText("	Make Translucent	");
		jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "mo translucent;isosurface translucent";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu7.add(jMenuItem33);

		jMenuItem34.setText("	Off	");
		jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "mo delete;isosurface delete;select *;dots off";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu7.add(jMenuItem34);

		jMenuItem16.setText("	White	");
		jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("white");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem16);

		jMenuItem17.setText("	Black	");
		jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("black");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem17);

		jMenuItem18.setText("	Red	");
		jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("red");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem18);

		jMenuItem19.setText("	Orange	");
		jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("orange");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem19);

		jMenuItem20.setText("	Yellow	");
		jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("yellow");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem20);

		jMenuItem21.setText("	Green	");
		jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("green");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem21);

		jMenuItem22.setText("	Cyan	");
		jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("cyan");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem22);

		jMenuItem23.setText("	Blue	");
		jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("blue");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem23);

		jMenuItem24.setText("	Indigo	");
		jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("indigo");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem24);

		jMenuItem25.setText("	Violet	");
		jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmolPanel2.viewer.setColorBackground("violet");
				centerJPanel.updateUI();
			}
		});
		jMenu8.add(jMenuItem25);

		jMenuItem35.setText("	On	");
		jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "spin on";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu9.add(jMenuItem35);

		jMenuItem36.setText("	Off	");
		jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "spin off";
				jmolPanel2.viewer.evalString(controller);
			}
		});
		jMenu9.add(jMenuItem36);

		jMenuItem37.setText("	Reset	");
		jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String controller = "spin off";
				jmolPanel2.viewer.evalString(controller);
				String controller1 = "if (showBoundBox or showUnitcell) {moveto 1.0 front;moveto 2.0 back;delay 1} else {boundbox on;moveto 1.0 front;moveto 2.0 back;delay 1;boundbox off}";
				jmolPanel2.viewer.evalString(controller1);
			}
		});
		jMenu9.add(jMenuItem37);

		iconPanel = new JPanel();
		jButton4 = new JButton();
		jButton5 = new JButton();
		jButton6 = new JButton();
		jButton7 = new JButton();
		jButton8 = new JButton();
		jButton9 = new JButton();
		jLabel13 = new JLabel();
		jLabel14 = new JLabel();
		jLabel15 = new JLabel();
		jLabel16 = new JLabel();
		jLabel17 = new JLabel();
		jLabel18 = new JLabel();
		jLabel19 = new JLabel();
		jLabel20 = new JLabel();
		jLabel21 = new JLabel();
		jLabel22 = new JLabel();
		jLabel23 = new JLabel();
		jLabel24 = new JLabel();
		jLabel25 = new JLabel();
		jLabel26 = new JLabel();
		jLabel27 = new JLabel();
		jLabel28 = new JLabel();
		jLabel29 = new JLabel();
		jLabel30 = new JLabel();
		jLabel31 = new JLabel();
		jLabel32 = new JLabel();
		jLabel33 = new JLabel();
		jLabel34 = new JLabel();
		jLabel35 = new JLabel();
		jLabel36 = new JLabel();
		iconPanel.setLayout(new GridLayout(1, 6, 2, 2));
		iconPanel.setBackground(Color.darkGray);
		ImageIcon image1 = new ImageIcon(picturePath + "/create.png");
		image1.setImage(image1.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		jButton4.setContentAreaFilled(false); // 设置按钮没有填充效果
		jButton4.setBorder(null); // 设置按钮不显示边框
		jButton4.setIcon(image1);
		jButton4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标经过
				Border border = new LineBorder(Color.black, 1);
				jButton4.setBorder(border);
				jButton4.setToolTipText("新建分子编辑");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开
				jButton4.setBorder(null);
			}
		});
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CreateMolecule createMolecule = new CreateMolecule();
				createMolecule.setVisible(true);
				createMolecule.startPaintingThread();
			}
		});

		ImageIcon image2 = new ImageIcon(picturePath + "/create-2dimension.png");
		image2.setImage(image2.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		jButton5.setContentAreaFilled(false); // 设置按钮没有填充效果
		jButton5.setBorder(null); // 设置按钮不显示边框
		jButton5.setIcon(image2);

		jButton5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标经过
				Border border = new LineBorder(Color.black, 1);
				jButton5.setBorder(border);
				jButton5.setToolTipText("新建二维分子编辑");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开
				jButton5.setBorder(null);
			}
		});
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JcpUI jcpui = new JcpUI();
				jcpui.setVisible(true);

			}
		});

		ImageIcon image3 = new ImageIcon(picturePath + "/openfile.png");
		image3.setImage(image3.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		jButton6.setContentAreaFilled(false); // 设置按钮没有填充效果
		jButton6.setBorder(null); // 设置按钮不显示边框
		jButton6.setIcon(image3);

		jButton6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标经过
				Border border = new LineBorder(Color.black, 1);
				jButton6.setBorder(border);
				jButton6.setToolTipText("打开文件");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开
				jButton6.setBorder(null);
			}
		});
		jButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser file = new JFileChooser(
						"D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
				file.showOpenDialog(null);
				file.setDialogTitle("请选择要打开的文件...");
				inFilePath = file.getSelectedFile().getAbsolutePath();
				jmolPanel2.viewer.openFile(inFilePath);

				jTextField1.setText(inFilePath);
			}
		});

		ImageIcon image4 = new ImageIcon(picturePath + "/save.png");
		image4.setImage(image4.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		jButton7.setContentAreaFilled(false); // 设置按钮没有填充效果
		jButton7.setBorder(null); // 设置按钮不显示边框
		jButton7.setIcon(image4);

		jButton7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标经过
				Border border = new LineBorder(Color.black, 1);
				jButton7.setBorder(border);
				jButton7.setToolTipText("保存文件");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开
				jButton7.setBorder(null);

			}
		});
		ImageIcon image5 = new ImageIcon(picturePath + "/conversion.png");
		image5.setImage(image5.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		jButton8.setContentAreaFilled(false); // 设置按钮没有填充效果
		jButton8.setBorder(null); // 设置按钮不显示边框
		jButton8.setIcon(image5);
		jButton8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标经过
				Border border = new LineBorder(Color.black, 1);
				jButton8.setBorder(border);
				jButton8.setToolTipText("格式转换");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开
				jButton8.setBorder(null);

			}
		});
		jButton8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new FormatConvUI();
				// formatconv.setVisible(true);
			}
		});
		ImageIcon image6 = new ImageIcon(picturePath + "/search.png");
		image6.setImage(image6.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		jButton9.setContentAreaFilled(false); // 设置按钮没有填充效果
		jButton9.setBorder(null); // 设置按钮不显示边框
		jButton9.setIcon(image6);
		jButton9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标经过
				Border border = new LineBorder(Color.black, 1);
				jButton9.setBorder(border);
				jButton9.setToolTipText("相似性比对");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开
				jButton9.setBorder(null);
			}
		});

		jButton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// similarityStart start = new similarityStart();
				// start.setVisible(true);
			}
		});
		iconPanel.add(jButton4);
		iconPanel.add(jButton5);
		iconPanel.add(jButton6);
		iconPanel.add(jButton7);
		iconPanel.add(jButton8);
		iconPanel.add(jButton9);
		iconPanel.add(jLabel13);
		iconPanel.add(jLabel14);
		iconPanel.add(jLabel15);
		iconPanel.add(jLabel16);
		iconPanel.add(jLabel17);
		iconPanel.add(jLabel18);
		iconPanel.add(jLabel19);
		iconPanel.add(jLabel20);
		iconPanel.add(jLabel21);
		iconPanel.add(jLabel22);
		iconPanel.add(jLabel23);
		iconPanel.add(jLabel24);
		iconPanel.add(jLabel25);
		iconPanel.add(jLabel26);
		iconPanel.add(jLabel27);
		iconPanel.add(jLabel28);
		iconPanel.add(jLabel29);
		iconPanel.add(jLabel30);
		iconPanel.add(jLabel31);
		iconPanel.add(jLabel32);
		iconPanel.add(jLabel33);
		iconPanel.add(jLabel34);
		iconPanel.add(jLabel35);
		iconPanel.add(jLabel36);

		northJPanel.add(jMenuBar1);
		northJPanel.add(iconPanel);

		/*******************************************
		 * WestPanel************************************* To carry on the
		 * functions of molecular screening 2014-07-14
		 * *****************************************Wenhao
		 * Wei************************************
		 */
		usermethod = 1;
		westJPanel.setLayout(new GridLayout());
		jTabbedPane1 = new JTabbedPane();
		westchildPanel = new JPanel();
		jlabel1 = new JLabel("Smilarity Method:");
		jLabel1 = new JLabel("(Note:File size must less than 10MB!)");
		jButton11 = new JButton("Export"); // ****************export the result
		north = new JPanel(new GridLayout());
		center = new JPanel(new GridLayout());
		south = new JPanel();
		jSeparator1 = new JSeparator();
		jPanel1 = new JPanel(new GridLayout(5, 1, 10, 10));
		panel1 = new JPanel(new GridLayout(1, 2, 0, 10));
		panel2 = new JPanel(new GridLayout(1, 2, 0, 10));
		panel3 = new JPanel(new GridLayout(1, 2, 0, 10));
		jPanel2 = new JPanel(new GridLayout(1, 1, 0, 10));
		jPanel1.add(panel1);
		jPanel1.add(panel2);
		jPanel1.add(panel3);
		jPanel1.add(jPanel2);
		jPanel1.add(jSeparator1);
		jPanel3 = new JPanel(new GridLayout(5, 1, 10, 10)); // upload database
															// panel
		jPanel4 = new JPanel(new GridLayout(1, 2, 70, 10));
		jPanel5 = new JPanel(new GridLayout(5, 1, 10, 10)); // chemmapper
															// database panel
		jPanel6 = new JPanel(new GridLayout(1, 2, 30, 10));
		jPanel7 = new JPanel(new GridLayout(1, 2, 70, 10));
		checkbox = new JCheckBox("Gernerated Conformers");
		jLabel4 = new JLabel("Input Molecule:");
		jtextField3 = new JTextField();
		jtextField3.setEditable(false);
		jLabel2 = new JLabel("Similarity Threshold:");
		jLabel3 = new JLabel("Max Hits:");
		jComboBox1 = new JComboBox();
		jComboBox2 = new JComboBox();
		jComboBox3 = new JComboBox();
		jComboBox4 = new JComboBox();
		jComboBox5 = new JComboBox();

		jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "0.8",
				"1.0", "1.2", "1.5", "1.8" }));
		jComboBox1.setSelectedItem("1.2");
		threshold = "1.2";
		jComboBox1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					threshold = (String) jComboBox1.getSelectedItem();
				}
			}
		});
		jtextField1 = new JTextField(); // screen number
		jtextField1.setText("1000");
		panel1.add(jLabel4);
		panel1.add(jtextField3);
		panel2.add(jLabel2);
		panel2.add(jComboBox1);
		panel3.add(jLabel3);
		panel3.add(jtextField1);
		jrButton1 = new JRadioButton("Bioactivity Database");
		jrButton2 = new JRadioButton("Compound Datatbase:");
		jrButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usermethod = 2;
				jComboBox2.setEnabled(true);
				jComboBox3.setEnabled(false);
			}
		});
		jrButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usermethod = 3;
				jComboBox3.setEnabled(true);
				jComboBox2.setEnabled(false);
			}
		});
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(jrButton1);
		bg1.add(jrButton2);
		jtextField2 = new JTextField(); // user upload database
		button1 = new JButton("Browse");
		button1.addActionListener(new ActionListener() { // 打开数据库文件
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser(
						"D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
				file.setMultiSelectionEnabled(false);
				int result = file.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					Filepath = file.getSelectedFile().getAbsolutePath();
					File file1 = new File(Filepath);
					jtextField2.setText(file1.getName()); // 显示选择的数据库名
					DataBase = Filepath;
				}

				// System.out.println(inFormat);

			}
		});
		jPanel2.add(jComboBox5);
		jPanel4.add(jtextField2);
		jPanel4.add(button1);
		jPanel3.add(jPanel4);
		jPanel3.add(checkbox);
		jPanel3.add(jLabel1);

		jComboBox2
				.setModel(new DefaultComboBoxModel(new String[] { "Choose...",
						"DrugBank", "ChEMBL", "BindingDB", "KEGG", "PDB" }));
		jComboBox2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model1 = (String) jComboBox2.getSelectedItem();
					model = 0;
				}
			}
		}); // 待添加2013.12.05
		jComboBox2.setEnabled(false);
		jComboBox3.setModel(new DefaultComboBoxModel(new String[] {
				"Choose...", "MayBridge", "Specs", "ZINC", "NCI" }));
		jComboBox3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model1 = (String) jComboBox3.getSelectedItem();
					model = 1;
				}
			}
		});
		jComboBox3.setEnabled(false);
		jComboBox4.setModel(new DefaultComboBoxModel(new String[] {
				"Choose...", "SHAFTS", "USR" }));
		jComboBox4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (((String) jComboBox4.getSelectedItem())
							.equals("SHAFTS"))
						model2 = "FeatureAlign";
					else if (((String) jComboBox4.getSelectedItem())
							.equals("USR"))
						model2 = "ShapeFilter";
				}
			}
		});
		jComboBox5.setModel(new DefaultComboBoxModel(new String[] {
				"Upload a datatbase", "Use ChemMapper datatbase" }));
		jComboBox5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (((String) jComboBox5.getSelectedItem())
							.equals("Upload a datatbase")) {
						usermethod = 1;
						center.removeAll();
						center.add(jPanel3);
						jPanel3.updateUI();
					} else if (((String) jComboBox5.getSelectedItem())
							.equals("Use ChemMapper datatbase")) {

						center.removeAll();
						center.add(jPanel5);
						jPanel5.updateUI();
					}
				}
			}
		});
		jPanel6.add(jlabel1);
		jPanel6.add(jComboBox4);
		jPanel5.add(jPanel6);
		jPanel5.add(jrButton1);
		jPanel5.add(jComboBox2);
		jPanel5.add(jrButton2);
		jPanel5.add(jComboBox3);
		button2 = new JButton("Start"); // start screen
		jPanel7.add(jButton11);
		jPanel7.add(button2);
		north.add(jPanel1);
		// north.add(jPanel2);
		center.add(jPanel3);
		south.add(jPanel7, BorderLayout.SOUTH);

		westchildPanel.add(north, BorderLayout.NORTH);
		westchildPanel.add(center, BorderLayout.CENTER);
		westchildPanel.add(south, BorderLayout.SOUTH);
		jTabbedPane1.add("Similarity", westchildPanel);
		westJPanel.add(jTabbedPane1);

		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String st = jtextField3.getText();
				outputNum = jtextField1.getText();
				System.out.println(threshold);
				if (st.isEmpty())
					JOptionPane.showMessageDialog(null,
							"Please select the input molecule！");
				else {
					switch (usermethod) {
					case 1:
						if (DataBase == null)
							JOptionPane.showMessageDialog(null,
									"Please select the input molecule！");
						else {
							NewPath = localworkPath + "Job";
							Shafts sf = new Shafts();
							sf.shaftinit(NewPath, inFilePath, DataBase,
									outputNum, threshold);
							String workid = sf.getworkid();
							addlocalnode(workid);
							FilePath1 = localworkPath + workid + "\\";
							String path = NewPath + "\\Result.list";
							data = sf.getdata();
							Vector columnNames = IV.getcolumn();
							southJPanel.removeAll();
							southJPanel.updateUI();
							jTable1 = new JTable();
							jTable1.setDefaultRenderer(String.class,
									new MyCellRenderer());
							CheckTableModle tableModel = new CheckTableModle(
									data, columnNames);
							jTable1.setModel(tableModel);
							jTable1.getTableHeader().setDefaultRenderer(
									new CheckHeaderCellRenderer(jTable1));
							//jTable1.addMouseListener(new ShowMol());
							JScrollPane jScrollPane1 = new JScrollPane(jTable1);
							southJPanel.add(jScrollPane1);
							southJPanel.updateUI();
							flag = 0;
							ifshow = new Hashtable<String, Integer>();
						}
						break;
					case 2:
						break;
					case 3:
						HttpInvokerClient HIC = new HttpInvokerClient();
						System.out.println(model + "***" + model2 + "***"
								+ model1);
						String ID = HIC.getid(inFilePath, model, outputNum,
								model2, model1, threshold);
						addnetnode(ID);
						break;
					default:
						break;

					}

				}
			}
		});
		/**********************************************
		 * EastJPanel********************************* The working record place
		 * 2014-07-14
		 */

		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jtextField6 = new JTextField();
		jButton2 = new javax.swing.JButton();
		jButton10 = new javax.swing.JButton();
		jButton12 = new javax.swing.JButton();
		if (IsUse != 1) {
			jButton12.setEnabled(false);
		}
		jTextField1.setOpaque(false);
		jButton2.addActionListener(new ActionListener() { // 打开文件按钮
			public void actionPerformed(ActionEvent e) {

				JFileChooser file = new JFileChooser(
						"D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
				file.showOpenDialog(null);
				file.setDialogTitle("请选择要打开的文件...");
				inFilePath = file.getSelectedFile().getAbsolutePath();
				jmolPanel2.viewer.openFile(inFilePath);
				String bandian = "dots on";
				jmolPanel2.viewer.evalString(bandian);
				File file11 = new File(inFilePath);
				jTextField2.setText(file11.getName());
				jTextField1.setText(inFilePath);
			}
		});
		// *********************************添加导出被选中数据2013.12.08************************************//

		jButton11.addActionListener(new ActionListener() { // 导出对比结果
					public void actionPerformed(ActionEvent e) {
						new ExportXlsUI(jTable1);
					}
				});

		jButton12.addActionListener(new ActionListener() { // 按jobID查询结果
					public void actionPerformed(ActionEvent e) {
						new Thread() {
							public void run() {
								// String FilePath = null;
								String gm = null;
								gm = jtextField6.getText();
								if (gm.isEmpty())
									JOptionPane.showMessageDialog(null,
											"请先输入查询的jobID号！");
								else {
									// FilePath =
									// "E:\\MyOffice\\Eclipse\\workspace\\ChemMapper\\workhome\\download";
									FilePath1 = downloadPath + gm + "\\";
									String name = new ListFinder(FilePath1)
											.GetList(FilePath1);
									if (name == null) {
										SFTPConnection sftp = new SFTPConnection();
										sftp.connect();
										sftp.batchDownLoadFile(gm, FilePath1);
										sftp.disconnect();
										name = new ListFinder(FilePath1)
												.GetList(FilePath1);
									}
									southJPanel.removeAll();
									southJPanel.updateUI();
									String path = FilePath1 + name;
									// System.out.println("后缀名路径为：" + path);
									data = IV.getdata(path,null);
									Vector columnNames = IV.getcolumn();
									jTable1 = new JTable();
									jTable1.setDefaultRenderer(String.class,
											new MyCellRenderer());
									CheckTableModle tableModel = new CheckTableModle(
											data, columnNames);
									jTable1.setModel(tableModel);
									jTable1.getTableHeader()
											.setDefaultRenderer(
													new CheckHeaderCellRenderer(
															jTable1));
									//jTable1.addMouseListener(new ShowMol());
									JScrollPane jScrollPane1 = new JScrollPane(
											jTable1);
									southJPanel.add(jScrollPane1);
									southJPanel.updateUI();
									flag = 0;
									// ifshow.clear();
									ifshow = new Hashtable<String, Integer>();
								}
							}
						}.start();
					}
				});

		eastJPanel.setLayout(new GridLayout(2, 1, 10, 10));
		root1 = new DefaultMutableTreeNode("LocalJob");
		root2 = new DefaultMutableTreeNode("NetWork");
		LocalTree = new JTree(root1);
		LocalTree.addMouseListener(new LocalworkMouseHandle());
		NetTree = new JTree(root2);
		NetTree.addMouseListener(new NetworkMouseHandle());
		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout(new GridLayout(1, 2));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(LocalTree);
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setViewportView(NetTree);
		jPanel3.add(scrollPane);
		jPanel3.add(scrollPane1);
		eastJPanel.add(jPanel3);
		JPanel jPanel4 = new JPanel(new GridLayout(6, 1, 10, 10));
		// eastJPanel.add(jPanel4);
		JPanel jpanel44 = new JPanel(new GridLayout(1, 2, 50, 30));
		JPanel jPanel55 = new JPanel(new GridLayout(1, 2, 100, 30));
		JPanel jPanel56 = new JPanel(new GridLayout(1, 2, 100, 30));
		jLabel9.setText("下载作业（请输入jobID号）：");
		jPanel4.add(jLabel9);
		jpanel44.add(jtextField6);
		jButton12.setText("下载");
		// jButton12.setEnabled(false);
		jpanel44.add(jButton12);
		jPanel4.add(jpanel44);
		jPanel55.add(jLabel7);
		jPanel55.add(jButton2);
		jLabel5.setText(" ");
		jLabel6.setText(" ");
		jLabel7.setText(" ");
		jLabel8.setText(" ");
		jPanel56.add(jLabel8);
		// jPanel56.add(jButton11);
		jButton2.setText("打开文件");
		jButton10.setText("更新");
		// jButton11.setText("导出结果");
		jPanel4.add(jLabel6);
		jPanel4.add(jTextField1);
		jPanel4.add(jPanel55);
		jPanel4.add(jPanel56);

		// southPanel

		southJPanel.setLayout(new GridLayout());
		String path = null;
		IV = new InitVector();
		// data = new Vector();
		data = IV.getdata(path,null);
		Vector columnNames = IV.getcolumn();
		CheckTableModle tableModel = new CheckTableModle(data, columnNames);
		jTable1 = new JTable();
		jTable1.setDefaultRenderer(String.class, new MyCellRenderer());
		jTable1.setModel(tableModel);
		jTable1.getTableHeader().setDefaultRenderer(
				new CheckHeaderCellRenderer(jTable1));
		TableColumn column = jTable1.getColumnModel().getColumn(5);
		column.setPreferredWidth(100);
		JScrollPane jScrollPane1 = new JScrollPane(jTable1);
		southJPanel.add(jScrollPane1);

		/**
		 * centerpanel
		 */

		centerJPanel.setLayout(new GridLayout());
		centerJPanel.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 5,
				Color.LIGHT_GRAY));
		jmolPanel2 = new JmolPanel1();
		centerJPanel.add(jmolPanel2);
		// jmolPanel.viewer.openFile(CreateMolecule.RENDER_FILE_NAME);
		jmolPanel2.setPreferredSize(new Dimension(400, 300));
	}

	public MainUI() {

		// new Thread(){
		String path;
		// public void run(){
		PropertyConfig pc = new PropertyConfig();
		ArrayList<String> a = pc.readProperties();
		if (a == null) {
			new WorkpathSetUI();
			// path = pc.getProperty("workpath");
		} else if ((pc.getProperty("showagain")).equals("NO")) {
			new WorkpathSetUI();
			// path = pc.getProperty("workpath");
		}
		// else
		ArrayList<String> a1 = pc.readProperties();

		// path = pc1.getProperty("workpath");
		path = a1.get(1);
		File file = new File(path);
		if (!file.exists())
			file.mkdir();
		localworkPath = path + "\\localwork\\";
		System.out.println(localworkPath);
		networkPath = path + "\\network\\";
		downloadPath = "D:\\MyOffice\\Github\\SHAFTS\\ChemMapper\\workhome\\download\\";
		picturePath = System.getProperty("user.dir") + "\\Pictures";
		CheckUserStatus CUS = new CheckUserStatus();
		IsUse = 1;// CUS.getuserstatus();
		getContentPane().setLayout(new BorderLayout());
		setLocation(5, 5);
		setSize(1200, 700); // 1200,700
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SHAFTS");
		initComponents();
		initwork();
		// data.removeAllElements();
		LA.close();
		// }
		// }.start();
	}

	/**
	 * reboot the system
	 */
	public void Reboot() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// JOptionPane.showMessageDialog( null,"正在重启...");
				frame.setVisible(false);
				frame.dispose();
				// f.setVisible(true);
				frame = null;
				MainUI.main(null); // 为了演示特意创建了一个参数
			}

			private JFrame frame = MainUI.this;
		});
	}

	private String threshold; // 分子相似性阈值
	private String outputNum; // 最大输出结果分子数
	private String InputFilepath = null;
	private String NewPath;
	private String localworkPath;
	private String networkPath;
	private String downloadPath;
	private String nodeName = null;
	private String oldName = null;
	private String picturePath = null;
	// private LaunchAnimation LA;
	// private String newName = null;
	private JTableHeader header;
	private int flag = 0; // 分子标记
	private int IsRename = 0; // 是否重命名标记
	private int IsUse = 0;
	private int usermethod = 0;
	private Hashtable<String, Integer> ifshow;
	private TreeNode treenode;
	private JPanel eastJPanel;
	private JPanel westchildPanel;
	private JPanel westJPanel;
	private JPanel northJPanel;
	private JPanel southJPanel;
	private JPanel centerJPanel;
	private JPanel iconPanel;
	private JPanel north;
	private JPanel center;
	private JPanel south;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JPanel jPanel6;
	private JPanel jPanel7;
	private JPanel jPanel8;
	private JComboBox jComboBox1;
	private JComboBox jComboBox2;
	private JComboBox jComboBox3;
	private JComboBox jComboBox4;
	private JComboBox jComboBox5;
	private JCheckBox checkbox;
	private JTabbedPane jtabbedPane1;
	private JTextField jtextField1;
	private JTextField jtextField2;
	private JTextField jtextField3;
	private JTextField jtextField4;
	private JTextField jtextField5;
	private JTextField jtextField6;
	private JLabel jlabel1;
	private JLabel jlabel2;
	private JLabel jlabel3;
	private JLabel jlabel4;
	private JLabel jlabel5;
	private JLabel jlabel6;
	private JLabel jlabel7;
	private JLabel jlabel8;
	private JLabel jlabel9;
	private JLabel jlabel10;
	private JLabel jlabel11;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private JLabel jLabel14;
	private JLabel jLabel15;
	private JLabel jLabel16;
	private JLabel jLabel17;
	private JLabel jLabel18;
	private JLabel jLabel19;
	private JLabel jLabel20;
	private JLabel jLabel21;
	private JLabel jLabel22;
	private JLabel jLabel23;
	private JLabel jLabel24;
	private JLabel jLabel25;
	private JLabel jLabel26;
	private JLabel jLabel27;
	private JLabel jLabel28;
	private JLabel jLabel29;
	private JLabel jLabel30;
	private JLabel jLabel31;
	private JLabel jLabel32;
	private JLabel jLabel33;
	private JLabel jLabel34;
	private JLabel jLabel35;
	private JLabel jLabel36;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JRadioButton jrButton1;
	private JRadioButton jrButton2;
	private JRadioButton jrButton3;
	private JRadioButton jrButton4;
	private JMenuBar jMenuBar1;
	private JMenu jMenu1;
	private JMenu jMenu2;
	private JMenu jMenu3;
	private JMenu jMenu4;
	private JMenu jMenu5;
	private JMenu jMenu6;
	private JMenu jMenu7;
	private JMenu jMenu8;
	private JMenu jMenu9;
	private JMenu jmenu;
	private JMenuItem jmenuitem;
	private JMenuItem jMenuItem1;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem3;
	private JMenuItem jMenuItem4;
	private JMenuItem jMenuItem5;
	private JMenuItem jMenuItem6;
	private JMenuItem jMenuItem7;
	private JMenuItem jMenuItem8;
	private JMenuItem jMenuItem9;
	private JMenuItem jMenuItem10;
	private JMenuItem jMenuItem11;
	private JMenuItem jMenuItem12;
	private JMenuItem jMenuItem13;
	private JMenuItem jMenuItem14;
	private JMenuItem jMenuItem15;
	private JMenuItem jMenuItem16;
	private JMenuItem jMenuItem17;
	private JMenuItem jMenuItem18;
	private JMenuItem jMenuItem19;
	private JMenuItem jMenuItem20;
	private JMenuItem jMenuItem21;
	private JMenuItem jMenuItem22;
	private JMenuItem jMenuItem23;
	private JMenuItem jMenuItem24;
	private JMenuItem jMenuItem25;
	private JMenuItem jMenuItem26;
	private JMenuItem jMenuItem27;
	private JMenuItem jMenuItem28;
	private JMenuItem jMenuItem29;
	private JMenuItem jMenuItem30;
	private JMenuItem jMenuItem31;
	private JMenuItem jMenuItem32;
	private JMenuItem jMenuItem33;
	private JMenuItem jMenuItem34;
	private JMenuItem jMenuItem35;
	private JMenuItem jMenuItem36;
	private JMenuItem jMenuItem37;
	private JTabbedPane jTabbedPane1;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JSeparator jSeparator3;
	private JSeparator jSeparator4;

	private JTextField jTextField1;
	private JTextField jTextField2;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	private JButton jButton6;
	private JButton jButton7;
	private JButton jButton8;
	private JButton jButton9;
	private JButton jButton10;
	private JButton jButton11;
	private JButton jButton12;
	private Vector data;
	private JTree LocalTree;
	private JTree NetTree;
	private JPopupMenu pm1;
	private JPopupMenu pm2;
	private DefaultMutableTreeNode root1;
	private DefaultMutableTreeNode root2;
	private DefaultMutableTreeNode nowNode;
	private InitVector IV;
	private File file2;
	private File file3;
	private String FilePath1 = null;
	public static CreateMolecule createMolecule;
	public static JcpUI jcpui;
	private JTable jTable1;
	private JmolPanel1 jmolPanel2;
}
