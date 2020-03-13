package com.xq17.mwq.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class BaseDao {

	// 1.查询多个记录 selectSomeNote
	protected Vector selectSomeNote(String sql) {
		Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
		Connection conn = JDBC.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int columnCount = rs.getMetaData().getColumnCount();
			int row = 1; // 定义行序号
			while (rs.next()) {
				Vector<Object> rowV = new Vector<Object>();
				rowV.add(new Integer(row++));
				for (int column = 1; column <= columnCount; column++) {
					rowV.add(rs.getObject(column));
				}
				vector.add(rowV);
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭链接状态对象
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return vector;
	}

	// 2.查询单个记录selectOnlyNote
	protected Vector selectOnlyNote(String sql) {
		Vector<Object> vector = null;
		Connection conn = JDBC.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int columnCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				vector = new Vector<Object>();
				for (int column = 1; column <= columnCount; column++) {
					vector.add(rs.getObject(column));
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;
	}

	// 3.查询多个值 selectSomeValue
	protected Vector selectSomeValue(String sql) {
		Vector<Object> vector = new Vector<Object>();
		Connection conn = JDBC.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				vector.add(rs.getObject(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;
	}

	// 4.查询单个值 selectOnlyValue
	protected Object selectOnlyValue(String sql) {
		Object value = null;
		Connection conn = JDBC.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				value = rs.getObject(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}

	// 5.插入修改删除记录longHaul
	protected Boolean longHaul(String sql) {
		Boolean isLongHaul = true;
		Connection conn = JDBC.getConnection();
		try {
			// 设置手动提交
//			conn.setAutoCommit(true);
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.commit();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				conn.rollback();
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			isLongHaul = false;
			// TODO: handle exception
		}
		return isLongHaul;
	}

}
