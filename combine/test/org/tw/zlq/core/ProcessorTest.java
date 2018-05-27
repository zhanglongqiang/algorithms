/**
 * 
 */
package org.tw.zlq.core;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import org.tw.zlq.conference.condition.StageSumCondition;
import org.tw.zlq.utils.Utils;



/**
 * @author longqiang
 *
 */
public class ProcessorTest {
	@Test
	public void test1() {
		String sampleStr = "1,2,5,3,2";
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new SumCondition(5));
		processor.selectCombine();
		for (Solution s : processor.getSortedSolutions()) {
			System.out.println(s.getElements());
		}
	}
	
	/**
	 * 1. ���������һ�׶���ͣ�С�ڵڶ��׶η�Χ��Сֵ
	 * Ԥ�ڣ� �޽������
	 */
	@Test
	public void test3() {
		String sampleStr = "30, 30, 45, 45, 30, 60";
		
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new StageSumCondition(180, 180, 240));
		processor.selectCombine();
		Assert.assertTrue(processor.getSortedSolutions().isEmpty());
	}
	
	/**
	 * 2. ���������һ�׶���ͣ����ڵڶ��׶η�Χ���ֵ
	 * Ԥ�ڣ� �޽������
	 * 60,60,60, 241
	 */
	@Test
	public void test4() {
		String sampleStr = "60,60,60, 241";
		
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new StageSumCondition(180, 180, 240));
		processor.selectCombine();
		for (Solution s : processor.getSortedSolutions()) {
			System.out.println(s.getElements());
		}
		Assert.assertTrue(processor.getSortedSolutions().isEmpty());
	}
	
	/**
	 * 3. ���������һ�׶���ͣ����ڵڶ��׶η�Χ��Сֵ
 	* Ԥ�ڣ� ��
 	* 30, 30, 45, 45, 30, 60, 60, 60
	 */
	@Test
	public void test5() {
		String sampleStr = "30, 30, 45, 45, 30, 60, 60, 60";
		
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new StageSumCondition(180, 180, 240));
		processor.selectCombine();
		for (Solution s : processor.getSortedSolutions()) {
			System.out.println(s.getElements());
		}
		Assert.assertTrue(!processor.getSortedSolutions().isEmpty());
	}
	
	/**
 4. ���������һ�׶���ͣ����ڵڶ��׶η�Χ���ֵ
  Ԥ�ڣ� ��
 180, 130, 50, 60
	 */
	@Test
	public void test6() {
		String sampleStr = "180, 130, 50, 60";
		
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new StageSumCondition(180, 180, 240));
		processor.selectCombine();
		for (Solution s : processor.getSortedSolutions()) {
			System.out.println(s.getElements());
		}
		Assert.assertTrue(!processor.getSortedSolutions().isEmpty());
	}
}
