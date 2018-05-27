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
	 * 元素集合是否满足条件<br>
	 * 返回 -1表示判定小于条件;
	 * 返回0表示满足; 
	 * 返回1表示判定超出条件
	 * @param elems
	 * @return
	 */
	public int isSatisfy(Collection<Element> elems);
}
