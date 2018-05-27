package org.tw.zlq.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.tw.zlq.core.Element;

public class Utils {
	public static Collection<Element> diff(Collection<Element> list1, Collection<Element> list2){
		List<Element> newList = new ArrayList<>(list1);
		newList.removeAll(list2);
		return newList;
	}
	
	/**
	 * �����¸�ʽ�ַ�����ʼ����element���󼯺�
	 * value0,value1 -- Elements(0, value0), Elements(1, value1)
	 * @return
	 */
	public static Collection<Element> genElements(String source){
	    Set<Element> elements = new HashSet<>();
	    String[] values = source.split(",");
	    for (int index=0;index<values.length;index++) {
	    	int value = Integer.parseInt(values[index].trim());
	    	elements.add(new Element(index, value));
	    }
		return elements;
	}
	
	/**
	 * ��ȡ�����ָ��Сʱ��ʱ�����
	 * @return
	 */
	public static Date getDate(int hour) {
		final long DAY_IN_MILLISECONDS = 24 * 60 * 60 * 1000l;
		final long ONE_HOUR_IN_MILLISECONDS = 60 * 60 * 1000l;
		Date now = new Date();
		return new Date(now.getTime() - (now.getTime() % DAY_IN_MILLISECONDS) + hour * ONE_HOUR_IN_MILLISECONDS);
	}
	
	/**
	 * ��һ��ʱ�����ָ��������
	 * @param date
	 * @param addMinutes
	 * @return
	 */
	public static Date addMinutes(Date date, int addMinutes) {
		final long ONE_MINUTE_IN_MILLISECONDS = 60 * 1000l;
		return new Date(date.getTime() + addMinutes * ONE_MINUTE_IN_MILLISECONDS);
	}
	
	/**
	 * ��date����ת����Сʱ����ָ��AM��PM
	 * @param date
	 */
	public static String dateFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(date);
	}
}
