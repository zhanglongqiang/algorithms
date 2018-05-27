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
 * ���н������
 * @author longqiang
 *
 */
public class Solution{
	// solution�ֲ�ͬ�׶��ж������������Ҫ����
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
		// ��������Ϊ��������hashcodeʱ����ͬ˳����ͬԪ�ص�hashcodeһ�������ں����б�Ҫʱȥ��
		Collections.sort(selectedElements, cmp);
	}
	
	
	public List<Element> getElements() {
		return selectedElements;
	}
	
	/**
	 * �����±��ж�����Ԫ���б��Ƿ���ͬ
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
