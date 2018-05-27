package org.tw.zlq.core;

import java.util.Collection;

public class SumCondition implements Condition{
	private int specifiedSum;
	
	public SumCondition(int specifiedSum) {
		super();
		this.specifiedSum = specifiedSum;
	}

	@Override
	public int isSatisfy(Collection<Element> elems) {
		int tmpSum = 0;
		for (Element e : elems) {
			tmpSum += e.getValue();
		}
		
		if (tmpSum < specifiedSum) {
			return -1;
		} else if (tmpSum > specifiedSum) {
			return 1;
		} else {
			return 0;
		}
	}
}
