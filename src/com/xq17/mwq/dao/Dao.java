package com.xq17.mwq.dao;

import java.util.Vector;

import org.junit.Test;

public class Dao extends BaseDao {
	// 1.定义一个自己的静态实例
	// 方便被调用
	private static Dao dao;

	static {
		dao = new Dao();
	}
	
	//私有构造方法
	private Dao() {};
	
	public static Dao getInstance() {
		return dao;
	}

	public Vector sUserNameOfNotFreeze() {
		// TODO Auto-generated method stub
		String sql = "select name from tb_user where status = 'ok'";
		System.out.println(sql);
		return selectSomeValue(sql);
	}

	public Vector sUserNameByName(String username) {
		String sql = "select * from tb_user where name = '" + username + "'";
		System.out.println(sql);
		return selectOnlyNote(sql);
	}

	public  Vector sDesk() {
		// TODO Auto-generated method stub
		String sql = "select * from tb_desk where status='ok'";
		System.out.println(sql);
		return selectSomeNote(sql);
	}

	// 根据助记码查询
	public Vector sMenuByCode(String code) {
		// TODO Auto-generated method stub
		String sql = "select * from tb_menu where code like '" + code +"%'";
		System.out.println(sql);
		return selectSomeNote(sql);
	}
	
	// 根据编号查询
    public Vector sMenuById(String num) {
        return selectSomeNote("select * from tb_menu where num like '" + num + "%'");
    }

	public Vector sMenuByNameAndStatus(String menuName, String status) {
		// TODO Auto-generated method stub
		String sql = "select * from tb_menu where name = '" + menuName + "' and  status='" +
					status + "'";
		System.out.println(sql);
		return selectOnlyNote(sql);
	}
	
	// 插入结账信息
    public boolean iOrderForm(String[] values) {
        String sql = "insert into tb_order_form(num,desk_num,consume_date, expenditure, user_id) values('" + values[0] + "','" + values[1] + "','" + values[2] + "',"
                + values[3] + "," + values[4] + ")";
        System.out.println(sql);
        return longHaul(sql);
    }
    
    // tb_order_item
    public boolean iOrderItem(String[] values) {
        String sql = "insert into tb_order_item(order_form_num,menu_num,amount, total) values('" + values[0] + "','" + values[1] + "'," + values[2] + ","
                + values[3] + ")";
        System.out.println(sql);
        return longHaul(sql);
    }
    
    public String sOrderFormOfMaxId() {
    	String sql = "select max(num) from tb_order_form";
    	System.out.println(sql);
        Object object = selectOnlyValue(sql);
        if (object == null) {
            return null;
        } else {
            return object.toString();
        }
    }
	
}
