package com.xq17.mwq.frame.manage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.peer.LabelPeer;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.junit.Test;

import com.xq17.mwq.dao.Dao;
import com.xq17.mwq.dao.JDBC;
import com.xq17.mwq.mwing.MTable;
import com.xq17.mwq.tool.Today;
import com.xq17.mwq.tool.Validate;

public class MenuDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4676805433881035028L;

	public static void main(String args[]) {
		MenuDialog dialog = new MenuDialog();
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		dialog.setVisible(true);
	}

	/**
	 * Create the dialog
	 */
	private JTextField numTextField;
	private JTextField nameTextField;
	private JTextField unitTextField;
	private JTextField codeTextField;
	private JComboBox sortComboBox;
	private JTextField unitPriceTextField;
	private final Dao dao = Dao.getInstance();
	private final Vector tableColumnV = new Vector();
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private MTable table;
	private String[] menu = new String[7];

	public MenuDialog() {
		super();
		setModal(false);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("菜品管理");
		setBounds(100, 100, 550, 475);

		// 顶部
		final JPanel OperatePanel = new JPanel();
		OperatePanel.setLayout(new FlowLayout());
		OperatePanel.setPreferredSize(new Dimension(200, 110));
		getContentPane().add(OperatePanel, BorderLayout.NORTH);
		// final JButton paddBlock = new JButton(" ");
		// paddBlock.setContentAreaFilled(false);
		// paddBlock.setBorderPainted(false);
		// paddBlock.setEnabled(false);
		final JLabel paddLabel = new JLabel("        ");
		final JLabel numLabel = new JLabel("编  号：");
		OperatePanel.add(numLabel);
		numTextField = new JTextField();
		numTextField.setColumns(8);
		numTextField.setEditable(false);
		//numTextField.setName("编  号");
		String maxId = dao.sMenuOfMaxId();
		System.out.println(maxId);
		numTextField.setText(getNextNum(maxId));
		numTextField.setHorizontalAlignment(SwingConstants.CENTER);
		OperatePanel.add(numTextField);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("名称：");
		OperatePanel.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setName("名称");
		OperatePanel.add(nameTextField);

		final JLabel unitLabel = new JLabel();
		unitLabel.setText("单位：");
		OperatePanel.add(unitLabel);

		unitTextField = new JTextField();
		unitTextField.setName("单位");
		unitTextField.setColumns(5);
		OperatePanel.add(unitTextField);
		OperatePanel.add(paddLabel);

		final JLabel codeLabel = new JLabel();
		codeLabel.setText("助记码：");
		OperatePanel.add(codeLabel);

		codeTextField = new JTextField();
		codeTextField.setName("助记码");
		codeTextField.setColumns(8);
		OperatePanel.add(codeTextField);

		final JLabel sortLabel = new JLabel();
		sortLabel.setText("菜系：");
		OperatePanel.add(sortLabel);
		sortComboBox = new JComboBox();
		sortComboBox.addItem("请选择");
		OperatePanel.add(sortComboBox);

		final JLabel paddLabel3 = new JLabel("      ");
		OperatePanel.add(paddLabel3);
		final JLabel unitPriceLabel = new JLabel();
		unitPriceLabel.setText("单价：");
		OperatePanel.add(unitPriceLabel);

		unitPriceTextField = new JTextField();
		unitPriceTextField.setName("单价");
		unitPriceTextField.setColumns(6);
		OperatePanel.add(unitPriceTextField);

		final JLabel label = new JLabel();
		label.setText("元");
		OperatePanel.add(label);
		final JLabel paddLabel4 = new JLabel(" ");
		OperatePanel.add(paddLabel4);
		Vector vector = dao.sSortName();
		System.out.println(vector);
		for (int i = 0; i < vector.size(); i++) {
			Vector v = (Vector) vector.get(i);
			sortComboBox.addItem(v.get(1));

		}

		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		final JLabel paddLabel5 = new JLabel("                                                             ");
		OperatePanel.add(paddLabel5);
		OperatePanel.add(panel);

		final JButton addButton = new JButton();
		addButton.setText("添加");
		panel.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setMenuInfo(menu);
				if(!verification(menu)) {
					return;
				};
				Vector<Object> menuNote = dao.sMenuNoteById(menu[0]);
				System.out.print(menuNote);
				if(menuNote != null) {
					JOptionPane.showMessageDialog(null, "该菜品已经存在!", "友情提示", JOptionPane.INFORMATION_MESSAGE);
					reset();
				}else if(dao.sMenuByName(menu[1]) != null) {
					JOptionPane.showMessageDialog(null, "该菜品名称已经使用!", "友情提示", JOptionPane.INFORMATION_MESSAGE);
					nameTextField.setText(null);
					nameTextField.requestFocus();
				}else {
					Vector<Object> rowV = new Vector<Object>();
					rowV.add(table.getRowCount()+1);
					for(int i=0; i<menu.length-1;i++) {
						rowV.add(menu[i]);
					}
					rowV.add("ok");
					tableModel.addRow(rowV);
					
					int selectedRow = 0;
					for(int row=0;row<table.getRowCount();row++) {
						if(menu[0].equals(table.getValueAt(row, 1))) {
							selectedRow = row;
							break;
						}
					}
					table.setRowSelectionInterval(selectedRow);
					table.scrollRectToVisible(table.getCellRect(selectedRow, 0, true));
					
					menu[3] = String.valueOf(dao.sSortByName(menu[3].toString()).get(0));
					if(dao.iMenu(menu))
					{
						reset();
					}
				}
			}
		});

		final JButton delButton = new JButton();
		delButton.setText("删除");
		panel.add(delButton);
		delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row == -1) {
					  JOptionPane.showMessageDialog(null, "请选择要删除的菜品！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
				}else {
					String delMenuName = table.getValueAt(row, 2).toString();
					String info = "确认要删除菜品”" + delMenuName+ "“?";
					int i = JOptionPane.showConfirmDialog(null, info, "友情提示", JOptionPane.YES_NO_OPTION);
					if(i==0) {
						dao.uMenuStateByName(delMenuName, "no");
						table.setValueAt("no", row, 7 );
						table.setRowSelectionInterval(row);
						reset();
					}
				}
			}

		});

		final JButton modifyButton = new JButton();
		modifyButton.setText("修改");
		modifyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setMenuInfo(menu);
				if(!verification(menu)) {
					return;
				}
				Vector<Object> menuNote = dao.sMenuNoteById(menu[0]);
				if(menuNote == null) {
					JOptionPane.showMessageDialog(null, "该菜品是新菜品，请点击”添加”按钮添加新菜品！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
				}else {
					String menuName = menuNote.get(2).toString();
					if(!menu[1].equals(menuName)) {
						JOptionPane.showMessageDialog(null, "菜品名称不能修改，请修改菜品其他信息！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
					}else {
						menu[3] = String.valueOf(dao.sSortByName(menu[3].toString()).get(0));
						dao.uMenu(menu);
						
						int changeRow = -1;
						for(int row=0; row<table.getRowCount(); row++) {
							String menuNum = table.getValueAt(row, 1).toString().trim();
							if(menu[0].equals(menuNum)) {
								changeRow = row;
								break;
							}
						}
						// 开始更新表内容
						
						menu[3] = dao.sSortNameById(menu[3]).get(0).toString();
						menu[6] = "ok";
						for(int i=1; i<menu.length;i++) {
							table.setValueAt(menu[i], changeRow, i+1);
						}
						
						table.setRowSelectionInterval(changeRow);
						table.scrollRectToVisible(table.getCellRect(changeRow, 0, true));
						reset();
					}
				}
				
			}
		});
		panel.add(modifyButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		String ColumnsNames[] = new String[] { "序 号", "编 号", "名 称", "助记码", "菜 系", "单 位", "单 价", "状态" };
		for (int i = 0; i < ColumnsNames.length; i++) {
			tableColumnV.add(ColumnsNames[i]);

		}

		tableModel.setDataVector(dao.sMenuOfSell(), tableColumnV);
		JDBC.closeConnection();

		table = new MTable(tableModel);
		table.setAutoCreateRowSorter(true); //自动排序
		if (table.getRowCount() > 0) {
			table.setRowSelectionInterval(0, 0);
		}
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() >=2) {
					//int selectedRow = table.getSelectedRow();
					int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
					Vector vector = (Vector) tableModel.getDataVector().get(selectedRow);
					System.out.println(vector);
					numTextField.setText(vector.get(1).toString());
					nameTextField.setText(vector.get(2).toString());
					nameTextField.setEditable(false);
					codeTextField.setText(vector.get(3).toString());
					sortComboBox.setSelectedItem(vector.get(4).toString());
					unitTextField.setText(vector.get(5).toString());
					unitPriceTextField.setText(vector.get(6).toString());
				}else {
					nameTextField.setEditable(true);
				}
			}
		});
		scrollPane.setViewportView(table);

		final JLabel leftPlaceholderLabel1 = new JLabel();
		leftPlaceholderLabel1.setPreferredSize(new Dimension(10, 10));
		final JLabel leftPlaceholderLabel2 = new JLabel();
		leftPlaceholderLabel2.setPreferredSize(new Dimension(10, 10));
		getContentPane().add(leftPlaceholderLabel1, BorderLayout.WEST);
		getContentPane().add(leftPlaceholderLabel2, BorderLayout.EAST);

		final JPanel exitPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		exitPanel.setLayout(flowLayout);
		getContentPane().add(exitPanel, BorderLayout.SOUTH);

		final JButton exitButton = new JButton();
		exitButton.setText("退出");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 关闭窗口
				dispose();
			}
		});
		exitPanel.add(exitButton);
	}

	protected boolean verification(String[] menu2) {
		// TODO Auto-generated method stub
		// Java 反射机制 获取MenuDialog类的所有属性
		Field[] fields = MenuDialog.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i]; // 获得指定属性
			if (field.getType().equals(JTextField.class)) {
				// 设置访问权限
				field.setAccessible(true);
				JTextField textField = null; // 声明一个JTextField类型的对象
				try {
					// 获得本类中相应的对象
					textField = (JTextField) field.get(MenuDialog.this);
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
				if(textField.getText().trim().equals("")){
					JOptionPane.showMessageDialog(null, "请填写商品“" + textField.getName() + "”！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
					textField.requestFocus(); // 令文本框获得焦点
					return false;
				}
			}
		}
		if (sortComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "请选择商品所属“菜系”！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
            return false;
		}
		
        if (menu[1].length() > 10) {
            JOptionPane.showMessageDialog(null, "菜品名称最多只能为 10 个汉字！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
            nameTextField.requestFocus();
            return false;
        }
        if (menu[2].length() > 10) {
            JOptionPane.showMessageDialog(null, "助记码最多只能为 10 个字符！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
            codeTextField.requestFocus();
            return false;
        }
        if (menu[4].length() > 2) {
            JOptionPane.showMessageDialog(null, "单位最多只能为 2 个汉字！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
            unitTextField.requestFocus();
            return false;
        }
        
        if (!Validate.execute("[1-9]{1}[0-9]{0,3}", menu[5])) {
            String infos[] = { "单价输入错误！", "单价必须在 1――9999" };
            JOptionPane.showMessageDialog(null, infos, "友情提示", JOptionPane.INFORMATION_MESSAGE);
            unitPriceTextField.requestFocus();
            return false;
        }
        return true;
	}

	protected void setMenuInfo(String[] menus) {
		// TODO Auto-generated method stub
		menus[0] = numTextField.getText().trim();
		menus[1] = nameTextField.getText().trim();
		menus[2] = codeTextField.getText().trim();
		menus[3] = sortComboBox.getSelectedItem().toString();
		menus[4] = unitTextField.getText().trim();
		menus[5] = unitPriceTextField.getText().trim();
		menus[6] = "ok";
	}

    private String getNextNum(String maxNum) {
    	maxNum = (String) maxNum.subSequence(1, 9);
        String date = Today.getDateOfNum().substring(2);
        if (maxNum == null) {
            maxNum = date + "01";
        } else {
        	String maxDate = (String) maxNum.subSequence(0, 6);
            if (maxDate.equals(date)) {
                maxNum = maxNum.substring(6);
                int nextNum = Integer.valueOf(maxNum) + 1;
                if (nextNum < 10)
                    maxNum = date + "0" + nextNum;
                else
                    maxNum = date + nextNum;
            } else {
                maxNum = date + "01";
            }
        }
        return maxNum;
    }
	
	private void reset() {
		// TODO Auto-generated method stub
		numTextField.setText(getNextNum(dao.sMenuOfMaxId()));
		nameTextField.setEditable(false);
		nameTextField.setText(null);
		codeTextField.setText(null);
		sortComboBox.setSelectedIndex(0);
		unitTextField.setText(null);
		unitPriceTextField.setText(null);
	}

	@Test
	public void Test() {
		System.out.println(Today.getDateOfNum());
		System.out.println(Today.getDateOfNum().substring(2));
		getNextNum(dao.sMenuOfMaxId());
	}

}
