package org.tw.zlq.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * ����һ��element���ϣ���ָ���������condition
 * �������Ԫ������б�Ҫ�����������Ԫ��ֵ��������������condition
 * @author longqiang
 *
 */
public class Processor {
	private Set<Solution> solutions = new HashSet<>();
	
	private Set<Element> initialedElements;
	private Condition condition;
	
	public Processor(Set<Element> elements, Condition condition) {
		this.condition = condition;
		this.initialedElements = elements; 
	}
	
	/**
	 * ���δ�δ����Ķ������������ջ�У����ж��Ƿ�������������������������з����б����������벻���з����б�
	 */
	public void selectCombine() {
		Stack<Element> selectedElems = new Stack<>();
		Queue<Element> candidateElements = new LinkedList<>(initialedElements);
		combine(selectedElems, candidateElements); 
	}
	
	// �ݹ�������ϲ�ѯѡ���ʺ�����������
	private void combine(Stack<Element> selectedElems, final Queue<Element> candidateElems) {
		Queue<Element> tmpUnprocessElems = new LinkedList<>(candidateElems);
		while (!tmpUnprocessElems.isEmpty()) {
			Element nextElem = tmpUnprocessElems.poll();
			selectedElems.add(nextElem);
			int result = condition.isSatisfy(selectedElems);
			if (result == 0) {
				solutions.add(new Solution(selectedElems));
			} else if (result < 0) {
				combine(selectedElems, tmpUnprocessElems);
			} 
			selectedElems.pop();
		}
	}
	
	public List<Solution> getSortedSolutions(){
		List<Solution> sortedSolutions = new ArrayList<>(solutions);
		Collections.sort(sortedSolutions, new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return o1.getElements().get(0).getIndex() - o2.getElements().get(0).getIndex();
			}
		});
		return sortedSolutions;
	}
}
