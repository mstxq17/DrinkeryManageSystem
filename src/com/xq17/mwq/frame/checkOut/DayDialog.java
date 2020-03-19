package com.xq17.mwq.frame.checkOut;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.xq17.mwq.dao.Dao;
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
		String minDatetime =  dao.sOrderFormOfMinDatetime();
		if(minDatetime == null) {
			yearComboBox.addItem(year);
		}else {
			int minYear = Integer.valueOf(minDatetime);
			for(int y=minYear; y<= year; y++) {
				yearComboBox.addItem(y);
			}
		}
		yearComboBox.setSelectedItem(year);
		judgeLeapYear(year);
		panel.add(yearComboBox);
		
	}
	private void judgeLeapYear(int year) {
		// TODO Auto-generated method stub
		if((year%4 == 0 && year%100 != 0) || year%400 ==0) {
			daysOfMonth[2] = 29;
		}else {
			daysOfMonth[2] = 28;
		}
	}
	
}
