package org.tw.zlq.conference.condition;

import java.util.Collection;

import org.tw.zlq.conference.error.ErrorCode;
import org.tw.zlq.conference.error.TwException;
import org.tw.zlq.core.Condition;
import org.tw.zlq.core.Element;

public class StageSumCondition implements Condition{
	private int firstStageSpecifiedSum;
	private int secondStageRangeStart;
	private int secondStageRangeEnd;
	
	/**
	 * @param firstStageSpecifiedSum  第一阶段求和指定值
	 * @param secondStageRangeStart  第二阶段求和范围开始值
	 * @param secondStageRangeEnd 第二阶段求和范围结束值
	 */
	public StageSumCondition(int firstStageSpecifiedSum, int secondStageRangeStart, int secondStageRangeEnd) {
		if (secondStageRangeEnd < secondStageRangeStart) {
			throw new TwException(ErrorCode.InputErr, "Condition error, sencond stage end less than start.");
		}
		this.firstStageSpecifiedSum = firstStageSpecifiedSum;
		this.secondStageRangeStart = firstStageSpecifiedSum + secondStageRangeStart;
		this.secondStageRangeEnd = firstStageSpecifiedSum + secondStageRangeEnd;
	}

	@Override
	public int isSatisfy(Collection<Element> elems) {
		int tmpSum = 0;
		boolean isMatchFirstStageSum = false;
		for (Element e : elems) {
			tmpSum += e.getValue();
			if (firstStageSpecifiedSum == tmpSum) {
				isMatchFirstStageSum = true;
			}
		}
		
		if (tmpSum <= firstStageSpecifiedSum) {
			return -1;
		} else {
			if (!isMatchFirstStageSum) {
				return 1;
			} else {
				if (tmpSum >= secondStageRangeStart && tmpSum <= secondStageRangeEnd) {
					return 0;
				} else if (tmpSum > secondStageRangeEnd ) {
					return 1;
				} else {
					return -1;
				}
			}
		}
	}
}
