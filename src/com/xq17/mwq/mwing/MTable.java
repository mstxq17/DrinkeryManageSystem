package com.xq17.mwq.mwing;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MTable extends JTable {
    
    public MTable() {
        super();
    }
    
    public MTable(DefaultTableModel tableModel) {
        super(tableModel);
    }
    
    public MTable(AbstractTableModel tableModel) {
        super(tableModel);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    
    // 表格列值居中显示
    @Override
    public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
        // 获得除表格头部分的单元格对象
        DefaultTableCellRenderer tableRenderer = (DefaultTableCellRenderer) super.getDefaultRenderer(columnClass);
        // 设置单元格内容居中显示
        tableRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        return tableRenderer;
    }
    
    // 表格列名信息
    @Override
    public JTableHeader getTableHeader() {
        // 获得表格头对象
        JTableHeader tableHeader = super.getTableHeader();
        tableHeader.setReorderingAllowed(false);// 设置表格列不可重排
        // 获得表格头的单元格对象
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        // 设置单元格内容（即列名）居中显示
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        return tableHeader;
    }
    
    // 表格不可编辑
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    // 用来设置表格的选中行
    @Override
    public void setRowSelectionInterval(int fromRow, int toRow) {// 重构父类的方法
        super.setRowSelectionInterval(fromRow, toRow);
    }
    
    // 用来设置表格的唯一选中行
    public void setRowSelectionInterval(int row) {// 通过重载实现自己的方法
        setRowSelectionInterval(row, row);
    }
    
    // 表格行只可单选
    @Override
    public ListSelectionModel getSelectionModel() {
        ListSelectionModel selectionModel = super.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return selectionModel;
    }
    
}
