/**
 * 
 */
package org.tw.zlq.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.tw.zlq.conference.error.ErrorCode;
import org.tw.zlq.conference.error.TwException;

/**
 * 排列解决方案
 * @author longqiang
 *
 */
public class Solution{
	// solution分不同阶段判定，因此这里需要保序
	private List<Element> selectedElements = new LinkedList<>();
	
	public Solution(List<Element> selectedElements) {
		if (selectedElements == null) {
			throw new TwException(ErrorCode.InputErr, "solution selected elements is null");
		}
		this.selectedElements.addAll(selectedElements);
		Comparator<Element> cmp = new Comparator<Element>() {
			@Override
			public int compare(Element o1, Element o2) {
				return o1.getIndex() - o2.getIndex();
			}
		};
		// 这里排序为了下面做hashcode时，不同顺序相同元素的hashcode一样，便于后续有必要时去重
		Collections.sort(selectedElements, cmp);
	}
	
	
	public List<Element> getElements() {
		return selectedElements;
	}
	
	/**
	 * 根据下标判定两个元素列表是否相同
	 * @param indexs
	 * @return
	 */
	private boolean equals2(List<Element> solutionIndexs) {
		if (solutionIndexs == null || solutionIndexs.size() != selectedElements.size()) {
			return false;
		}
		for (int i=0;i<solutionIndexs.size();i++) {
			if (!selectedElements.contains(solutionIndexs.get(i))) { 
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solution otherSolution = (Solution) obj;
		return equals2(otherSolution.getElements());
	}

	@Override
	public String toString() {
		return "{elements:" + selectedElements + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((selectedElements == null) ? 0 : selectedElements.hashCode());
		return result;
	}
}
