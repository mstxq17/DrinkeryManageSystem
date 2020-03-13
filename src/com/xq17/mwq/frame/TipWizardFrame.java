package com.xq17.mwq.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.net.URL;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import com.xq17.mwq.dao.Dao;
import com.xq17.mwq.frame.TipWizardFrame.Time;
import com.xq17.mwq.frame.TipWizardFrame.addActionListener;
import com.xq17.mwq.frame.TipWizardFrame.cancelActionListener;
import com.xq17.mwq.frame.TipWizardFrame.checkOutActionListener;
import com.xq17.mwq.frame.TipWizardFrame.codeTextKeyListener;
import com.xq17.mwq.frame.TipWizardFrame.numComboxAddListener;
import com.xq17.mwq.frame.TipWizardFrame.signActionListener;
import com.xq17.mwq.mwing.MButton;
import com.xq17.mwq.mwing.MTable;
import com.xq17.mwq.tool.Today;


public class TipWizardFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		new TipWizardFrame(null).setVisible(true);
	}


	private Vector<String> leftTableColumnV;
	private Vector<Vector<Object>> leftTableValueV;
	private DefaultTableModel leftTableModel;
	private MTable leftTable;
	private Vector<String> rightTableColumnV;
	private Vector<Vector<Object>> rightTableValueV;
	private DefaultTableModel rightTableModel;
	private MTable rightTable;
	private JComboBox<String> numComboBox;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private final Dao dao = Dao.getInstance();
	private JLabel timeLabel;
	private JTextField codeTextField;
	private JTextField nameTextField;
	private JTextField unitTextField;
	private JTextField amountTextField;
	private JTextField expenditureTextField;
	private JTextField realWagesTextField;
	private JTextField changeTextField;
	private JRadioButton codeRadioButton;
	private Vector<Vector<Vector<Object>>> menuOfDeskV;

	// 1. 构造方法
	public TipWizardFrame(Vector user) {
		// 2.属性
		this.setTitle("T 科技");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 1024, 768);
		// this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// this.addWindowListener(new WindowAdapter() {
		// public void windowClosing(WindowAdapter e) {
		// System.out.println("1");
		// System.exit(0);
		// }
		// });
		// 3.设置北部标签
		URL topUrl = this.getClass().getResource("/img/top.jpg");
		final JLabel topLabel = new JLabel();
		topLabel.setPreferredSize(new Dimension(0, 100));
		// 使 Icon 居中
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topLabel.setIcon(new ImageIcon(topUrl));
		this.add(topLabel, BorderLayout.NORTH);

		// 4. 设计中部签单工作区
		// 4.1 创建分割面板
		JSplitPane jSplitPanel = new JSplitPane();
		// 4.2 设置为水平分割
		jSplitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		// 4.3 设置面板默认分割位置
		jSplitPanel.setDividerLocation(755);
		jSplitPanel.setDividerSize(5);
		// 4.4 设置为支持快速展开/折叠分隔条
		jSplitPanel.setOneTouchExpandable(true);
		// 4.5 设置边框 TitleBorder
		jSplitPanel.setBorder(new TitledBorder("show"));
		this.add(jSplitPanel, BorderLayout.CENTER);

		// 4.6 创建一个左侧容器面板 leftPanel
		JPanel leftPanel = new JPanel();
		// 4.6.1 设置面板的布局
		leftPanel.setLayout(new BorderLayout());
		// 4.6.2 将容器面板放入
		jSplitPanel.setLeftComponent(leftPanel);
		// 4.6.3 建立一个leftTitleLabel
		JLabel leftTitleLabel = new JLabel();
		// 4.6.4 设置字体 加粗 14
		leftTitleLabel.setFont(new Font("", Font.BOLD, 14));
		// 4.6.5 设置高宽
		leftTitleLabel.setPreferredSize(new Dimension(0, 25));
		// 4.6.6 设置标签名
		leftTitleLabel.setText(" 签单列表: ");
		// 4.6.7 添加进左侧容器的北部
		leftPanel.add(leftTitleLabel, BorderLayout.NORTH);

		// 4.7 创建一个JscrollPanel面板 leftScrollPane
		JScrollPane leftScrollPane = new JScrollPane();
		// 4.8 添加进左侧容器
		leftPanel.add(leftScrollPane);

		menuOfDeskV = new Vector<Vector<Vector<Object>>>();
		// 4.9 设置列名 leftTableColumnV Vector<String>
		leftTableColumnV = new Vector<String>();
		String leftTableColumns[] = { "  ", "序    号", "商品编号", "商品名称", "单    位", "数    量", "单    价", "金    额" };
		// 4.10 for循环动态添加进leftTableColumnV
		for (int i = 0; i < leftTableColumns.length; i++) {
			leftTableColumnV.add(leftTableColumns[i]);
		}
		// 4.11 创建值 leftTableValueV Vector<Vector<Object>
		leftTableValueV = new Vector<Vector<Object>>();
		// 4.12 创建表格模型
		leftTableModel = new DefaultTableModel(leftTableValueV, leftTableColumnV);
		// 动态修改消费金额的数据
		leftTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				int rowCount = leftTable.getRowCount();
				float expenditure = 0.0f;
				for (int row = 0; row < rowCount; row++) {
					expenditure += Float.valueOf(leftTable.getValueAt(row, 7).toString());
				}
				// 修改结账窗口的消费金额
				expenditureTextField.setText(expenditure + "0");
			}
		});
		// 4.3 leftTable 得到 MTable的实例
		leftTable = new MTable(leftTableModel);

		// 4.4 在jscrollPanel 显示
		leftScrollPane.add(leftTable);
		leftScrollPane.setViewportView(leftTable);

		// 5.右边差不多 rightPanel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		jSplitPanel.setRightComponent(rightPanel);
		JLabel rightTitleLabel = new JLabel();
		rightTitleLabel.setFont(new Font("", Font.BOLD, 14));
		rightTitleLabel.setPreferredSize(new Dimension(0, 25));
		rightTitleLabel.setText(" 开台列表: ");
		rightPanel.add(rightTitleLabel, BorderLayout.NORTH);
		JScrollPane rightScrollPane = new JScrollPane();
		rightPanel.add(rightScrollPane);
		rightTableColumnV = new Vector<String>();
		rightTableColumnV.add("序    号");
		rightTableColumnV.add("台    号");
		rightTableColumnV.add("开台时间");
		rightTableValueV = new Vector<Vector<Object>>();
		rightTableModel = new DefaultTableModel(rightTableValueV, rightTableColumnV);
		rightTable = new MTable(rightTableModel);
		// 设置鼠标监听事件
		rightTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int rSelectedRow = rightTable.getSelectedRow();// 获得“开台列表”中的选中行
				leftTableValueV.removeAllElements();// 清空“签单列表”中的所有行
				leftTableValueV.addAll(menuOfDeskV.get(rSelectedRow));// 将选中台号的签单列表添加到“签单列表”中
				leftTableModel.setDataVector(leftTableValueV, leftTableColumnV);// 刷新“签单列表”
				leftTable.setRowSelectionInterval(0);// 选中“签单列表”中的第一行
				numComboBox.setSelectedItem(rightTable.getValueAt(rSelectedRow, 1));// 同步选中“台号”下拉菜单中的相应台号
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		rightScrollPane.add(rightTable);
		rightScrollPane.setViewportView(rightTable);

		// 6. 设置窗口南部
		// 6.1 设置一个bottomPanel 容器面板
		final JPanel bottomPanel = new JPanel();
		// 6.2 设置高宽
		bottomPanel.setPreferredSize(new Dimension(0, 230));
		// 6.3 设置布局
		bottomPanel.setLayout(new BorderLayout());
		// 6.4 将容器添加进当前面板的南部
		this.add(bottomPanel, BorderLayout.SOUTH);

		// 7 设置中间开单列 orderDishesPanel
		final JPanel orderDishesPanel = new JPanel();
		// 7.1 设置浮雕边缘
		orderDishesPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		// 7.2 添加进bottomPanel容器的北部
		bottomPanel.add(orderDishesPanel, BorderLayout.NORTH);
		// 7.3 example,台号numLabel
		final JLabel numLabel = new JLabel("台号:");
		numLabel.setFont(new Font("", Font.BOLD, 12));
		orderDishesPanel.add(numLabel);
		// 7.4 设置下拉列表numComboBox
		numComboBox = new JComboBox<String>();
		// 7.5 添加监听事件
		numComboBox.addActionListener(new numComboxAddListener());
		// 初始化台号下拉列表
		initNumComboBox();
		orderDishesPanel.add(numComboBox);

		// *作业练习, 补全按钮
		final JLabel codeALabel = new JLabel();
		codeALabel.setFont(new Font("", Font.BOLD, 12));
		codeALabel.setText("  商品（");
		orderDishesPanel.add(codeALabel);
		final JRadioButton numRadioButton = new JRadioButton();
		numRadioButton.setFont(new Font("", Font.BOLD, 12));
		buttonGroup.add(numRadioButton);
		numRadioButton.setText("编号");
		orderDishesPanel.add(numRadioButton);
		final JLabel codeBLabel = new JLabel();
		codeBLabel.setText("/");
		orderDishesPanel.add(codeBLabel);
		codeRadioButton = new JRadioButton();
		codeRadioButton.setFont(new Font("", Font.BOLD, 12));
		buttonGroup.add(codeRadioButton);
		codeRadioButton.setSelected(true);
		codeRadioButton.setText("助记码");
		orderDishesPanel.add(codeRadioButton);
		final JLabel codeCLabel = new JLabel();
		codeCLabel.setText("):");
		orderDishesPanel.add(codeCLabel);
		codeTextField = new JTextField();
		codeTextField.setColumns(6);
		orderDishesPanel.add(codeTextField);
		// 监听事件，实现智能选菜
		codeTextField.addKeyListener(new codeTextKeyListener());
		// 添加一个商品名称
		final JLabel nameLabel = new JLabel();
		nameLabel.setFont(new Font("", Font.BOLD, 12));
		nameLabel.setText(" 商品名称 ");
		orderDishesPanel.add(nameLabel);
		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nameTextField.setEditable(false);
		nameTextField.setFocusable(false);
		nameTextField.setColumns(7);
		orderDishesPanel.add(nameTextField);

		final JLabel unitLabel = new JLabel("  单位：");
		unitLabel.setFont(new Font("", Font.BOLD, 12));
		orderDishesPanel.add(unitLabel);
		unitTextField = new JTextField();
		unitTextField.setHorizontalAlignment(SwingConstants.CENTER);
		unitTextField.setEditable(false);
		unitTextField.setFocusable(false);
		unitTextField.setColumns(4);
		orderDishesPanel.add(unitTextField);

		final JLabel amountLabel = new JLabel(" 数量: ");
		amountLabel.setFont(new Font("", Font.BOLD, 12));
		orderDishesPanel.add(amountLabel);

		amountTextField = new JTextField();
		// 这里可以设置监听事件
		// 对数量进行限制
		amountTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				String amount = amountTextField.getText();
				if (amount.length() == 0) {
					amountTextField.setText("1");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				amountTextField.setText(null);
			}
		});
		amountTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				int length = amountTextField.getText().length();
				if (length < 2) {
					String num = (length == 0 ? "123456789" : "0123456789");
					if (num.indexOf(e.getKeyChar()) < 0) {
						e.consume();
					}
				} else {
					e.consume();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		amountTextField.setText("1");
		amountTextField.setHorizontalAlignment(SwingConstants.CENTER);
		amountTextField.setColumns(2);
		orderDishesPanel.add(amountTextField);

		// 开单、 签单 、取消 都需要添加事件
		final JButton addButton = new JButton("开 单");
		addButton.addActionListener(new addActionListener());
		final JButton signButton = new JButton("签 单");
		signButton.addActionListener(new signActionListener());
		final JButton cancelButton = new JButton("取 消");
		cancelButton.addActionListener(new cancelActionListener());
		orderDishesPanel.add(addButton);
		orderDishesPanel.add(signButton);
		orderDishesPanel.add(cancelButton);

		// 8.设计底层，收银页面
		// 8.1 设计一个容器clueOnPanel
		final JPanel clueOnPanel = new JPanel();
		// 8.2 设置大小
		clueOnPanel.setPreferredSize(new Dimension(250, 0));
		clueOnPanel.setBorder(new TitledBorder("Timer"));
		clueOnPanel.setLayout(new GridLayout(0, 1));
		bottomPanel.add(clueOnPanel, BorderLayout.WEST);
		final JLabel aClueOnLabel = new JLabel();
		clueOnPanel.add(aClueOnLabel);
		aClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		aClueOnLabel.setText("  今天是：");

		final JLabel bClueOnLabel = new JLabel();
		bClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(bClueOnLabel);
		bClueOnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bClueOnLabel.setText(Today.getDateOfShow());
		// 星期获取
		final JLabel cClueOnLabel = new JLabel();
		cClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(cClueOnLabel);
		cClueOnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cClueOnLabel.setText(Today.getDayOfWeek());
		// 时间线程
		timeLabel = new JLabel();// 创建用于显示时间的标签对象
		timeLabel.setFont(new Font("宋体", Font.BOLD, 14));// 设置标签中的文字为宋体、粗体、14号
		timeLabel.setForeground(new Color(255, 0, 0));// 设置标签中的文字为红色
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);// 设置标签中的文字居中显示
		clueOnPanel.add(timeLabel);// 将标签添加到上级容器中
		new Time().start();

		final JLabel eClueOnLabel = new JLabel("当前操作员: ");
		clueOnPanel.add(eClueOnLabel);
		eClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		final JLabel showUserLabel = new JLabel();
		showUserLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(showUserLabel);
		showUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
		if (user == null) {
			showUserLabel.setText("系统默认用户");
		} else {
			showUserLabel.setText(user.get(1).toString());
		}

		// 设置结账功能模块 bottomPanel center 部分
		final JPanel checkOutPanel = new JPanel();
		checkOutPanel.setPreferredSize(new Dimension(500, 0));
		bottomPanel.add(checkOutPanel);
		checkOutPanel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		checkOutPanel.setLayout(new GridBagLayout());
		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(72, 70));
		URL rmbUrl = this.getClass().getResource("/img/rmb.jpg");
		ImageIcon rmbIcon = new ImageIcon(rmbUrl);
		label.setIcon(rmbIcon);
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(0, 0, 0, 15);
		gridBagConstraints_9.gridheight = 4;
		gridBagConstraints_9.gridy = 0;
		gridBagConstraints_9.gridx = 0;
		checkOutPanel.add(label, gridBagConstraints_9);
		final JLabel expenditureLabel = new JLabel();
		expenditureLabel.setFont(new Font("", Font.BOLD, 16));
		expenditureLabel.setText("消费金额：");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.gridx = 1;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.insets = new Insets(0, 10, 0, 0);
		checkOutPanel.add(expenditureLabel, gridBagConstraints_13);
		expenditureTextField = new JTextField();
		expenditureTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		expenditureTextField.setText("0.00");
		expenditureTextField.setForeground(new Color(255, 0, 0));
		expenditureTextField.setFont(new Font("", Font.BOLD, 15));
		expenditureTextField.setColumns(7);
		expenditureTextField.setEditable(false);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.gridx = 2;
		checkOutPanel.add(expenditureTextField, gridBagConstraints_6);
		final JLabel expenditureUnitLabel = new JLabel();
		expenditureUnitLabel.setForeground(new Color(255, 0, 0));
		expenditureUnitLabel.setFont(new Font("", Font.BOLD, 15));
		expenditureUnitLabel.setText(" 元");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 3;
		checkOutPanel.add(expenditureUnitLabel, gridBagConstraints_14);
		final JLabel realWagesLabel = new JLabel();
		realWagesLabel.setFont(new Font("", Font.BOLD, 16));
		realWagesLabel.setText("实收金额：");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 1;
		checkOutPanel.add(realWagesLabel, gridBagConstraints_7);
		realWagesTextField = new JTextField();
		realWagesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		realWagesTextField.setText("0.00");
		realWagesTextField.setForeground(new Color(0, 128, 0));
		realWagesTextField.setFont(new Font("", Font.BOLD, 15));
		realWagesTextField.setColumns(7);
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.gridx = 2;
		checkOutPanel.add(realWagesTextField, gridBagConstraints_8);
		final JLabel realWagesUnitLabel = new JLabel();
		realWagesUnitLabel.setForeground(new Color(0, 128, 0));
		realWagesUnitLabel.setFont(new Font("", Font.BOLD, 15));
		realWagesUnitLabel.setText(" 元");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_15.gridy = 1;
		gridBagConstraints_15.gridx = 3;
		checkOutPanel.add(realWagesUnitLabel, gridBagConstraints_15);

		final JButton checkOutButton = new JButton();
		// 添加结账的监听事件
		checkOutButton.addActionListener(new checkOutActionListener(user));
		checkOutButton.setFont(new Font("", Font.BOLD, 12));
		checkOutButton.setMargin(new Insets(2, 14, 2, 14));
		checkOutButton.setText("结 账");
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.anchor = GridBagConstraints.EAST;
		gridBagConstraints_10.gridwidth = 2;
		gridBagConstraints_10.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_10.gridy = 2;
		gridBagConstraints_10.gridx = 2;
		checkOutPanel.add(checkOutButton, gridBagConstraints_10);
		final JLabel changeLabel = new JLabel();
		changeLabel.setFont(new Font("", Font.BOLD, 16));
		changeLabel.setText("找零金额：");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_11.gridy = 3;
		gridBagConstraints_11.gridx = 1;
		checkOutPanel.add(changeLabel, gridBagConstraints_11);

		changeTextField = new JTextField();
		changeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		changeTextField.setText("0.00");
		changeTextField.setForeground(new Color(255, 0, 255));
		changeTextField.setFont(new Font("", Font.BOLD, 15));
		changeTextField.setEditable(false);
		changeTextField.setColumns(7);
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_12.gridy = 3;
		gridBagConstraints_12.gridx = 2;
		checkOutPanel.add(changeTextField, gridBagConstraints_12);

		final JLabel changeUnitLabel = new JLabel();
		changeUnitLabel.setForeground(new Color(255, 0, 255));
		changeUnitLabel.setFont(new Font("", Font.BOLD, 15));
		changeUnitLabel.setText(" 元");
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_16.gridy = 3;
		gridBagConstraints_16.gridx = 3;
		checkOutPanel.add(changeUnitLabel, gridBagConstraints_16);
		bottomPanel.add(checkOutPanel, BorderLayout.CENTER);
		// end

		// 设置功能点模块 bottomPanel east 部分
		// 设置容器
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 0));
		bottomPanel.add(buttonPanel, BorderLayout.EAST);

		// 1.菜品管理
		final JPanel aButtonPanel = new JPanel();
		aButtonPanel.setBorder(new TitledBorder(""));
		aButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(aButtonPanel);
		final JButton aTopButton = new MButton();
		URL menUrl = this.getClass().getResource("/img/menu.jpg");
		ImageIcon menuIcon = new ImageIcon(menUrl);
		aTopButton.setIcon(menuIcon);
		aButtonPanel.add(aTopButton);

		// 1.菜系管理
		final JButton aCenterButton = new MButton();
		URL sortUrl = this.getClass().getResource("/img/sort.jpg");
		ImageIcon sortIcon = new ImageIcon(sortUrl);
		aCenterButton.setIcon(sortIcon);
		aButtonPanel.add(aCenterButton);

		// 1.台号管理
		final JButton aBottomButton = new MButton();
		URL deskUrl = this.getClass().getResource("/img/desk.jpg");
		ImageIcon deskIcon = new ImageIcon(deskUrl);
		aBottomButton.setIcon(deskIcon);
		aButtonPanel.add(aBottomButton);

		final JPanel bButtonPanel = new JPanel();
		bButtonPanel.setBorder(new TitledBorder(""));
		bButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(bButtonPanel);

		// 2.日结账
		final JButton bTopButton = new MButton();
		URL dayUrl = this.getClass().getResource("/img/day.png");
		ImageIcon dayIcon = new ImageIcon(dayUrl);
		bTopButton.setIcon(dayIcon);
		bButtonPanel.add(bTopButton);

		// 2.月结账
		final JButton bCenterButton = new MButton();
		URL monthUrl = this.getClass().getResource("/img/month.png");
		ImageIcon monthIcon = new ImageIcon(monthUrl);
		bCenterButton.setIcon(monthIcon);
		bButtonPanel.add(bCenterButton);

		// 2.年结账
		final JButton bBottomButton = new MButton();
		URL yearUrl = this.getClass().getResource("/img/year.png");
		ImageIcon yearIcon = new ImageIcon(yearUrl);
		bBottomButton.setIcon(yearIcon);
		bButtonPanel.add(bBottomButton);

		final JPanel cButtonPanel = new JPanel();
		cButtonPanel.setBorder(new TitledBorder(""));
		cButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(cButtonPanel);
		// 3.修改密码
		final JButton cTopButton = new MButton();
		URL passwordUrl = this.getClass().getResource("/img/password.jpg");
		ImageIcon passwordIcon = new ImageIcon(passwordUrl);
		cTopButton.setIcon(passwordIcon);
		cButtonPanel.add(cTopButton);
		// 3.用户管理
		final JButton cCenterButton = new MButton();
		URL userUrl = this.getClass().getResource("/img/user.jpg");
		ImageIcon userIcon = new ImageIcon(userUrl);
		cCenterButton.setIcon(userIcon);
		cButtonPanel.add(cCenterButton);
		// 3.退出系统
		final JButton cBottomButton = new MButton();
		URL exitUrl = this.getClass().getResource("/img/exit.jpg");
		ImageIcon exitIcon = new ImageIcon(exitUrl);
		cBottomButton.setIcon(exitIcon);
		cButtonPanel.add(cBottomButton);
		cBottomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (rightTable.getRowCount() > 0) {
					JOptionPane.showMessageDialog(null, "还有未结账的餐台，系统不能退出", "友情提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					System.exit(0);
				}
			}
		});
	}

	private void initNumComboBox() {
		// TODO Auto-generated method stub
		numComboBox.removeAllItems();
		numComboBox.addItem("请选择");
		Vector allDeskV = dao.sDesk();
		System.out.println(allDeskV);
		for (int i = 0; i < allDeskV.size(); i++) {
			Vector deskV = (Vector) allDeskV.get(i);
			numComboBox.addItem(deskV.get(1).toString());
		}

	}

	// 开单处理函数
	private void makeOutAnInvoice() {
		// TODO Auto-generated method stub
		String deskNum = numComboBox.getSelectedItem().toString();
		String menuName = nameTextField.getText();
		String menuAmount = amountTextField.getText();
		// check
		if (deskNum.equals("请选择")) {
			JOptionPane.showMessageDialog(null, "请选择台号", "友情提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (menuName.length() == 0) {
			JOptionPane.showMessageDialog(null, "请录入商品名称！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// 处理开台信息
		int rightSelectedRow = rightTable.getSelectedRow();
		int leftRowCount = 0; // 默认点菜数目为0
		if (rightSelectedRow == -1) {
			rightSelectedRow = rightTable.getRowCount();
			// 新开台的向量对象
			Vector deskV = new Vector();
			deskV.add(rightSelectedRow + 1);
			deskV.add(deskNum);
			deskV.add(Today.getTime());
			rightTableModel.addRow(deskV);
			// 选中新开的台
			rightTable.setRowSelectionInterval(rightSelectedRow);
			menuOfDeskV.add(new Vector());
		} else {
			leftRowCount = leftTable.getRowCount();
		}

		// 处理点菜信息
		Vector vector = dao.sMenuByNameAndStatus(menuName, "ok");
		if (vector.size() == 0) {
			JOptionPane.showMessageDialog(null, "该商品已经停售,请选择其他商品", "友情提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
			int amount = Integer.valueOf(menuAmount);
			int unitPrice = Integer.valueOf(vector.get(5).toString());
			int money = unitPrice * amount;
			Vector<Object> menuV = new Vector<Object>();
			menuV.add("NEW"); // 新点菜标记
			menuV.add(leftRowCount + 1);
			menuV.add(vector.get(0));
			menuV.add(menuName);
			menuV.add(vector.get(4));
			menuV.add(amount);
			menuV.add(unitPrice);
			menuV.add(money);
			leftTableModel.addRow(menuV);
			leftTable.setRowSelectionInterval(leftRowCount);
			menuOfDeskV.get(rightSelectedRow).add(menuV);

			// 清理工作
			codeTextField.setText(null);
			nameTextField.setText(null);
			unitTextField.setText(null);
			amountTextField.setText("1");
		}
	}
	
	public String getNum() {
		// TODO Auto-generated method stub
        String maxNum = dao.sOrderFormOfMaxId();
        String date = Today.getDateOfNum();
        if (maxNum == null) {
            maxNum = date + "001";
        } else {
            if (maxNum.subSequence(0, 8).equals(date)) {
                maxNum = maxNum.substring(8);
                int nextNum = Integer.valueOf(maxNum) + 1;
                if (nextNum < 10)
                    maxNum = date + "00" + nextNum;
                else if (nextNum < 100)
                    maxNum = date + "0" + nextNum;
                else
                    maxNum = date + nextNum;
            } else {
                maxNum = date + "001";
            }
        }
        return maxNum;
	}

	public class Time extends Thread {
		public void run() {
			while (true) {
				Date date = new Date();
				timeLabel.setText(date.toString().substring(11, 19));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public class checkOutActionListener implements ActionListener {

		private Vector user; 
		public checkOutActionListener(Vector user) {
			// TODO Auto-generated constructor stub
			this.user  = user;
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int selectedRow = rightTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(null, "请选择要结账的台号！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				int rowCount = leftTable.getRowCount();
				String NEW = leftTable.getValueAt(rowCount - 1, 0).toString();// 获得最后点菜的标记
				if (NEW.equals("NEW")) {// 如果最后点菜被标记为“NEW”,则弹出提示
					JOptionPane.showMessageDialog(null, "请先确定未签单商品的处理方式！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 消费金额
					float expenditure = Float.valueOf(expenditureTextField.getText());
					float realWages = Float.valueOf(realWagesTextField.getText());
					if (realWages < expenditure) {
						if (realWages == 0.0f)
							JOptionPane.showMessageDialog(null, "请输入实收金额！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "实收金额不能小于消费金额！", "友情提示",
									JOptionPane.INFORMATION_MESSAGE);
					} else {
						changeTextField.setText(realWages - expenditure + "0");
						String[] values = { getNum(), rightTable.getValueAt(selectedRow, 1).toString(),
								Today.getDate() + " " + rightTable.getValueAt(selectedRow, 2),
								expenditureTextField.getText(), user.get(5).toString() };// 组织消费单信息
						if(dao.iOrderForm(values)) {
							System.out.println("成功执行iOrderFrom语句！");
						}else{
							System.out.println("iOrderFrom语句执行失败！");
						}// 持久化到数据库
						values[0] = dao.sOrderFormOfMaxId();// 获得消费单编号
                        for (int i = 0; i < rowCount; i++) {// 通过循环获得各个消费项目的信息
                            values[1] = leftTable.getValueAt(i, 2).toString();// 获得商品编号
                            values[2] = leftTable.getValueAt(i, 5).toString();// 获得商品数量
                            values[3] = leftTable.getValueAt(i, 7).toString();// 获得商品消费金额
    						if(dao.iOrderItem(values)) { // 持久化到数据库
    							System.out.println("成功执行iOrderItem语句！");
    						}else{
    							System.out.println("iOrderItem语句执行失败！");
    						}// 持久化到数据库
                        }
						JOptionPane.showMessageDialog(null, rightTable.getValueAt(selectedRow, 1) + " 结账完成！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);// 弹出结账完成提示
						rightTableModel.removeRow(selectedRow);// 从“开台列表”中取消开台
						leftTableValueV.removeAllElements();// 清空“签单列表”
						leftTableModel.setDataVector(leftTableValueV, leftTableColumnV);// 刷新“签单列表”
						realWagesTextField.setText("0.00");// 清空“实收金额”文本框
						changeTextField.setText("0.00");// 清空“找零金额”文本框
						menuOfDeskV.remove(selectedRow);//
					}
				}
			}
		}

	}

	public class cancelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int lSelectedRow = leftTable.getSelectedRow();// 获得“签单列表”中的选中行，即要取消的菜品
			if (lSelectedRow == -1) {// 未选中任何行
				JOptionPane.showMessageDialog(null, "请选择要取消的商品！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				int rSelectedRow = rightTable.getSelectedRow();// 获得“开台列表”中的选中行，即取消菜品的台号
				int i = JOptionPane.showConfirmDialog(null, "确定要取消“" + rightTable.getValueAt(rSelectedRow, 1) + "”中的商品“"
						+ leftTable.getValueAt(lSelectedRow, 3) + "”？", "友情提示", JOptionPane.YES_NO_OPTION);// 弹出提示信息确认是否取消
				if (i == 0) {// 确认取消
					leftTableModel.removeRow(lSelectedRow);// 从“签单列表”中取消菜品
					int rowCount = leftTable.getRowCount();// 获得取消后的点菜数量
					System.out.println("RowCount:" + rowCount);
					System.out.println("lSelectedRow:" + lSelectedRow);
					if (rowCount == 0) {// 未点任何菜品
						rightTableModel.removeRow(rSelectedRow);// 取消开台
						int rRowCount = rightTable.getRowCount();
						for (int row = rSelectedRow; row < rRowCount; row++) {
							rightTable.setValueAt(row + 1, row, 0);
						}
						menuOfDeskV.remove(rSelectedRow);// 移除签单列表
					} else {
						Vector<Vector<Object>> menus = menuOfDeskV.get(rSelectedRow);
						if (lSelectedRow == rowCount) {// 取消菜品为最后一个
							lSelectedRow -= 1;// 设置最后一个菜品为选中的
						} else {// 取消菜品不是最后一个
							for (int row = lSelectedRow; row < rowCount; row++) {// 修改点菜序号
								leftTable.setValueAt(row + 1, row, 1);
								menus.get(row + 1).set(1, row + 1);
							}
							menus.remove(lSelectedRow);
						}
						menuOfDeskV.set(rSelectedRow, menus);
						leftTable.setRowSelectionInterval(lSelectedRow);// 设置选中行
					}
				}
			}

		}

	}

	public class addActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// 开单函数
			makeOutAnInvoice();
			codeTextField.requestFocus();
		}
	}

	/**
	 * 签单的处理方法
	 * 
	 * @author xq17
	 *
	 */
	public class signActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int selectedRow = rightTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(null, "请选择要签单的台号!", "友情提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				// 开始签单,修改左边的Row(0)
				// 从最后开始
				int row = leftTable.getRowCount() - 1;
				String NEW = leftTable.getValueAt(row, 0).toString();
				if (NEW.equals("NEW")) {
					// 最后一行如果是NEW
					// 重新获取value的值
					leftTableValueV.removeAllElements();
					leftTableValueV.addAll(menuOfDeskV.get(selectedRow));
					for (; row >= 0; row--) // 全都修改
					{
						leftTableValueV.get(row).set(0, "");
					}
					// 重新渲染
					leftTableModel.setDataVector(leftTableValueV, leftTableColumnV);
					leftTable.setRowSelectionInterval(0);
				}
			}
		}

	}

	/**
	 * 处理codeTextField的键盘事件,实现获取智能菜品
	 * 
	 * @author xq17
	 *
	 */
	public class codeTextKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			// 限制不能输入空格
			if (e.getKeyChar() == ' ') {
				e.consume();
			}
			// 按下回车键,等同于"开单"的按钮
			// 按下不是回车键，则获取输入内容，内容不为空，判断输入的是商品编号还是助记码,
			// 然后做相应的查询数据库操作(取第一个)
			// 显示菜品的名称和单位,若没有则置空
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			String input = codeTextField.getText().trim();
			Vector vector = null;
			if (input.length() > 0) { // 不空
				if (codeRadioButton.isSelected()) {
					vector = dao.sMenuByCode(input); // 查询符合条件的菜品
					// 判断返回结果
					if (vector.size() > 0) {
						// 取第一个
						vector = (Vector) vector.get(0);
						System.out.println(vector);
					} else {
						vector = null;
					}
				} else {
					vector = dao.sMenuById(input);
					if (vector.size() > 0) {
						vector = (Vector) vector.get(0);
						System.out.println(vector);
					} else {
						vector = null;
					}
				}
			}
			// 开始设计值 名称和单位
			if (vector == null) {
				nameTextField.setText(null);
				unitTextField.setText(null);
			} else {
				nameTextField.setText(vector.get(3).toString());
				unitTextField.setText(vector.get(5).toString());
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public class numComboxAddListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// 获得开台列表行数
			int rowCount = rightTable.getRowCount();
			if (rowCount > 0) {
				String selectedDeskNum = numComboBox.getSelectedItem().toString();
				int needSelectedRow = -1;
				opened: for (int row = 0; row < rowCount; row++) {// 通过循环查看选中的台号是否已经开台
					String openedDeskNum = rightTable.getValueAt(row, 1).toString();// 获得已开台的台号
					if (selectedDeskNum.equals(openedDeskNum)) {// 查看选中的台号是否已经开台
						needSelectedRow = row;// 选中的台号已经开台
						break opened;// 跳出循环
					}
				}
				if (needSelectedRow == -1) {

					rightTable.clearSelection();// 取消选择
					leftTableValueV.removeAllElements();// 清空"签单列表"中的所有行
					leftTableModel.setDataVector(leftTableValueV, leftTableColumnV); // 刷新签单列表

				} else {
					// 选中的台号已经开启
					if (needSelectedRow != rightTable.getSelectedRow()) {
						rightTable.setRowSelectionInterval(needSelectedRow);
						leftTableValueV.removeAllElements(); // 清空“清单列表”中的所有行
						leftTableValueV.addAll(menuOfDeskV.get(needSelectedRow));
						// 重新渲染
						leftTableModel.setDataVector(leftTableValueV, leftTableColumnV);
						leftTable.setRowSelectionInterval(0); // 选中第一行
					}
				}

			}
		}
	}

}
