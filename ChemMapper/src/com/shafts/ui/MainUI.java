package com.shafts.ui;

import java.awt.*; //******

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ecust.remote.chemmapper.ChemmapperService;
import org.ecust.remote.chemmapper.model.ChemmapperServiceModel;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.*;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;
import org.openbabel.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.shafts.application.CreateMolecule;
import com.shafts.application.FormatConv;
import com.shafts.application.Shafts;
import com.shafts.utils.CheckUserStatus;
//import com.shafts.application.CreateMolecule.RenderThread;
import com.shafts.utils.JmolPanel1;
import com.shafts.utils.JCPPanel1;
//import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import com.shafts.utils.PropertyConfig;
import com.shafts.utils.ShowGif;
import com.shafts.utils.TimerMessageDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

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
					JDialog.setDefaultLookAndFeelDecorated(true);

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
		File[] listdir1 = f1.listFiles();
		File[] listdir2 = f2.listFiles();
		for (int i = 0; i < listdir1.length; i++) {
			String nodename = listdir1[i].getName();
			addlocalnode(nodename);
		}
		for (int i = 0; i < listdir2.length; i++) {
			String nodename = listdir2[i].getName();
			addnetnode(nodename);
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
		class ShowMol extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {// 单击鼠标左键
					if (e.getClickCount() == 2) {
						String stringName[] = new String[20];
						int colummCount = jTable1.getModel().getColumnCount();// 列数
						int rowCount = jTable1.getModel().getRowCount();// 行数
						for (int i = 1; i < colummCount; i++)
							stringName[i] = jTable1.getModel()
									.getValueAt(jTable1.getSelectedRow(), i)
									.toString();
						show3Dname = stringName[2];
						String path1 = FilePath1 + show3Dname + ".mol2";
						File mol2file = new File(path1);
						if (!mol2file.exists()) {
							show3Dname = stringName[1];
						}
						boolean bl = (boolean) jTable1.getModel().getValueAt(
								jTable1.getSelectedRow(), 0);
						String path2 = FilePath1 + show3Dname + ".mol2";
						if (bl == false) {
							jTable1.getModel().setValueAt(true,
									jTable1.getSelectedRow(), 0);
							flag++;
							ifshow.put(show3Dname, new Integer(flag));
							String controller1 = "load APPEND " + "\"" + path2
									+ "\"" + " ;frame*";
							String controller2 = "dots on";
							jmolPanel2.viewer.evalString(controller1);
							jmolPanel2.viewer.evalString(controller2);
						} else {
							jTable1.getModel().setValueAt(false,
									jTable1.getSelectedRow(), 0);
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
		/**
		 * 二维分子编辑对应主界面显示同步
		 * 
		 * @author baoabo
		 *
		 */
		class RenderThread extends Thread {
			private long lastmodified = 0;
			private final int SLEEP_TIME = 2000;

			@SuppressWarnings("static-access")
			public void run() {
				file2 = new File(createMolecule.RENDER_FILE_NAME);
				jTextField1.setText(createMolecule.RENDER_FILE_NAME);
				while (true) {
					if (file2.exists() && file2.lastModified() > lastmodified) {
						jmolPanel2.viewer
								.openFile(CreateMolecule.RENDER_FILE_NAME);
						String bandian = "dots on";
						jmolPanel2.viewer.evalString(bandian);
						lastmodified = file2.lastModified();
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
				if (file2.exists())
					file2.delete();
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
				jTextField1.setText(createMolecule.RENDER_FILE_NAME);
				while (true) {
					if (file3.exists() && file3.lastModified() > lastmodified1) {
						jmolPanel2.viewer
								.openFile(CreateMolecule.RENDER_FILE_NAME);
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
					if (nodeName == null || nodeName == "网络任务")
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
							if (!nodeName.equals("网络任务")) {
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
								data = IV.getdata(path);
								Vector columnNames = IV.getcolumn();
								jTable1 = new JTable();
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
					if (nodeName == null || nodeName == "Local Job")
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
							if (!nodeName.equals("Local Job")) {
								FilePath1 = localworkPath + nodeName + "\\";
								String name = new ListFinder(FilePath1)
										.GetList(FilePath1);
								String path = FilePath1 + name;
								data = IV.getdata(path);
								Vector columnNames = IV.getcolumn();
								southJPanel.removeAll();
								jTable1 = new JTable();
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
				createMolecule = new CreateMolecule();
				createMolecule.setVisible(true);
				createMolecule.startPaintingThread();
				RenderThread rThread = new RenderThread();
				rThread.start();
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
		 * *******************set the molecule surface
		 * ***************************
		 * **/
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

				FormatConvUI formatconv = new FormatConvUI();
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

		/**
		 * westpanel
		 */
		westJPanel.setLayout(new GridLayout());
		jTabbedPane1 = new JTabbedPane(JTabbedPane.TOP);
		westJPanel.add(jTabbedPane1);
		westJPanel.setVisible(true);
		jPanel1 = new JPanel();
		jPanel1.setLayout(new GridLayout(9, 1, 20, 20));
		jPanel2 = new JPanel();
		jPanel2.setLayout(new GridLayout());

		// jmolPanel2 = new JmolPanel1();

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();
		jSeparator3 = new javax.swing.JSeparator();
		jSeparator4 = new javax.swing.JSeparator();
		jComboBox1 = new javax.swing.JComboBox();
		jComboBox2 = new javax.swing.JComboBox();
		jComboBox4 = new javax.swing.JComboBox();
		jButton3 = new javax.swing.JButton();
		Box hbox1 = Box.createHorizontalBox();
		hbox1.add(jButton3);
		jTextField2 = new javax.swing.JTextField();
		jTextField2.setOpaque(false);
		button2 = new JButton();
		/**
		 * 本地筛选模式
		 */
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String st = jTextField1.getText();
				if (st.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请选择对比源文件！");
				} else {
					outputNum = jtextField1.getText();
					threshold = jtextField2.getText();
					NewPath = "D:\\MyOffice\\Github\\SHAFTS\\ChemMapper\\workhome\\localwork\\Job";
					Shafts sf = new Shafts();
					sf.shaftinit(NewPath, inFilePath, DataBase, outputNum,
							threshold);
					String workid = sf.getworkid();
					addlocalnode(workid);
					FilePath1 = "D:\\MyOffice\\Github\\SHAFTS\\ChemMapper\\workhome\\localwork\\"
							+ workid + "\\";
					String path = NewPath + "\\Result.list";
					data = sf.getdata();
					Vector columnNames = IV.getcolumn();
					southJPanel.removeAll();
					southJPanel.updateUI();
					jTable1 = new JTable();
					CheckTableModle tableModel = new CheckTableModle(data,
							columnNames);
					jTable1.setModel(tableModel);
					jTable1.getTableHeader().setDefaultRenderer(
							new CheckHeaderCellRenderer(jTable1));
					jTable1.addMouseListener(new ShowMol());
					JScrollPane jScrollPane1 = new JScrollPane(jTable1);
					southJPanel.add(jScrollPane1);
					southJPanel.updateUI();
					flag = 0;
					ifshow = new Hashtable<String, Integer>();
				}
			}
		});

		// 选项卡一

		jTabbedPane1.addTab("	格式转换		", jPanel1);
		jLabel1.setText("3DMV 功能选择");
		jLabel2.setText("格式转换输入格式");
		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "请输入要进行转换的格式...", "mol", "CDK", "cml", "rxn",
						"smiles", "mol2" }));
		jComboBox1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String) jComboBox1.getSelectedItem();
					inFormat = s;
					System.out.println(inFormat);
				}
			}
		});
		jLabel12.setText("格式转换输出格式");
		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"请输入要进行导出的格式...", "mol2", "pdb", "cdk", "cml" }));
		jComboBox2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String) jComboBox2.getSelectedItem();
					outFormat = s;
					System.out.println(outFormat);
				}
			}
		});
		jButton3.setText("开始转换"); // ***********下
		jPanel1.add(jLabel1);
		jPanel1.add(jSeparator1);
		jPanel1.add(jLabel2);
		jPanel1.add(jComboBox1);
		jPanel1.add(jLabel12);
		jPanel1.add(jComboBox2);
		jPanel1.add(hbox1);
		/*
		 * jButton3.addActionListener(new ActionListener() { //开始转换按钮 public
		 * void actionPerformed(ActionEvent e) { if(inFilePath==null){
		 * JOptionPane.showMessageDialog( null,"错误！请选择文件！"); }
		 * 
		 * else{ JFileChooser fc=new
		 * JFileChooser("D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
		 * fc.setMultiSelectionEnabled(false); int
		 * result=fc.showSaveDialog(null); if
		 * (result==JFileChooser.APPROVE_OPTION){ File
		 * file=fc.getSelectedFile(); fileName=file.getName();
		 * 
		 * outFilePath=file.getAbsolutePath(); System.out.println(fileName); }
		 * 
		 * formatconv(); jTextField2.setText(outFilePath);
		 * 
		 * 
		 * }
		 * 
		 * } });
		 */
		/**
		 * 选项卡二： 数据库选择
		 */

		// 本地数据库

		jtabbedPane1 = new JTabbedPane(JTabbedPane.BOTTOM);
		panel1 = new JPanel(); // 本模块专用组件 每种10个（含预留）
		panel1.setLayout(new GridLayout(8, 2, 20, 20));
		jlabel1 = new JLabel();
		jlabel2 = new JLabel();
		jlabel3 = new JLabel();
		jlabel4 = new JLabel();
		button1 = new JButton();
		jtextField1 = new JTextField(); // 筛选个数
		jtextField2 = new JTextField(); // 分子阈值
		jtextField3 = new JTextField();
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		JLabel label4 = new JLabel();
		label1.setText("   ");
		label2.setText("   ");
		label3.setText("   ");
		label4.setText("   ");
		jlabel1.setText(" 筛选结果个数(<=1000):");
		jlabel1.setToolTipText("筛选结果最大为1000");
		jlabel2.setText(" 分子阈值(0~2):");
		jlabel2.setToolTipText("分子阈值在0到2之间");
		panel1.add(jlabel1);
		panel1.add(jtextField1);
		jtextField1.setText("1000");
		panel1.add(jlabel2);
		panel1.add(jtextField2);
		jtextField2.setText("0");
		jlabel3.setText(" 数据库：");
		jlabel4.setText("      ");
		button1.setText(" 浏览");
		button2.setText(" 开始对比");
		panel1.add(jlabel3);
		panel1.add(jlabel4);
		panel1.add(jtextField3); // 显示选择的本地数据库文件
		panel1.add(button1);
		panel1.add(label1);
		panel1.add(label2);
		panel1.add(label3);
		panel1.add(jSeparator2);
		panel1.add(label4);
		panel1.add(button2);
		button1.addActionListener(new ActionListener() { // 打开数据库文件
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser(
						"D:\\MyOffice\\Github\\SHAFTS\\ChemMapper");
				file.showOpenDialog(null);
				Filepath = file.getSelectedFile().getAbsolutePath();
				File file1 = new File(Filepath);
				jtextField3.setText(file1.getName()); // 显示选择的数据库名
				DataBase = Filepath;
				System.out.println(inFormat);

			}
		});

		// 网络数据库

		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		button3 = new JButton();
		button4 = new JButton();
		button5 = new JButton();
		jtextField4 = new JTextField();
		jtextField5 = new JTextField();
		jComboBox111 = new JComboBox();
		jComboBox222 = new JComboBox();
		jComboBox333 = new JComboBox();
		panel2.setLayout(new GridLayout(3, 1, 10, 10));
		panel3.setLayout(new GridLayout(3, 2, 10, 10));
		panel4.setLayout(new GridLayout(3, 1, 10, 10));
		panel5.setLayout(new GridLayout(3, 2, 10, 10));
		jlabel5 = new JLabel();
		jlabel6 = new JLabel();
		jlabel7 = new JLabel();
		jlabel8 = new JLabel();
		jlabel9 = new JLabel();
		jlabel10 = new JLabel();
		jlabel11 = new JLabel();
		jlabel5.setText("筛选结果个数(<=1000)：");
		jlabel5.setToolTipText("筛选结果最大为1000");
		jlabel6.setText("分子阈值(0~2)：");
		jlabel6.setToolTipText("分子阈值在0到2之间");
		jlabel7.setText("选择对比方法：");
		jComboBox111.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "请选择...", "SHAFTS", "USR" }));
		jComboBox111.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (((String) jComboBox111.getSelectedItem())
							.equals("SHAFTS"))
						model2 = "FeatureAlign";
					else if (((String) jComboBox111.getSelectedItem())
							.equals("USR"))
						model2 = "ShapeFilter";
				}
			}
		}); // 待添加2013.12.05
		panel3.add(jlabel5);
		panel3.add(jtextField4);
		panel3.add(jlabel6);
		panel3.add(jtextField5);
		panel3.add(jlabel7);
		panel3.add(jComboBox111);
		jlabel8.setText("请在任一模式中选择数据库（不可全选）：");
		panel4.add(jSeparator3);
		panel4.add(jlabel8);
		panel4.add(jSeparator4);
		jlabel9.setText("Target Navigator:");
		jlabel10.setText("Hit Explorer:");
		jComboBox222.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "请选择...", "DrugBank", "ChEMBL", "BindingDB",
						"KEGG", "PDB" }));
		jComboBox222.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					model1 = (String) jComboBox222.getSelectedItem();
					model = 0;
				}
			}
		}); // 待添加2013.12.05
		jComboBox333
				.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
						"请选择...", "MayBridge", "Specs", "ZINC", "NCI" }));
		jComboBox333.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					model1 = (String) jComboBox333.getSelectedItem();
					model = 1;
				}
			}
		}); // 待添加2013.12.05
		jlabel11.setText("    ");
		button3.setText("开始对比");
		panel5.add(jlabel9);
		panel5.add(jComboBox222);
		panel5.add(jlabel10);
		panel5.add(jComboBox333);
		panel5.add(jlabel11);
		panel5.add(button3);

		/**
		 * 网络筛选，提交作业并返回ID，将ID加入到作业树中
		 */
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jTextField2 != null) {
					HttpInvokerClient HIC = new HttpInvokerClient();
					String ID = HIC.getid(inFilePath, model,
							jtextField4.getText(), model2, model1,
							jtextField5.getText());
					addnetnode(ID);
				} else {
					JOptionPane.showMessageDialog(null, "请选择对比源文件！");
				}
				// node1 = new DefaultMutableTreeNode(ID);
				// root.add(node1);
				// tree.scrollPathToVisible(new TreePath(node1.getPath()));
				// tree.updateUI();
			}
		}); // 添加事件修改 2014.2.21

		panel2.add(panel3);
		panel2.add(panel4);
		panel2.add(panel5);
		// panel2.setEnabled(false);
		// panel2.setVisible(false);
		jtabbedPane1.add(" 本地数据库  ", panel1);
		jtabbedPane1.add(" 网络数据库  ", panel2);
		if (IsUse != 1) {
			jtabbedPane1.setEnabledAt(1, false);
		}
		jTabbedPane1.addTab("  数据库选择器    ", jtabbedPane1);

		/**
		 * eastJPanel
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
		jButton11 = new javax.swing.JButton();
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
						/*
						 * JFileChooser fc=new
						 * JFileChooser("D:\\MyOffice\\Github\\SHAFTS\\ChemMapper"
						 * ); //fc.setFileFilter(filter);
						 * fc.setMultiSelectionEnabled(false); int
						 * result=fc.showSaveDialog(null); if
						 * (result==JFileChooser.APPROVE_OPTION){ File
						 * file=fc.getSelectedFile(); //fileName=file.getName();
						 * outFilePath=file.getAbsolutePath();
						 * JOptionPane.showMessageDialog( null,"导出成功！");
						 * //System.out.println(fileName); } String
						 * excelFileName = outFilePath+".xls";
						 * 
						 * if(jTable1.getRowCount() == 1)
						 * JOptionPane.showMessageDialog( null,"没有可导出的对比结果！");
						 * else{ try{ HSSFWorkbook workbook=new HSSFWorkbook();
						 * HSSFSheet sheet=workbook.createSheet();
						 * workbook.setSheetName(0, "分子相似性对比结果"); HSSFCellStyle
						 * cellStyle=workbook.createCellStyle();
						 * cellStyle.setDataFormat
						 * (HSSFDataFormat.getBuiltinFormat("@")); HSSFRow
						 * row=sheet.createRow(0); HSSFCell cell1 =
						 * row.createCell(0); //第一列 HSSFCell cell2 =
						 * row.createCell(1); HSSFCell cell3 =
						 * row.createCell(2); HSSFCell cell4 =
						 * row.createCell(3); HSSFCell cell5 =
						 * row.createCell(4); HSSFCell cell6 =
						 * row.createCell(5); //定义单元格为字符串类型
						 * cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
						 * cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
						 * cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
						 * cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
						 * cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
						 * cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
						 * cell1.setCellValue("Rank");
						 * cell2.setCellValue("Name");
						 * cell3.setCellValue("HybridScore");
						 * cell4.setCellValue("ShapeScore");
						 * cell5.setCellValue("FeatureScore");
						 * cell6.setCellValue("Query");
						 * 
						 * int j = 1; // j为导出的excel行数 int k; //k为列 for(int
						 * i=1;i<jTable1.getRowCount();i++){ //i为表的行数 第一行为表头
						 * if((boolean) jTable1.getValueAt(i,0)) { row =
						 * sheet.createRow(j); for(k=1;k<7;k++) { HSSFCell
						 * cell=row.createCell(k-1); String s =
						 * (String)jTable1.getValueAt(i,k); //设置单元格格式
						 * cell.setCellStyle(cellStyle);
						 * cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						 * cell.setCellValue(s); } j++; } } //建立一个文件输出流
						 * FileOutputStream fOut=new
						 * FileOutputStream(excelFileName); //把相应的Excel工作薄存盘
						 * workbook.write(fOut); //操作结束，关闭文件 fOut.flush();
						 * fOut.close(); //JOptionPane.showMessageDialog(
						 * null,"导出成功！"); } catch (Throwable t) {
						 * t.printStackTrace(); } }
						 */
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
									data = IV.getdata(path);
									Vector columnNames = IV.getcolumn();
									jTable1 = new JTable();
									CheckTableModle tableModel = new CheckTableModle(
											data, columnNames);
									jTable1.setModel(tableModel);
									jTable1.getTableHeader()
											.setDefaultRenderer(
													new CheckHeaderCellRenderer(
															jTable1));
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
				});

		eastJPanel.setLayout(new GridLayout(2, 1, 10, 10));
		root1 = new DefaultMutableTreeNode("本地任务");
		root2 = new DefaultMutableTreeNode("网络任务");
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
		// eastJPanel.add(jPanel3);
		// jp = new JobPanel();
		// jp.initwork();
		// eastJPanel.add(jp);
		eastJPanel.add(jPanel3);
		JPanel jPanel4 = new JPanel(new GridLayout(6, 1, 10, 10));
		eastJPanel.add(jPanel4);
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
		jPanel56.add(jButton11);
		jButton2.setText("打开文件");
		jButton10.setText("更新");
		jButton11.setText("导出结果");
		jPanel4.add(jLabel6);
		jPanel4.add(jTextField1);
		jPanel4.add(jPanel55);
		jPanel4.add(jPanel56);

		// southPanel

		southJPanel.setLayout(new GridLayout());
		String path = "";
		IV = new InitVector();
		// data = new Vector();
		data = IV.getdata(path);
		Vector columnNames = IV.getcolumn();
		CheckTableModle tableModel = new CheckTableModle(null, columnNames);
		jTable1 = new JTable();
		jTable1.setModel(tableModel);
		jTable1.getTableHeader().setDefaultRenderer(
				new CheckHeaderCellRenderer(jTable1));
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
		
		
		//new Thread(){
			String path;
			//public void run(){
				PropertyConfig pc = new PropertyConfig();
				ArrayList<String> a = pc.readProperties();
				if(a == null){
					new WorkpathSetUI();
					path = pc.getProperty("workpath");
				}
				else if((pc.getProperty("showagain")).equals("NO")){
					new WorkpathSetUI();
					path = pc.getProperty("workpath");
				}
				else
					path = pc.getProperty("workpath");
				localworkPath = path + "\\localwork\\";
				networkPath = path + "\\network\\";
				downloadPath = "D:\\MyOffice\\Github\\SHAFTS\\ChemMapper\\workhome\\download\\";
				picturePath = System.getProperty("user.dir") + "\\Pictures";
				CheckUserStatus CUS = new CheckUserStatus();
				IsUse = 1;// CUS.getuserstatus();
				getContentPane().setLayout(new BorderLayout());
				setLocation(5, 5);
				setSize(1200, 700);
				setTitle("SHAFTS");
				initComponents();
				initwork();
				data.removeAllElements();
				LA.close();
			//}
		//}.start();
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
	private String NewPath;
	private String localworkPath;
	private String networkPath;
	private String downloadPath;
	private String nodeName = null;
	private String oldName = null;
	private String picturePath = null;
	//private LaunchAnimation LA;
	// private String newName = null;
	private int flag = 0; // 分子标记
	private int IsRename = 0; // 是否重命名标记
	private int IsUse = 0;
	private Hashtable<String, Integer> ifshow;
	private TreeNode treenode;
	private JPanel eastJPanel;
	private JPanel westJPanel;
	private JPanel northJPanel;
	private JPanel southJPanel;
	private JPanel centerJPanel;
	private JPanel iconPanel;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;
	private JPanel panel7;
	private JPanel panel8;
	private JPanel panel9;
	private JPanel panel10;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel tp1;
	private JPanel tp2;
	private JComboBox jComboBox111;
	private JComboBox jComboBox222;
	private JComboBox jComboBox333;
	private JComboBox jComboBox1;
	private JComboBox jComboBox2;
	private JComboBox jComboBox4;
	private JTabbedPane jtabbedPane1;
	private JTabbedPane jtabbedPane2;
	private JTextField jtextField1;
	private JTextField jtextField2;
	private JTextField jtextField3;
	private JTextField jtextField4;
	private JTextField jtextField5;
	private JTextField jtextField6;
	private JTextField jtextField7;
	private JTextField jtextField8;
	private JTextField jtextField9;
	private JTextField jtextField10;
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
	private JButton jButton1;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
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
	private JMenu jmenu1;
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
