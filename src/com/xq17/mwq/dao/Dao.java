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

	public String sOrderFormOfMinDatetime() {
		// TODO Auto-generated method stub
		String sql = "select min(consume_date) from tb_order_form";
		System.out.println(sql);
        Object object = selectOnlyValue(sql);
        if (object == null) {
            return null;
        } else {
            return object.toString();
        }
	}

	public Vector sSortName() {
		// TODO Auto-generated method stub
		String sql = "select name from tb_sort";
		System.out.println(sql);
		return selectSomeNote(sql);
	}

	public Vector sMenuOfSell() {
		// TODO Auto-generated method stub
		String sql = "select num, name, code, sort_id, unit, unit_price, status from tb_menu";
		System.out.println(sql);
		Vector vector = selectSomeNote(sql);
		System.out.println(vector);
		for (int i = 0; i < vector.size(); i++) {
			Vector menuV = (Vector) vector.get(i);
			// 表联合
			Vector sortV = sSortById(menuV.get(4).toString());
			menuV.set(4, sortV.get(1));
		}
		return vector;
	}
	
	public Vector sSortById(String id) {
		String sql = "select * from tb_sort where id = " + id;
		System.out.println(sql);
		return selectOnlyNote(sql);
	}

	public String sMenuOfMaxId() {
		// TODO Auto-generated method stub
		String sql = "select max(num) from tb_menu";
		System.out.println(sql);
		Object object = selectOnlyNote(sql);
		if(object == null) {
			return null;
		}else {
			return object.toString();
		}
	}

	public boolean uMenuStateByName(String name, String status) {
		// TODO Auto-generated method stub
		return longHaul("update tb_menu set status='" + status + "' where name='" + name + "'");
	}

	public Vector<Object> sMenuNoteById(String id) {
		// TODO Auto-generated method stub
		String sql = "select * from tb_menu where num=" + id;
		System.out.println(sql);
		return selectOnlyNote(sql);
	}
	
	public Vector<Object> sMenuByName(String name) {
		// TODO Auto-generated method stub
		String sql = "select * from tb_menu where name='" + name + "'";
		System.out.println(sql);
		return selectOnlyNote(sql);
	}

	public Vector sSortByName(String name) {
		// TODO Auto-generated method stub
		String sql = "select id from tb_sort where name='" + name +"'" ;
		System.out.println(sql);
		return selectOnlyNote(sql);
	}
	
	public Vector sSortNameById(String id) {
		// TODO Auto-generated method stub
		String sql = "select name from tb_sort where id=" +id ;
		System.out.println(sql);
		return selectOnlyNote(sql);
	}

	public Boolean iMenu(String[] values) {
		// TODO Auto-generated method stub
        String sql = "insert into tb_menu(num,name, code, sort_id, unit, unit_price, status) values('" + values[0] + "','" + values[1] + "','" + values[2]
                + "'," + values[3] + ",'" + values[4] + "','" + values[5] + "','" + values[6] + "')";
        System.out.println(sql);
        return longHaul(sql);
		
	}

    public boolean uMenu(String[] values) {
        String sql = "update tb_menu set code='" + values[2] + "', sort_id=" + values[3] + ", unit='" + values[4] + "', unit_price=" + values[5] + ", status='"
                + values[6] + "' where name='" + values[1] + "'";
        System.out.println(sql);
        return longHaul(sql);
    }

    public Vector sMenu() {
        String hql = "select num, name, code, sort_id, unit, unit_price from tb_menu";
        System.out.println(hql);
        Vector vector = selectSomeNote(hql);
        for (int i = 0; i < vector.size(); i++) {
            Vector menuV = (Vector) vector.get(i);
            Vector sortV = sSortById(menuV.get(4).toString());
            menuV.set(4, sortV.get(1));
        }
        return vector;
    }

	public Vector sOrderFormOfDay(String date) {
		// TODO Auto-generated method stub
		String sql =  "select * from tb_order_form where consume_date between '" + date +" 00:00:00' and '" + date +" 23:59:59'";
		System.out.println(sql);
		return selectSomeNote(sql);
	}

	public Vector sOrderItemAndMenuByOrderFormNum(String num) {
		// TODO Auto-generated method stub
		String sql = "select * from v_order_item_and_menu  where order_form_num='" + num + "'";
		System.out.println(sql);
		return selectSomeNote(sql);
	}
}
