/**
 * 
 */
package org.tw.zlq.core;

import java.util.Collection;

/**
 * @author longqiang
 *
 */
public interface Condition {
	/**
	 * Ԫ�ؼ����Ƿ���������<br>
	 * ���� -1��ʾ�ж�С������;
	 * ����0��ʾ����; 
	 * ����1��ʾ�ж���������
	 * @param elems
	 * @return
	 */
	public int isSatisfy(Collection<Element> elems);
}
