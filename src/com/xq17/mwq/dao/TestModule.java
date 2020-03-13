package com.xq17.mwq.dao;

import org.junit.Test;

public class TestModule {

	@Test
	public  void JdbcTest() {
		Dao dao = Dao.getInstance();
		System.out.println(dao.sUserNameOfNotFreeze());
	}

}
