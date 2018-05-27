package org.tw.zlq.utils;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;


public class UtilsTest {

	@Test
	public void testGetDate() {
		Date date = Utils.getDate(1);
		System.out.println(Utils.dateFormat(date));
	}
	
	@Test
	public void testDateFormat() {
		// 北京时间 20：41  -- > 12:41
		Date date = new Date(1527424886259l); 
		Assert.assertEquals("12:41下午", Utils.dateFormat(date));
	}

	@Test
	public void testAddMinute() {
		Date date = Utils.getDate(1);
		Date date2 = Utils.addMinutes(date, 20); 
		Assert.assertEquals(20 * 60 * 1000l, date2.getTime() - date.getTime());
		System.out.println(Utils.dateFormat(date) + "/" + Utils.dateFormat(date2));
	}
}
