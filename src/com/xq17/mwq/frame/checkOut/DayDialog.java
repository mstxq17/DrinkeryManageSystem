package com.xq17.mwq.frame.checkOut;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.junit.Test;

import com.xq17.mwq.dao.Dao;
import com.xq17.mwq.mwing.FixedColumnTablePanel;
import com.xq17.mwq.tool.Today;

public class DayDialog extends JDialog {

	private Vector<String> tableColumnV;
	private Vector<Vector<Object>> tableValueV;
	private JComboBox dayComboBox;
	private JComboBox monthComboBox;
	private JComboBox yearComboBox;
	private int daysOfMonth[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private Dao dao = Dao.getInstance();

	public static void main(String args[]) {
		DayDialog dayDialog = new DayDialog();
		dayDialog.addWindowListener(new WindowAdapter() {
			public void WindowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		dayDialog.setVisible(true);
	}

	/**
	 * Create the dialog
	 */
	public DayDialog() {
		super();
		this.setModal(true);
		this.setTitle("日结账");
		this.setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.NORTH);

		int year = Today.getYEAR();
		int month = Today.getMONTH();
		int day = Today.getDAY();

		yearComboBox = new JComboBox();
		yearComboBox.setMaximumRowCount(10);
		String minDatetime = dao.sOrderFormOfMinDatetime().toString().substring(0, 4);
//		String minDatetime = null;
		if (minDatetime == null) {
			yearComboBox.addItem(year);
		} else {
			int minYear = Integer.valueOf(minDatetime);
			for (int y = minYear; y <= year; y++) {
				yearComboBox.addItem(y);
			}
		}
		yearComboBox.setSelectedItem(year);
		judgeLeapYear(year);
		yearComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int year = (int) yearComboBox.getSelectedItem();
				judgeLeapYear(year);
				int month = (Integer) monthComboBox.getSelectedItem();// 获得选中的月份
				if (month == 2) {// 如果选中的为2月
					int itemCount = dayComboBox.getItemCount();// 获得日下拉菜单当前的天数
					if (itemCount != daysOfMonth[2]) {// 如果日下拉菜单当前的天数不等于2月份的天数
						if (itemCount == 28)// 如果日下拉菜单当前的天数为28天
							dayComboBox.addItem(29);// 则添加为29天
						else
							// 否则日下拉菜单当前的天数则为29天
							dayComboBox.removeItem(29);// 则减少为28天
					}
				}
			}
		});
		panel.add(yearComboBox);

		// 月份
		monthComboBox = new JComboBox();
		monthComboBox.setMaximumRowCount(12);
		for (int m = 1; m < 13; m++) {
			monthComboBox.addItem(m);
		}
		monthComboBox.setSelectedItem(month);
		monthComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int month = (Integer) monthComboBox.getSelectedItem();// 获得选中的月份
				int itemCount = dayComboBox.getItemCount();// 获得日下拉菜单当前的天数
				while (itemCount != daysOfMonth[month]) {// 如果日下拉菜单当前的天数不等于选中月份的天数
					if (itemCount > daysOfMonth[month]) {// 如果大于选中月份的天数
						dayComboBox.removeItem(itemCount);// 则移除最后一个选择项
						itemCount--;// 并将日下拉菜单当前的天数减1
					} else {// 否则小于选中月份的天数
						itemCount++;// 将日下拉菜单当前的天数加1
						dayComboBox.addItem(itemCount);// 并添加为选择项
					}
				}
			}
		});
		panel.add(monthComboBox);
		final JLabel monthLabel = new JLabel();
		monthLabel.setText("月");
		panel.add(monthLabel);
		// 天
		dayComboBox = new JComboBox();
		int days = daysOfMonth[month];
		for (int d = 1; d <= days; d++) {
			dayComboBox.addItem(d);
		}
		dayComboBox.setSelectedItem(day);
		panel.add(dayComboBox);

		final JLabel dayLabel = new JLabel();
		dayLabel.setText("日 ");
		panel.add(dayLabel);

		final JButton submitButton = new JButton();
		submitButton.setText("确定");
		panel.add(submitButton);
		submitButton.addActionListener(new ActionListener() {
			
			@Override
            public void actionPerformed(ActionEvent e) {
				tableValueV.removeAllElements();
				int year = (Integer)yearComboBox.getSelectedItem();
				int month = (Integer)monthComboBox.getSelectedItem();
				int day = (Integer) dayComboBox.getSelectedItem();
				int ColumnCount = tableColumnV.size();
				Vector orderFormV = dao.sOrderFormOfDay(year + "-" + month + "-" + day);
				for(int row=0; row < orderFormV.size(); row++) {
					Vector rowV = new Vector(); // 表格行对象
					Vector orderForm  = (Vector) orderFormV.get(row);
					String orderFormNum = orderForm.get(1).toString();
					rowV.add(orderFormNum);// 编号
					rowV.add(orderForm.get(2));// 台号
					rowV.add(orderForm.get(3).toString().substring(11,19));
					rowV.add(orderForm.get(4));//消费金额
					for(int column=4; column<ColumnCount; column++) {
						rowV.add("--"); 
					}
					Vector orderItemV = dao.sOrderItemAndMenuByOrderFormNum(orderFormNum);
					for(int i=0; i<orderItemV.size();i++) {
						Vector orderItem = (Vector) orderItemV.get(i);// 消费项目对象
						
						String menuName = orderItem.get(4).toString();
                        for (int column = 4; column < ColumnCount; column++) {
                            if (tableColumnV.get(column).equals(menuName)) {
                                int amount = (Integer) orderItem.get(3);
                                String cv  = rowV.get(column).toString().trim();
                                System.out.println(cv);
                                if (cv.equals("--"))
                                    rowV.set(column, amount);
                                else
                                    rowV.set(column, (Integer) rowV.get(column) + amount);
                                break;
                            }
                        }
					}
					tableValueV.add(rowV);
				}
				
				Vector totalV = new Vector();
				totalV.add("总计");
				totalV.add("--");
				totalV.add("--");
				int rowCount = tableValueV.size();
				for(int column=3; column< ColumnCount; column++) {
					int total=0;
					for(int row=0; row<rowCount;row++) {
						Object value = tableValueV.get(row).get(column);
						if(!value.equals("--")) {
							total += (Integer) value;
						}
					}
					totalV.add(total);
				}
				tableValueV.add(totalV);
				Container contentPane = getContentPane();
				contentPane.remove(1);
                contentPane.add(new FixedColumnTablePanel(tableColumnV, tableValueV, 4), BorderLayout.CENTER);
                SwingUtilities.updateComponentTreeUI(contentPane);
				
			}
		});

		tableColumnV = new Vector<String>();
		tableColumnV.add("编号");
		tableColumnV.add("台号");
		tableColumnV.add("开台时间");
		tableColumnV.add("消费金额");
		Vector<Vector<Object>> vector = dao.sMenu();
		for (int i = 0; i < vector.size(); i++) {
			tableColumnV.add(vector.get(i).get(2).toString());
		}
		tableValueV = new Vector();
		this.getContentPane().add(new FixedColumnTablePanel(tableColumnV, tableValueV, 4), BorderLayout.CENTER);
	}

	private void judgeLeapYear(int year) {
		// TODO Auto-generated method stub
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			daysOfMonth[2] = 29;
		} else {
			daysOfMonth[2] = 28;
		}
	}
	
	@Test
	public void Test() {
		String cv = "--".toString();
		System.out.println("--1".equals("--"));
		System.out.println(cv.equals("--"));
	}
}
